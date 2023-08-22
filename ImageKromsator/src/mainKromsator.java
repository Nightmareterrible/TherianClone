import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class mainKromsator {

	public static void main(String[] args) {
		int PieceW = 500;
		int PieceH = 500;
		try {
			Image img = ImageIO.read(mainKromsator.class.getResource("map1.jpg"));
			BufferedImage b = (BufferedImage) img;
			for (int x = 0; x < b.getWidth(); x+=PieceW) {
				for (int y = 0; y < b.getHeight(); y+=PieceH) {
					// java.awt.image.RasterFormatException: (y + height) is outside of Raster
					int W = Math.min(PieceW, b.getWidth() - x);
					int H = Math.min(PieceH, b.getHeight() - y);
					
					BufferedImage piece = b.getSubimage(x, y, W, H);
					// Если не уничтожить graphics, он будет готов к рисованию, и не даст сохранить. Надо уничтожить
					// https://ru.stackoverflow.com/questions/15684/%D0%A1%D0%BE%D1%85%D1%80%D0%B0%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5-%D0%BE%D0%B1%D1%8A%D0%B5%D0%BA%D1%82%D0%B0-bufferedimage-%D0%B2-%D0%B3%D1%80%D0%B0%D1%84%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B9-%D1%84%D0%B0%D0%B9%D0%BB
					piece.getGraphics().dispose();
					ImageIO.write(piece, "png", new File("map"+x/PieceW+","+y/PieceH+".png"));
				}
			}
			System.out.println("Работа выполнена, хозяин !");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
