package deletedClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageResizer {
    public static void createResizedImages(BufferedImage originalImage, String outputPath) throws IOException {
        createResizedImage(originalImage, outputPath + "map1_1920x1080.jpg", 1920, 1080);
        createResizedImage(originalImage, outputPath + "map1_3840x2160.jpg", 3840, 2160);
        createResizedImage(originalImage, outputPath + "map1_7680x4320.jpg", 7680, 4320);
    }

    private static void createResizedImage(BufferedImage originalImage, String outputPath, int width, int height) throws IOException {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        ImageIO.write(resizedImage, "jpg", new File(outputPath));
        System.out.println("Created image: " + outputPath + " with resolution " + width + "x" + height);
    }
}
