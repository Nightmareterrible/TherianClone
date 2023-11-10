import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	int X;
	int Y;
	int drowingX;
	int drowingY;

	public static void main(String[] args) {

		GLOBALS.mode = "editor";
		new okno();
		r = new redactor();

	}

	public redactor() {
		p = new Polygon();
		okno.p.addMouseListener(this);
		okno.p.addMouseMotionListener(this);
		String json;
		Gson gBilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		json = gBilder.toJson(location);//пока что не работает нужным образом
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		drowingX= e.getX();
		drowingY= e.getY();
		
		
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
			p.addPoint((int) (((e.getX() )/ Map.scale)-X), (int) ((e.getY()  )/ Map.scale-Y));//первый поинт где зажата левая кнопка мыши 
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {// long left button and pres right button
		
		if (e.getButton() == 1 && p != null && p.npoints >= 3) {//если точек больше 3 то создаем полигон 
			location.put(p, "forest");//по дефолту это будет лес 
			keys.add(p);	//также сохраняем ключик 
			
			p = new Polygon();
			leftMauseButonIsPresd = false;
		} else if (e.getButton() == 1) {
			p = new Polygon();
			leftMauseButonIsPresd = false;
			
		} else if (e.getButton() == 3 && p != null&&leftMauseButonIsPresd==true) {
			
			p.addPoint((int) ((e.getX() )/ Map.scale-X ), (int) ((e.getY()  )/ Map.scale)-Y);//при нажаии правой кнопки мыши добавляем поинт в полигон
			
		}
	}

	public void shiftXY(Point2D shift) {
		X += (int) shift.getX();
		Y += (int) shift.getY(); //rh
	}

	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.5f));
		for (int i = 0; i < keys.size(); i++) {

			for (int j = 0; j < keys.get(i).npoints ; j++) {
				
				if (keys.get(i).npoints > j + 1) {
					g2.drawLine(
							(int) ((Math.round(keys.get(i).xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(keys.get(i).xpoints[j + 1]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j + 1]) + Y) * Map.scale));
				} else {
					g2.drawLine(
							(int) ((Math.round(keys.get(i).xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(keys.get(i).xpoints[0]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[0]) + Y) * Map.scale));
					
				}
			}

		}
		if (leftMauseButonIsPresd==true&& p.npoints>=1) {
			
			g2.drawOval((int)((Math.round(p.xpoints[0]) + X) * Map.scale-5), 
					(int)((Math.round(p.ypoints[0]) + Y) * Map.scale-5), 
					10, 
					10
					);
			g2.drawOval((int)((Math.round(p.xpoints[0]) + X) * Map.scale-1), 
					(int)((Math.round(p.ypoints[0]) + Y) * Map.scale-1), 
					1, 
					1
					);
			if(drowingX!=0&&drowingY!=0)
			g2.drawLine(
					(int) ((Math.round(p.xpoints[p.npoints-1]) + X) * Map.scale),
					(int) ((Math.round(p.ypoints[p.npoints-1]) + Y) * Map.scale),
					drowingX,
					drowingY
);
			for (int i = 0; i < p.npoints; i++) {
				if (p.npoints > i + 1)
				g2.drawLine(
						(int) ((Math.round(p.xpoints[i]) + X) * Map.scale),
						(int) ((Math.round(p.ypoints[i]) + Y) * Map.scale),
						(int) ((Math.round(p.xpoints[i + 1]) + X) * Map.scale),
						(int) ((Math.round(p.ypoints[i + 1]) + Y) * Map.scale));
			}
		}
		
		
		

	}

}
