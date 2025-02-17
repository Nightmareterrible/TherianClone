package Map;

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.*;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;

class MapTiler {
    private final String imagePath;
    private static final int TILE_WIDTH = 640;
    private static final int TILE_HEIGHT = 360;
    private static final int MIN_WIDTH = 1920;
    private static final int MIN_HEIGHT = 1080;
    private final ExecutorService executor;
    private final Properties properties;

    public MapTiler(String imagePath) {
        this.imagePath = imagePath;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.properties = new Properties();
    }

    public void generateTiles() {
        try (ImageInputStream inputStream = ImageIO.createImageInputStream(new File(imagePath))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(inputStream);
            if (!readers.hasNext()) {
                throw new IOException("No suitable image reader found.");
            }

            int lod = 0;
            int width = 1, height = 1;

            File propertiesFile = new File("tiles/MapData.properties");
            propertiesFile.getParentFile().mkdirs();

            // Добавляем информацию о размере тайлов в файл properties
            properties.setProperty("Tile", "Dimensions");
            properties.setProperty("TileWidth", String.valueOf(TILE_WIDTH));
            properties.setProperty("TileHeight", String.valueOf(TILE_HEIGHT));

            // Описание LOD
            properties.setProperty("LOD", "Levels");
            BufferedImage image = null;

            int width1 = 1, height1 = 1;

            try (ImageInputStream stream = ImageIO.createImageInputStream(new File(imagePath))) {
                ImageReader reader = ImageIO.getImageReaders(stream).next();
                reader.setInput(stream, false, false);
                width = reader.getWidth(0);
                height = reader.getHeight(0);

                image = reader.read(0);
            } catch (Exception e) {

            }
            do {
                if (lod > 0)
                    image = scaleImage(image, width, height); // Масштабируем изображение


                // Обновляем ширину и высоту на уменьшенное изображение
                width = image.getWidth();
                height = image.getHeight();

                int cols = (int) Math.ceil((double) width / TILE_WIDTH);
                int rows = (int) Math.ceil((double) height / TILE_HEIGHT);

                File outputDir = new File("tiles/LOD" + lod);
                outputDir.mkdirs();
                properties.setProperty("LOD" + lod + ".Path", outputDir.getAbsolutePath());
                properties.setProperty("LOD" + lod + ".Width", String.valueOf(width));
                properties.setProperty("LOD" + lod + ".Height", String.valueOf(height));

                CountDownLatch latch = new CountDownLatch(cols * rows);

                for (int y = 0; y < rows; y++) {
                    for (int x = 0; x < cols; x++) {
                        final int tileX = x;
                        final int tileY = y;
                        int finalWidth = width;
                        int finalHeight = height;
                        BufferedImage finalImage = image;
                        executor.submit(() -> {
                            try {
                                int tileWidth = Math.min(TILE_WIDTH, finalWidth - tileX * TILE_WIDTH);
                                int tileHeight = Math.min(TILE_HEIGHT, finalHeight - tileY * TILE_HEIGHT);

                                BufferedImage tile = finalImage.getSubimage(tileX * TILE_WIDTH, tileY * TILE_HEIGHT, tileWidth, tileHeight);
                                File tileFile = new File(outputDir, "tile_" + tileX + "_" + tileY + ".png");
                                ImageIO.write(tile, "png", tileFile);
                                System.out.println("tile_" + tileX + "_" + tileY + ".png");
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                latch.countDown();
                            }
                        });
                    }
                }
                latch.await();

                // Уменьшаем размеры для следующего LOD
                width /= 2;
                height /= 2;
                lod++;


            } while (width >= MIN_WIDTH && height >= MIN_HEIGHT && lod < 10); // Ограничиваем до 10 уровней или минимального размера

            // Сохраняем количество LOD
            properties.setProperty("LODCount", String.valueOf(lod));

            // Сохраняем все данные в файл
            try (FileOutputStream fos = new FileOutputStream(propertiesFile)) {
                properties.store(fos, "Map LOD Paths");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    // Метод для масштабирования изображения
    private BufferedImage scaleImage(BufferedImage image, int targetWidth, int targetHeight) {
        Image scaledImage = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }


}