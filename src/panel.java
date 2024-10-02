import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

import javax.swing.JPanel;

class panel extends JPanel {
	Map map;
	public GlobalLoadImg globalLoadImg;
	SizeAndScrol sizeAndScrol;
	Shifting shifting;


	// -------------------------------------------------------
	public panel() {
		globalLoadImg = new GlobalLoadImg();
		setLayout(null);
		shifting = new Shifting();

		map = new Map(this);
		float X = (int) (Math.random() * 500);
		float Y = (int) (Math.random() * 500);
		Pers.init(this, X, Y);

	}

	// -------------------------------------------------------
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		map.draw(g);
		Pers.draw(g);
		if(redactor.r!=null)
		redactor.r.draw(g);
		

		g.setColor(Color.white);
	}



}
