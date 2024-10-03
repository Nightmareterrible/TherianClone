import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class okno extends JFrame {
	static panel p;
	static Menu menu;
	public static final int windowWidth= 955;
	public static final int windowHight= 665;
	okno() {
		setBounds(0, 0, windowWidth, windowHight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p = new panel();
		setContentPane(p);
		setVisible(true);
		p.addMouseListener(p.shifting);
		p.addMouseMotionListener(p.shifting);
		p.addMouseWheelListener(p.shifting);


		//menu = new Menu();
		//menu.setLocation(0 ,520);
		//p.add(menu);
		
		
		
		int fps = (int)(1000/60);// fps
		Timer repaintTimer = new Timer(fps, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				okno.p.repaint();	
			}
		});
		repaintTimer.start();
		
		
		
	}
}