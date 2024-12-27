import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

// Класс Map для работы с большими изображениями
public class Map {
    private panel panel;
    BufferedImage imagePart;

    public Map(panel p) {
        panel = p;
        try {
            String filePath = "src/img/map.png"; // Путь к изображению
            int x = 0;
            int y = 0; // Начальные координаты области загрузки
            int width = 1500, height = 1000; // Размеры области загрузки

            // Читаем часть изображения
            imagePart = readImagePartially(filePath, x, y, width, height);

            // Выводим информацию о загруженной части изображения
            System.out.println("Part of the image loaded successfully.");
            System.out.println("Image part dimensions: " + imagePart.getWidth() + "x" + imagePart.getHeight());

        } catch (IOException e) {
            System.err.println("Error reading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static BufferedImage readImagePartially(String filePath, int x, int y, int width, int height) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        ImageInputStream input = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
        if (!readers.hasNext()) {
            throw new IOException("No image readers found for file: " + filePath);
        }

        ImageReader reader = readers.next();
        reader.setInput(input);

        ImageReadParam param = reader.getDefaultReadParam();
        param.setSourceRegion(new java.awt.Rectangle(x, y, width, height)); // Указываем область загрузки

        return reader.read(0, param); // Читаем указанную область изображения
    }

    public void UpdateMap() {

    }

    public void draw(Graphics g) {
        if (imagePart != null) {
            Shifting.drowAutoScaleAndShiftingImage(g, imagePart, 0, 0, 1500, 1000, null);
            //g.drawImage(imagePart, 0, 0, null); // Отображаем изображение начиная с точки (0, 0)
        }
    }

    public static void main(String[] args) {
        try {
            String filePath = "src/img/map.png"; // Путь к изображению
            int x = 0, y = 0; // Начальные координаты области загрузки
            int width = 1500, height = 1000; // Размеры области загрузки

            // Читаем часть изображения
            BufferedImage imagePart = readImagePartially(filePath, x, y, width, height);

            // Выводим информацию о загруженной части изображения
            System.out.println("Part of the image loaded successfully.");
            System.out.println("Image part dimensions: " + imagePart.getWidth() + "x" + imagePart.getHeight());

        } catch (IOException e) {
            System.err.println("Error reading image: " + e.getMessage());
            e.printStackTrace();
        }
    }


}