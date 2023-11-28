package _GAME;
import java.awt.Button;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.swing.JPanel;

public class SizeAndScrol {
	protected boolean mousepressed;
	protected boolean paintmovecircle;
	protected boolean clicked = false;
	protected int oldmx;
	protected int oldmy;
	protected int pressedmx;
	protected int pressedmy;
	protected boolean move_map_activated;
	public Button btn;
	MouseMotionAdapter mouseMotionAd;
	MouseAdapter mouseAd;
	MouseAdapter mouseWhil;
	double koeff = 1.0;

	// -------------------------------------------------------

	// -------------------------------------------------------
	public SizeAndScrol() {

		float X = (int) (Math.random() * 500);
		float Y = (int) (Math.random() * 500);

		/*
		 * while (okno.p==null) { System.out.println("���� OKNO.P"); }
		 */
		mouseMotionAd = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				
					paintmovecircle = true;
					int mx = e.getX();
					int my = e.getY();

					double d = Math.sqrt(Math.pow((mx - pressedmx), 2) + Math.pow((my - pressedmy), 2));
					if (redactor.r == null ||
							redactor.r.leftMauseButonIsPresd == false) {
						if (d > GLOBALS.drag_circle_diameter) {
							move_map_activated = true;
					}
						try {
							Point2D shift = okno.p.map.move(mx - oldmx, my - oldmy);
							Pers.shiftXY(shift);
							for (int i = 0; i < okno.p.map.villageList.size() - 1; i++) {
								okno.p.map.villageList.get(i).shiftXY(shift);
							}
							if (redactor.r != null)
								redactor.r.shiftXY(shift);
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						// ����� !!! ����� ��������� - ��� ���������� ������ ������
						// ��������� ���
						// �������� (������� ��������� pressedmx, ...y)
						oldmx = mx;
						oldmy = my;
					}
					okno.p.repaint();
				
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				
				
			}
		};

		
		mouseAd = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mousepressed = true;
				pressedmx = oldmx = e.getX();
				pressedmy = oldmy = e.getY();
				int mx = e.getX();
				int my = e.getY();
				if (Pers.CircleX != -100 && Pers.CircleY != -100) {
					if (Pers.dist(Pers.CircleX + Pers.PersR, Pers.CircleY + Pers.PersR, mx, my) < Pers.PersR) {
						clicked = true;// ������ �� mouseReleased - ����� ��
										// ���� Pers.Move
					}
				}
			}

			// -------------------------------------------------------
			@Override
			public void mouseReleased(MouseEvent e) {
				int mx = e.getX();
				int my = e.getY();

				// ////////////////////////////////////////////////////////////////
				for (int i = 0; i < okno.p.map.villageList.size() - 1; i++) {
					village vil = okno.p.map.villageList.get(i);// �� ���� ������ ��� ��� +30 ��������
					if (vil.X <= mx / Map.scale + 30 && vil.Y <= my / Map.scale + 30
							&& vil.X + vil.vilagePictureWidth >= mx / Map.scale + 30
							&& vil.Y + vil.vilagePictureHeight >= my / Map.scale + 30) {

						okno.p.map.villageList.get(i).citeIsOpen = true;
						System.out.println("�� ������� ����� ");
						return;
					}
				}

				mousepressed = false;
				paintmovecircle = false;
				if (!move_map_activated) {
					double d = Math.sqrt(Math.pow((mx - pressedmx), 2) + Math.pow((my - pressedmy), 2));
					if (d < GLOBALS.drag_circle_diameter && !clicked) {
						try {
							// System.out.println("do move");
							if (GLOBALS.mode.toLowerCase().equals("player")) {
								System.out.println(GLOBALS.mode);
								Pers.move(mx, my);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				move_map_activated = false;
				clicked = false;

				
				
				paintmovecircle = true;
				

				double d = Math.sqrt(Math.pow((mx - pressedmx), 2) + Math.pow((my - pressedmy), 2));
				if (d > GLOBALS.drag_circle_diameter) {
					move_map_activated = true;

					try {
						Point2D shift = okno.p.map.move(mx - oldmx, my - oldmy);
						Pers.shiftXY(shift);
						for (int i = 0; i < okno.p.map.villageList.size() - 1; i++) {
							okno.p.map.villageList.get(i).shiftXY(shift);
						}
						if (redactor.r != null)
							redactor.r.shiftXY(shift);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					// ����� !!! ����� ��������� - ��� ���������� ������ ������
					// ��������� ���
					// �������� (������� ��������� pressedmx, ...y)
					oldmx = mx;
					oldmy = my;
				}
				okno.p.repaint();
			}

			// -------------------------------------------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON1) {
					int mx = e.getX();
					int my = e.getY();

					try {
						if (Pers.CircleX != -100 && Pers.CircleY != -100) {
							// ���� �������� �� ���������� - �� ������� ����� �
							// ���������
							// ���������� �� ������ ���������� �� mx,my ������
							// ���� ������ �������
							if (Pers.dist(Pers.CircleX + Pers.PersR, Pers.CircleY + Pers.PersR, mx, my) < Pers.PersR) {
								clicked = true; // ������ �� mouseReleased -
												// ����� �� ���� Pers.Move
								okno.p.map.moveToPers();
							}
						} // ���� �������� �� �� ����� ���������� - ��
							// ���������� ��������� � ����� �����
						else if (GLOBALS.mode.toLowerCase().equals("player")) {
							System.out.println(GLOBALS.mode);
							Pers.move(mx, my);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			// -------------------------------------------------------
		};
		mouseWhil = new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (okno.p.map.loadingImages.size() > 0) {
					System.out.println("������ ��������");
					return; // ���� ��������� �� ����� �������� ����������� - ����� ������
				}

				// ���� scale < 1.0, �� �� ����� ���������
				// ���� scale > 1.0, �� �� ����� �����������
				double scale = okno.p.map.scale;
				if (e.getWheelRotation() < 0)
					scale /= e.getScrollAmount() / 5.0; // ������� �����
				else
					scale *= e.getScrollAmount() / 5.0; // ������� ����
				if (scale > 2)
					scale = 2; // ������ ��� ����� �������� � ������ ��� 1:1,
								// ������� > 1 ������
				double koeffX = okno.p.getWidth() * 1.0 / okno.p.map.mapW;
				double koeffY = okno.p.getHeight() * 1.0 / okno.p.map.mapH;
				koeffX = Math.max(koeffX, koeffY); // if (koeffY > koeffX) koeffX = koeffY;
				scale = Math.max(scale, koeffX); // if (koeffX > scale) scale = koeffX;
				okno.p.map.scale = scale;
				okno.p.repaint();
			}
		};

	}
}
