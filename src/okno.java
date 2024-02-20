import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class okno extends JFrame {
	static panel p;
	static Menu menu;
	okno() {
		setBounds(0, 0, 900, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		p = new panel();
		setContentPane(p);
		//setResizable(false);
		setVisible(true); // !!! setVisible ���� � ����� �����, ����� ��������
						  // ��� ������ ����� �� �������������
		
		menu = new Menu();
		menu.setLocation(0 ,520);
		p.add(menu);
		
		
		
		int KadrVSek = (int)(1000/60);// fps
				
		Timer repaintTimer = new Timer(KadrVSek, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				okno.p.repaint();	
			}
		});
		repaintTimer.start();
		
		
		
	}
}