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


public class redactor implements MouseMotionListener, MouseListener{
	
	Polygon p;
	boolean leftMauseButonIsPresd;//переменная для отслежвания передвижения мышы с зажатой левой кнопкой мыши
	HashMap<Polygon, String> location = new HashMap<>();//хранение полигона и его тип
	public static void main(String[] args)  {
		GLOBALS.mode = "editor";
		new okno();
		redactor r = new redactor();
		
	}
	public redactor() {
		okno.p.addMouseListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		 System.out.println("cbsba");
		 p = new Polygon() ;
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(p.npoints>3){
			location.put(p, "forest");
		}
	}


}
