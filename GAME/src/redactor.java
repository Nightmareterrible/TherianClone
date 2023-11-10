import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.peer.KeyboardFocusManagerPeer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.net.ssl.KeyManager;
import javax.swing.event.MouseInputListener;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class redactor implements MouseMotionListener, MouseListener {

	Polygon p;
	boolean leftMauseButonIsPresd;// переменная для отслежвания передвижения мышы с зажатой левой кнопкой мыши
	HashMap<Polygon, String> location = new HashMap<>();// хранение полигона и его тип
	ArrayList<Polygon> keys = new ArrayList<>();// т.к. полигон являеться ключом его надо гдето хранить для этого нужен этот лист
	static redactor r;
	float X;
	float Y;

	public static void main(String[] args) {

		GLOBALS.mode = "editor";
		new okno();
		r = new redactor();

	}

	public redactor() {
		okno.p.addMouseListener(this);

		String json;
		Gson gBilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		json = gBilder.toJson(location);//пока что не работает нужным образом
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (e.getButton() == 1) {

			leftMauseButonIsPresd = true;//это нужно чтобы карта не двигалась во время редатирования 
			p = new Polygon();
			p.addPoint((int) (e.getX() / Map.scale + X), (int) (e.getY() / Map.scale + Y));//первый поинт где зажата левая кнопка мыши 

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {// long left button and pres right button
		
		if (e.getButton() == 1 && p != null && p.npoints >= 3) {//если точек больше 3 то создаем полигон 
			location.put(p, "forest");//по дефолту это будет лес 
			keys.add(p);	//также сохраняем ключик 	
			leftMauseButonIsPresd = false;
		} else if (e.getButton() == 1) {
			leftMauseButonIsPresd = false;

		} else if (e.getButton() == 3 && p != null) {
			p.addPoint((int) (e.getX() / Map.scale + X), (int) (e.getY() / Map.scale + Y));//при нажаии правой кнопки мыши добавляем поинт в полигон
		}
	}

	public void shiftXY(Point2D shift) {
		X += (float) shift.getX();
		Y += (float) shift.getY(); //rh
	}

	public void draw(Graphics g) {
		for (int i = 0; i < keys.size(); i++) {

			for (int j = 0; j < keys.get(i).npoints ; j++) {
				
				if (keys.get(i).npoints > j + 1) {
					g.drawLine(
							(int) ((Math.round(keys.get(i).xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(keys.get(i).xpoints[j + 1]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j + 1]) + Y) * Map.scale));
				} else {
					g.drawLine(
							(int) ((Math.round(keys.get(i).xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(keys.get(i).xpoints[0]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[0]) + Y) * Map.scale));
					
				}
			}

		}

	}

}
