import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.event.IIOReadProgressListener;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

class Map {
	static public int mapW = 4096;
	static public int mapH = 2849;
	final static public int pieceW = 500;
	final static public int pieceH = 500;
	/**
	 * ������ ����������� �����. ������ ����������� - �:<br>
	 * imgs[x][y]
	 */
	Image[][] imgs;
	/** �����������-��������. ������������ ����� ��������� ����� */
	Image nullimg;
	int[] imgsx;
	int[] imgsy;

	ArrayList<String> loadingImages = new ArrayList<String>();

	village v;
	private double mapX;
	private double mapY;
	private panel panel;
	private MyThread myThread;
	int imgW;
	int imgH;
	protected static double scale = 1;
	ArrayList<village> villageList = new ArrayList<village>();

	public Map(panel p) {
		System.out.println("�������� �������� �����");
		panel = p;
		int max_x=0, max_y=0;
		// �������� ������� ����� �� ���������� �����������
		// ��������� ����� �� ����� "�����������" ��������� ����� ����� �������� (�������������� � ������� �����������)
		String path = new File("").getAbsolutePath();
		File dir = new File(path+ "/src/img/maps/"); //path ��������� �� ����������
		File[] arrFiles = dir.listFiles();
		List<File> lst = Arrays.asList(arrFiles);
		
		for (int i = 0; i < lst.size(); i++) {
			String n = lst.get(i).getName();
			if (n.indexOf(",") > 0) 
			{
				n = n.substring(3).replaceAll(".png", "");
				String nn[] = n.split(",");
				int x = Integer.parseInt(nn[0]);
				int y = Integer.parseInt(nn[1]);
				max_x = Math.max(max_x, x);
				max_y = Math.max(max_y, y);
				//System.out.println(n);
			}
		}
		System.out.println(max_x);
		System.out.println(max_y);
		Image z = loadImage("map" + max_x + "," + max_y + ".png");
		// max_x � max_y �� ���� ������ -1, ������ ��� ��������� �� ���������� � ����
		mapW = (max_x ) * pieceW + z.getWidth(null);
		mapH = (max_y ) * pieceH + z.getHeight(null);
		System.out.println("������ �����: "+mapW+", "+mapH);
		
		int countX = mapW / pieceW;
		if (mapW % pieceW != 0)
			countX++;
		int countY = mapH / pieceH;
		if (mapH % pieceH != 0)
			countY++;
		imgs = new Image[countX][countY];
		imgsx = new int[countX];
		imgsy = new int[countY];

		try {
			nullimg = ImageIO.read(this.getClass().getResource(
					"img/nullimg.png"));
			for (int x = 0; x < imgs.length; x++) {
				for (int y = 0; y < imgs[x].length; y++) {
					imgs[x][y] = nullimg;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"������ �������� ����������� ��������");
		}

		for (int i = 0; i < 6; i++) {
			v = new village();
			v.X = 100 * i;
			v.Y = 100 * i;
			villageList.add(v);
		}

		// ��� ������ �������� �������� ����
		// TODO: 1) ������ ������� ������ ���� ��������� (��������, 60�60 px)
		// TODO: 2) ����� ������ �� ������� (������� ������ � ��� ������, ����
		// �������� ��� �������� ���� ��������� � ������)
		/*
		 * for (int i = 0; i < map.villageList.size()-1; i++) {
		 * villageList.get(i). loadImageVilageImg(this.getClass().getResource(
		 * "img/cite.jpeg")); villageList.get(i).
		 * loadImagePictureVilageImg(this.getClass().getResource(
		 * "img/cite.jpeg")); }
		 */

		System.out.println("��������� �������� �����");

	}

	public double getX() {
		return mapX;
	}

	public double getY() {
		return mapY;
	}

	/**
	 * <b>scaleMultiply</b><br>
	 * �������� �� �������<br>
	 * <br>
	 * 
	 * @return ����������: double
	 */
	private double scaleM(double x) {
		return x * scale;
	}

	/**
	 * <b>scaleDivide</b><br>
	 * ��������� �� �������<br>
	 * <br>
	 * 
	 * @return ����������: double
	 */
	private double scaleD(double x) {
		return x / scale;
	}

	/**
	 * ������������� �����
	 * 
	 //* @see Map#loadImage(String, Image)
	 */
	public void draw(Graphics g) {
		// ��� �������, ����� ������ ����� ������
		imgsx[0] = (int) (scaleM(-mapX));
		imgsy[0] = (int) (scaleM(-mapY));
		int W = (int) scaleM(pieceW);
		int H = (int) scaleM(pieceH);
		// System.out.println("W = "+W+", H = " + H+", scale = "+scale);
		for (int x = 0; x < imgsx.length - 1; x++) {
			imgsx[x + 1] = imgsx[x] + H;
		}
		for (int y = 0; y < imgsy.length - 1; y++) {
			imgsy[y + 1] = imgsy[y] + H;
		}
		for (int x = 0; x < imgs.length; x++) {
			for (int y = 0; y < imgs[x].length; y++) {
				if (imgs[x][y] == nullimg && isImageVisible(x, y)) {
					Image temp = loadImage("map" + x + "," + y + ".png");
					if (temp != null)
						imgs[x][y] = temp;
				}
				// System.out.println("imgx["+x+"] = "+imgsx[x] +
				// ", imgy["+y+"] = "+imgsx[y]);
				int X = (int) (imgsx[x]);
				int Y = (int) (imgsy[y]);
				g.drawImage(imgs[x][y], X, Y, W, H, null);
			}
		}

		/*
		 * // "��������" g.drawRect((panel.getWidth() - 300) / 2, 30, 300, 30);
		 * g.setColor(Color.green); if (myThread != null) {
		 * g.fillRect((panel.getWidth() - 300) / 2, 32, (int) (300 *
		 * myThread.percent / 100), 28); // Font currentFont = g.getFont(); //
		 * Font newFont = currentFont.deriveFont(currentFont.getSize() * //
		 * 1.4F); Font newFont = new Font("Courier New", Font.BOLD, 17);
		 * g.setFont(newFont); g.setColor(Color.black);
		 * g.drawString("�������� �����������", (panel.getWidth() - 300) / 2 +
		 * 30, 55); }
		 */
	}

	/**
	 * ����������, ������ �� ����������� � �������� x � y
	 * 
	 * @param x_img
	 *            - ���������� ����� ����������� �� X ( �� ��� ����������)
	 * @param y_img
	 *            - ���������� ����� ����������� �� Y ( �� ��� ����������)
	 * @return ������, ���� ����������� � ��������� �������� ������ �� ������
	 */
	private boolean isImageVisible(int x_img, int y_img) {

		double XX = scaleM( x_img * pieceW - mapX);
		double YY = scaleM( y_img * pieceH - mapY);
		if ((XX >= 0 && XX <= panel.getWidth() + pieceW)
				|| (XX + pieceW >= 0 && XX + pieceW <= panel.getWidth()
						+ pieceW)) {
			if ((YY >= 0 && YY <= panel.getHeight() + pieceH)
					|| (YY + pieceH >= 0 && YY + pieceH <= panel.getHeight()
							+ pieceH))
				return true;
		}

		return false;
	}

	/**
	 * ��������� ����������� �� �������� � ���������� ��� � ����������
	 * ���������� Image. ����������� ����� �������� � ����� img/ <br>
	 * ��� ����� ������ ���� mapX,Y.png<br>
	 * �������� ��� X = 2, Y = 1 ���� ������ ����� ��� map2,1.png
	 * 
	 * @param filename
	 *            - ��� ����� ����������� ��� ��������
	 * @return ���������� ����������� ����������� Image
	 * @see Map#draw(Graphics)
	 */
	private Image loadImage(String filename) {
		// �������� ����������� ������ ���� � ������
		// ������� �� ������ ��������� ����� �����,
		// ���� ��� ��� ������ ����� ���
		// �������� ����������� �� ����� �����
		// System.out.println(outImage);

		if (loadingImages.indexOf(filename) > -1) {
			return null;
		}
		loadingImages.add(filename);

		Image outImage = null;
		String path = new File("").getAbsolutePath();
		File f = new File(path + "/src/img/maps/" + filename);
		if (f.exists()) {
			try {
				outImage = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// System.out.println("file not exists: " + f.getAbsolutePath());
		}
		loadingImages.remove(filename);
		return outImage;
	}

	/*
	 * private static String getFormatName(Object o) { try { ImageInputStream
	 * iis = ImageIO.createImageInputStream(o); Iterator iter =
	 * ImageIO.getImageReaders(iis); if (!iter.hasNext()) { return null; }
	 * ImageReader reader = (ImageReader) iter.next(); iis.close();
	 * 
	 * return reader.getFormatName(); } catch (IOException e) { } return null; }
	 */

	private class MyThread extends Thread {
		URL res;
		float percent = 0;

		// �����������
		MyThread(URL resource) {
			// ������ ����� �����
			super("������ �����");
			res = resource;
			start(); // ��������� �����
		}

		public void run() {
			// ���� ��������� �� ����� �������� ����������� - ����� ������
			// ���������� � ���� ArrayList ��������� ���������
			loadingImages.add(res.getPath());

			// http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/DeterminingtheFormatofanImageinaFile.htm
			// http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/AddImageIOReadProgressListenertoImageReader.htm
			// https://stackoverflow.com/questions/18636708/how-to-give-image-url-to-a-createimageinputstream-method
			// https://developer.alexanderklimov.ru/android/java/thread.php

			Iterator readers = ImageIO.getImageReadersBySuffix("JPEG");
			// Iterator readers = ImageIO.getImageReadersBySuffix("PNG");
			ImageReader imageReader = (ImageReader) readers.next();
			InputStream urlInputHere;
			try {
				urlInputHere = res.openStream();
				ImageInputStream in;
				in = ImageIO.createImageInputStream(urlInputHere);
				imageReader.setInput(in, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageReader
					.addIIOReadProgressListener(new IIOReadProgressListener() {
						public void imageComplete(ImageReader source) {
							System.out.println("image complete " + source);
							panel.repaint();
						}

						public void imageProgress(ImageReader source,
								float percentageDone) {
							System.out.println("image progress " + source
									+ ": " + percentageDone + "%");
							if (myThread != null) {
								percent = percentageDone;
								panel.repaint();
							}
						}

						public void imageStarted(ImageReader source,
								int imageIndex) {
							System.out.println("image #" + imageIndex
									+ " started " + source);
						}

						public void readAborted(ImageReader source) {
							System.out.println("read aborted:" + source);
						}

						public void sequenceComplete(ImageReader source) {
							System.out.println("sequence complete " + source);
						}

						public void sequenceStarted(ImageReader source,
								int minIndex) {
							System.out.println("sequence started " + source
									+ ": " + minIndex);
						}

						public void thumbnailComplete(ImageReader source) {
							System.out.println("thumbnail complete " + source);
						}

						public void thumbnailProgress(ImageReader source,
								float percentageDone) {
							System.out.println("thumbnail started " + source
									+ ": " + percentageDone + "%");
						}

						public void thumbnailStarted(ImageReader source,
								int imageIndex, int thumbnailIndex) {
							System.out.println("thumbnail progress " + source
									+ ", " + thumbnailIndex + " of "
									+ imageIndex);
						}
					});

			/*
			 * try { img = imageReader.read(0); imgW = img.getWidth(null); imgH
			 * = img.getHeight(null); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
			loadingImages.remove(res.getPath()); // ����� ������ ��������� �����
		}//зачем нужен этот поток ?

	}

	public void loadImage(URL resource) throws Exception {
		// img = ImageIO.read(resource);
		myThread = new MyThread(resource);
	}

	public Point2D move(int mouseX, int mouseY) throws Exception {
		if (panel == null) {
			throw new Exception(
					"����� �� ����������������� �������. � ��� � ��������?");
			// return;
		}
		double OldX = mapX;
		double OldY = mapY;
		mapX -= scaleD(mouseX);
		mapY -= scaleD(mouseY);
		if (mapX < 0)
			mapX = 0;
		if (mapY < 0)
			mapY = 0;
		// panel -�� �� �����������, ����������� ������ ������ ����� � X
		if (scaleM(mapX) + panel.getWidth() > scaleM(mapW))
			mapX = (scaleM(mapW) - panel.getWidth());
		if (scaleM(mapY) + panel.getHeight() > scaleM(mapH))
			mapY = (scaleM(mapH) - panel.getHeight());
		double shiftx = OldX - mapX;
		double shifty = OldY - mapY;
		return new Point2D.Double(shiftx, shifty);
	}

	public void moveToPers() throws Exception {
		// �����
		int centerX = panel.getWidth() / 2;
		int centerY = panel.getHeight() / 2;
		Point2D shift = move((centerX - (int) Pers.X), (centerY - (int) Pers.Y));
		Pers.shiftXY(shift);

	}
}