import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.peer.KeyboardFocusManagerPeer;
import java.util.HashMap;

import javax.net.ssl.KeyManager;
import javax.swing.event.MouseInputListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class redactor implements MouseMotionListener, MouseListener{
	
			
	Polygon p;
	boolean leftMauseButonIsPresd;//переменная для отслежвания передвижения мышы с зажатой левой кнопкой мыши
	HashMap<Polygon, String> location = new HashMap<>();//хранение полигона и его тип
	static redactor r;
	public static void main(String[] args)  {
		
		GLOBALS.mode = "editor";
		new okno();
		r = new redactor();//jbwuevgfu
		System.out.println("neibniwbnvfew");
		//bfbdfbdf
		
	}
	public redactor() {
		okno.p.addMouseListener(this);
		
		String json;
		Gson gBilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		json = gBilder.toJson(location);
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
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e.getButton());
		if(e.getButton()==1) {
			
			leftMauseButonIsPresd=true;
			p = new Polygon() ;
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==1&&p!=null &&p.npoints>=3){
			location.put(p, "forest");
			System.out.println("location is added");
			leftMauseButonIsPresd=false;
		}else if(e.getButton()==3){
			p.addPoint(e.getX(), e.getY());
		}
	}
	public void draw(Graphics g) {
		
	}


}
