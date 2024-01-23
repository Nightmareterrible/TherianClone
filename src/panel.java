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

	public Button btn;
	Map map;
	double koeff = 1.0;
	SizeAndScrol sizeAndScrol;

	public void createskroolov() {
		sizeAndScrol = new SizeAndScrol();
		addMouseMotionListener(sizeAndScrol.mouseMotionAd);
		addMouseListener(sizeAndScrol.mouseAd);
		addMouseWheelListener(sizeAndScrol.mouseWhil);

	}

	// -------------------------------------------------------

	// -------------------------------------------------------
	public panel() {

		setLayout(null);
		float X = (int) (Math.random() * 500);
		float Y = (int) (Math.random() * 500);
		map = new Map(this);
		Pers.init(this, X, Y);

		createskroolov();

		/*
		System.out.println("начали загрузку");
		try {
			// Загрузить ресурсы в статическом методе - не получится. Поэтому -
			// грузим отдельным методом
			map.loadImage(this.getClass().getResource("img/map1.jpg"));
			// map.loadImage(this.getClass().getResource("img/map2.jpg"));
			// map.loadImage(this.getClass().getResource("img/86a463b2e2583ca508e968c13b91d3c0.jpg"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("завершили загрузку");
		*/
		try {
			Pers.loadImage(this.getClass().getResource("img/pers.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// -------------------------------------------------------
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		map.draw(g);
		if (map.villageList != null) {
			for (int i = 0; i < map.villageList.size(); i++) {
				map.villageList.get(i).draw(g);
			}

		}
		Pers.draw(g);
		if(redactor.r!=null)
		redactor.r.draw(g);
		
		if (sizeAndScrol.paintmovecircle) {
			int W = (int) GLOBALS.drag_circle_diameter;
			g.setColor(Color.yellow);
			g.drawOval(sizeAndScrol.pressedmx - W / 2 - 1,
					sizeAndScrol.pressedmx - W / 2, W, W);
			g.setColor(Color.white);
			g.drawOval(sizeAndScrol.pressedmx - W / 2 + 1,
					sizeAndScrol.pressedmx - W / 2 + 1, W, W);
			g.setColor(Color.black);
			g.drawOval(sizeAndScrol.pressedmx - W / 2, sizeAndScrol.pressedmx
					- W / 2, W, W);
		}
		g.setColor(Color.white);
	}

}
