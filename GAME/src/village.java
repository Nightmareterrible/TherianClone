import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class village {

	private Image vilageImg;// картинка самого горoда
	private Image pictureVilageImg;// картинка которая будет отображаться на
									// карте
	boolean citeIsOpen = false;
	float X , Y ;
	int persR = 30;

	int vilagePictureHeight;
	int vilagePictureWidth ;
	int vilageWidth ;
	int vilageHeight ;

	public void loadImageVilageImg(URL resource) throws IOException {
		vilageImg = ImageIO.read(resource);
		vilageHeight = 10;
		vilageWidth = 10;
	}

	public void loadImagePictureVilageImg(URL resource) throws IOException {
		pictureVilageImg = ImageIO.read(resource);
		vilagePictureHeight = 10;
		vilagePictureWidth = 10;
	}

	public village() {

	}

	static int r(double x) {
		return (int) (x * Map.scale);
	}

	static int r_(double x) {
		return (int) (x / Map.scale);
	}

	static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public void draw(Graphics g) {

		int roundedShiftedX = Math.round(X) - persR;
		int roundedShiftedY = Math.round(Y) - persR;
		

		if (citeIsOpen == true) {// рисоваться должно на MapPanel
			if (vilageHeight != 0 && vilageWidth != 0)
				g.drawImage(vilageImg, r(roundedShiftedX), r(roundedShiftedY),
						vilageWidth, vilageHeight, null);
		} else {
			if (vilagePictureHeight != 0 && vilagePictureWidth != 0)
				g.drawImage(pictureVilageImg, r(roundedShiftedX),r(roundedShiftedY),
						vilagePictureWidth,vilagePictureHeight, null);
		}
	}

	public void shiftXY(Point2D shift) {
		X += shift.getX();
		Y += shift.getY();

	}

}
