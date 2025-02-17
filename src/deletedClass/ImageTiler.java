package deletedClass;

import GameMode.Drawable;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;

public class ImageTiler implements Drawable {
    private BufferedImage image; // Одно изображение с разрешением 1920x1080
    private BufferedImage ChaheImg;
    BufferedImage im;
    private ImageReader reader;

    public ImageTiler()  {
        String imagePath  = "src/img/map1_7680x4320.jpg";
        ImageInputStream imageInputStream = null;
        try {
            imageInputStream = ImageIO.createImageInputStream(new File(imagePath));
            // Получение декодера изображения
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            if (!readers.hasNext()) {
                throw new IOException("No image readers found");
            }
            reader = readers.next();
            reader.setInput(imageInputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public BufferedImage getSubImage(int x, int y, int width, int height) throws IOException {
        // Указание области для чтения
        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceRegion(new Rectangle(x, y, width, height));

        // Чтение указанной области изображения в BufferedImage
        return reader.read(0, param);
    }

    private BufferedImage loadImage(int b) {
        try {
            // Открытие изображения с помощью ImageInputStream
            File inputFile = new File("src/img/map1_7680x4320.jpg");
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputFile);

            // Получение декодера изображения
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            if (!readers.hasNext()) {
                throw new IOException("No image readers found");
            }
            ImageReader reader = readers.next();
            reader.setInput(imageInputStream);

            // Получение размеров изображения
            int imageWidth = reader.getWidth(0);
            int imageHeight = reader.getHeight(0);

            // Указание области, которую мы хотим прочитать
            int x = b; // начальная координата x
            int y = 0; // начальная координата y
            int width = 1920; // ширина части изображения
            int height = 1080; // высота части изображения

            // Чтение указанной области изображения в BufferedImage
            ImageReadParam param = reader.getDefaultReadParam();
            param.setSourceRegion(new Rectangle(x, y, width, height));
            BufferedImage subImage = reader.read(0, param);


            imageInputStream.close();
            // Сохранение полученной части изображения в файл
            System.out.println("Часть изображения успешно получена!");
            return subImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void updScl() {

    }

    @Override
    public void draw(Graphics g) throws IOException {
        if (image != null) {
            //g.drawImage(image, 0, 0, Okno.windowWidth, Okno.windowHeight, null);
        }

        g.drawImage(im,0,0,null);
        /*if(Shifting.getScale() == 0.5) {


        }*/
    }

    private BufferedImage getTile(int x, int y) {

        return null;
    }

}
/*














 * */