import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Timer;

class Pers {
	static float X, Y;
	static float StartX, StartY;
	static float DestX, DestY;
	static float d, v = 5, t;
	static float pkat;
	static float vx, vy;//что это такое ??????
	static float k, b;
	static Timer t1 = null;
	private static panel panel = null;
	
	static int PersR = 30;
	static int CircleX = -100;
	static int CircleY = -100;
	private static Image img;
	public Pers(){
		try {
			Pers.loadImage(this.getClass().getResource("img/pers.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	static int r(double x)
	{
		return (int)(x * Map.scale);
	}
	static int r_(double x)
	{
		return (int)(x / Map.scale);
	}

	static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public static void move(float mx1, float my1) throws Exception {
		float mx = r_(mx1);
		float my = r_(my1);
		if (panel == null) {
			throw new Exception(
					"Pers ?? ???????????????? ???????. ? ??? ??? ?????????");
			// return;
		}
		StartX = X;
		StartY = Y;
		DestX = mx;
		DestY = my;

		// d = ((mx - X) * (mx - X)) + ((my - Y) * (my - Y));
		// d = (float) Math.sqrt(d);
		d = (float) dist(mx, my, X, Y);
		t = d / v;
		vx = (DestX - StartX) / t;
		vy = (DestY - StartY) / t;

		if (t1 != null) {
			try {
				t1.stop();
				t1 = null;
				Thread.sleep(20);
			} catch (Exception e1) {
			}
		}
		t1 = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mx - X < 0 && my - Y < 0) {
					X += vx;
					Y += vy;
				} else if (mx - X > 0 && my - Y < 0) {
					X += vx;
					Y += vy;
				} else if (mx - X > 0 && my - Y > 0) {
					X += vx;
					Y += vy;
				} else if (mx - X < 0 && my - Y > 0) {
					X += vx;
					Y += vy;
				} else {
					vx = 0;
					vy = 0;
				}

				// ??????????? DestX, Y - ?????? ??? ??? ????? ???????????
				// ?????? ? ??????
				if (Math.sqrt(Math.pow((DestX - X), 2)
						+ Math.pow((DestY - Y), 2)) < v) {
					t1.stop();
					t1 = null;
					StartX = DestX;
					StartY = DestY;
				}

				panel.repaint();
			}

		});

		t1.start();

	}

	public static void draw(Graphics g) {
		if (StartX != DestX && StartY != DestY) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(10.0f));
			g.setColor(Color.yellow);
			int X = r(Math.round(StartX));
			int Y = r(Math.round(StartY));
			int X2 = r(Math.round(DestX));
			int Y2 = r(Math.round(DestY));
			g.drawLine(X - 1, Y,X2, Y2);
			g.setColor(Color.white);
			g.drawLine(X + 1, Y + 1,X2, Y2);
			g.setColor(Color.black);
			g.drawLine(X, Y,
					X2, Y2);
			g2.setStroke(new BasicStroke(1.0f));
		}

		int XX = Math.round(X) - PersR;
		int YY = Math.round(Y) - PersR;

		if (X + PersR < 0 || Y + PersR < 0 || r(X) > panel.getWidth()
				|| r(Y) > panel.getHeight()) {
			// ??????? ????? ??????????? ? ????? X ? Y. ??, ??????? ?? 0 -
			// ???????????? (?????? ??? ?????????)
			int interX = -1;
			int interY = -1;

			double x1 = panel.getWidth() / 2;
			double y1 = panel.getHeight() / 2; // ????? ?????????
			double x2 = X;
			double y2 = Y;
			if (Math.abs(x2 - x1) < 1)// ??????? ?? ?????? ?????? 1 ???????
										// (????? ?????)
			{
				// ??? ??? ?????, ???? ???-?? ??????
				// ????... ????? ??????????? ?????? ? ???????? ?????? ?????
				// ?????:
				if (Y + PersR < 0)
					interY = 0;
				else
					interY = panel.getHeight();
				interX = (int) x1; // ??? ? ???? ????????
			} else if (Math.abs(y2 - y1) < 1) // ??????? ?? ?????? ?????? 1
												// ??????? (????? ?????)
			{
				// ??? ??? ?????, ???? ???-?? ??????
				// ????... ????? ??????????? ?????? ? ???????? ?????? ?????
				// ?????:
				interY = (int) y1; // ??? ? ???? ????????
				if (X + PersR < 0)
					interX = 0;
				else
					interX = panel.getWidth();
			} else {
				double k = (y2 - y1) / (x2 - x1);
				double b = (x2 * y1 - x1 * y2) / (x2 - x1);
				// x,y = 0,b - ??????????? ? OY
				// x,y = -b/k,0 - ??????????? ? OX
				double d_do_OX = dist(x1, y1, -b / k, 0);
				double d_do_OY = dist(x1, y1, 0, b);
				if (d_do_OX < d_do_OY) // !!!! ?? ????????? ???? !!!
				{

					if (Y + PersR < 0) {
						interY = 0;
						interX = (int) (-b / k);
					} else {
						interY = panel.getHeight();
						interX = panel.getWidth() - (int) (-b / k);
					}
				} else {
					if (X + PersR < 0) {
						interX = 0;
						interY = (int) b;
					} else {
						interX = panel.getWidth();
						interY = panel.getHeight() - (int) b;
					}

				}

			}
			g.setColor(Color.YELLOW);
			CircleX = interX - PersR;
			CircleY = interY - PersR;
			g.fillOval(r(CircleX),  r(CircleY), PersR * 2, PersR * 2); // ??????,
																				// ???
																				// ????
																				// ??????
			// ????? ?? ?????? ????????? ? ?????????
			// g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
		} else {
			g.setColor(Color.DARK_GRAY);
			if (img  != null)
				g.drawImage(img, r(XX), r(YY), PersR * 2, PersR * 2, null);
			else
				g.fillOval(r(XX), r(YY), PersR * 2, PersR * 2); // ?????? ?????????
			CircleX = -100;
			CircleY = -100;
		}

	}

	public static void init(panel p, float x2, float y2) {
		Pers pers = new Pers();
		panel = p;
		X = x2;
		Y = y2;
	}

	public static void shiftXY(Point2D shift) {
		StartX += shift.getX();
		X += shift.getX();
		DestX += shift.getX();
		StartY += shift.getY();
		Y += shift.getY();
		DestY += shift.getY();
	}
	public static void loadImage(URL resource) throws IOException {
		img = ImageIO.read(resource);
		
	}

}