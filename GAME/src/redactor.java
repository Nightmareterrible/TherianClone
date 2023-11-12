import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.peer.KeyboardFocusManagerPeer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.net.ssl.KeyManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class redactor implements MouseMotionListener, MouseListener {

	Polygon p;
	boolean leftMauseButonIsPresd;// переменная для отслежвания передвижения мышы с зажатой левой кнопкой мыши
	HashMap<Polygon, String> location = new HashMap<>();// хранение полигона и его тип
	ArrayList<Polygon> keys = new ArrayList<>();// т.к. полигон являеться ключом его надо гдето хранить для этого нужен
												// этот лист
	static redactor r;
	int X;
	int Y;
	int drowingX;
	int drowingY;
	boolean deletPoligon;

	public static void main(String[] args) {

		GLOBALS.mode = "editor";
		new okno();
		r = new redactor();
		okno.p.setFocusable(true);

	}

	public redactor() {
		p = new Polygon();
		String json = null;
		Gson gBilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		// json = gBilder.toJson(location);

		File f = new File("location.txt");

		if (f.exists())
			try {
				FileInputStream ff = new FileInputStream(f);
				InputStreamReader r = new InputStreamReader(ff);
				BufferedReader b = new BufferedReader(r);
				Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
				json = b.readLine();
				readJson(json);
				b.close();
				r.close();
				ff.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "read:\n" + e.toString());
				e.printStackTrace();
			}

		JButton btnNewButton_1 = new JButton("сохранить полигоны");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBounds(0, 0, 120, 70);
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeJson();

			}

		});
		JButton btnNewButton_2 = new JButton("удалить полигоны");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_2.setBounds(120, 0, 120, 70);
		btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deletPoligon = true;

			}

		});
		okno.p.add(btnNewButton_1);
		okno.p.add(btnNewButton_2);
		okno.p.addMouseListener(this);
		okno.p.addMouseMotionListener(this);

	}

	private void readJson(String json) {

		Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String[] jsons = json.split("<br>");

		for (int i = 0; i < jsons.length; i++) {

			Type listType = new TypeToken<ArrayList<POLIGON>>() {
			}.getType();
			ArrayList<POLIGON> arreyPoligonClas = new Gson().fromJson(jsons[i], listType);
			p = new Polygon();
			for (int j = 0; j < arreyPoligonClas.size(); j++) {
				p.addPoint(arreyPoligonClas.get(j).x, arreyPoligonClas.get(j).y);
			}
			location.put(p, arreyPoligonClas.get(0).TypeLocation);
			keys.add(p);
		}
		p = null;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////
	protected void writeJson() {
		String json = null;
		Gson g = new Gson();

		for (int j = 0; j < keys.size(); j++) {
			ArrayList<POLIGON> arreyPoligomClas = new ArrayList<POLIGON>();

			for (int i = 0; i < keys.get(j).npoints; i++) {

				POLIGON pol = new POLIGON();
				pol.x = keys.get(j).xpoints[i];
				pol.y = keys.get(j).ypoints[i];
				pol.TypeLocation = location.get(keys.get(j));
				arreyPoligomClas.add(pol);
			}
			if (json == null) {
				json = g.toJson(arreyPoligomClas) + "<br>";
			} else {
				json = json + g.toJson(arreyPoligomClas) + "<br>";
			}
		}

		File f = new File("location.txt");
		try {
			f.createNewFile();
			FileOutputStream ff = new FileOutputStream(f);
			OutputStreamWriter r = new OutputStreamWriter(ff);
			BufferedWriter w = new BufferedWriter(r);
			w.write(json);

			w.close();
			r.close();
			ff.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "write:\n" + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		drowingX = e.getX();
		drowingY = e.getY();

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
		drowingX = e.getX();
		drowingY = e.getY();
		if (e.getButton() == 1 && p != null) {
			leftMauseButonIsPresd = true;// это нужно чтобы карта не двигалась во время редатирования
			p.addPoint((int) (((e.getX()) / Map.scale) - X), (int) ((e.getY()) / Map.scale - Y));// первый поинт где
																									// зажата левая
																									// кнопка мыши

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {// long left button and pres right button
		drowingX = e.getX();
		drowingY = e.getY();
		if (!deletPoligon) {

			if (e.getButton() == 1 && p != null && p.npoints >= 3) {// если точек больше 3 то создаем полигон
				location.put(p, "forest");// по дефолту это будет лес
				keys.add(p); // также сохраняем ключик

				p = new Polygon();
				leftMauseButonIsPresd = false;
			} else if (e.getButton() == 1) {
				p = new Polygon();
				leftMauseButonIsPresd = false;

			} else if (e.getButton() == 3 && p != null && leftMauseButonIsPresd == true) {

				p.addPoint((int) ((e.getX()) / Map.scale - X), (int) ((e.getY()) / Map.scale) - Y);// при нажаии правой
																									// кнопки мыши
																									// добавляем
																									// поинт в полигон

			}
		}else {
			for (int i = 0; i < keys.size(); i++) {
				if(keys.get(i).contains((int)((e.getX()) / Map.scale - X) , (int) ((e.getY()) / Map.scale) - Y)) {
					location.remove(keys.get(i));
					keys.remove(i);
					
				}
			}
		}
	}

	public void shiftXY(Point2D shift) {
		X += (int) shift.getX();
		Y += (int) shift.getY(); // rh
	}

	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.5f));
		for (int i = 0; i < keys.size(); i++) {

			for (int j = 0; j < keys.get(i).npoints; j++) {

				if (keys.get(i).npoints > j + 1) {
					g2.drawLine((int) ((Math.round(keys.get(i).xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(keys.get(i).xpoints[j + 1]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j + 1]) + Y) * Map.scale));
				} else {
					g2.drawLine((int) ((Math.round(keys.get(i).xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(keys.get(i).xpoints[0]) + X) * Map.scale),
							(int) ((Math.round(keys.get(i).ypoints[0]) + Y) * Map.scale));

				}
			}

		}
		if (leftMauseButonIsPresd == true && p != null && p.npoints >= 1) {

			g2.drawOval((int) ((Math.round(p.xpoints[0]) + X) * Map.scale - 5),
					(int) ((Math.round(p.ypoints[0]) + Y) * Map.scale - 5), 10, 10);
			g2.drawOval((int) ((Math.round(p.xpoints[0]) + X) * Map.scale - 1),
					(int) ((Math.round(p.ypoints[0]) + Y) * Map.scale - 1), 1, 1);
			if (drowingX != 0 && drowingY != 0)
				g2.drawLine((int) ((Math.round(p.xpoints[p.npoints - 1]) + X) * Map.scale),
						(int) ((Math.round(p.ypoints[p.npoints - 1]) + Y) * Map.scale), drowingX, drowingY);
			for (int i = 0; i < p.npoints; i++) {
				if (p.npoints > i + 1)
					g2.drawLine((int) ((Math.round(p.xpoints[i]) + X) * Map.scale),
							(int) ((Math.round(p.ypoints[i]) + Y) * Map.scale),
							(int) ((Math.round(p.xpoints[i + 1]) + X) * Map.scale),
							(int) ((Math.round(p.ypoints[i + 1]) + Y) * Map.scale));
			}
		}

	}

	

}
