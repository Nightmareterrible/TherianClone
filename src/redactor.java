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
import java.beans.PropertyChangeListener;
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
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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


	MapLocation MapLoc;//класс с хранение всех данных о локации
	boolean leftMauseButonIsPresd;
	ArrayList<MapLocation> locations = new ArrayList<>();//все локации

	static redactor r;
	int X;
	int Y;
	int drowingX;
	int drowingY;
	boolean deletPoligon;
	JRadioButton radBtn[] = new JRadioButton[3];//типы локаций
	String[] nameLocation = { "forest", "city", "woter" };//имена локаций
	ButtonGroup bg = new ButtonGroup();

	public static void main(String[] args) {

		GLOBALS.mode = "editor";
		new okno();
		r = new redactor();
		okno.p.setFocusable(true);

	}

	public redactor() {





		readJson();// вызываем функцию по прочтению и записи локацый


		JButton btnNewButton_1 = new JButton("сохранить");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBounds(0, 0, 120, 70);
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeJson();

			}

		});
		JButton btnNewButton_2 = new JButton("удалить");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_2.setBounds(120, 0, 120, 70);
		btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (deletPoligon == true) {
					deletPoligon = false;
					return;
				}
				deletPoligon = true;


			}

		});

		for (int i = 0; i < nameLocation.length; i++) {
			radBtn[i] = new JRadioButton();
			radBtn[i].setText(nameLocation[i]);
			radBtn[i].setBounds(240, i * 30, 100, 30);
			bg.add(radBtn[i]);
			okno.p.add(radBtn[i]);

		}
		radBtn[0].setSelected(true);
		okno.p.add(btnNewButton_1);
		okno.p.add(btnNewButton_2);
		okno.p.addMouseListener(this);
		okno.p.addMouseMotionListener(this);

	}

	private void readJson() {
		String json=null;
		Gson gBilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		ArrayList<MapLocationToSave> tempLocations = new ArrayList<MapLocationToSave>();//временное хранилище перобразованых локаций для чтения из json
		File f = new File("location.txt");//читаем файл и записываем его в переменную json
		if (f.exists())
			try {
				FileInputStream ff = new FileInputStream(f);
				InputStreamReader r = new InputStreamReader(ff);
				BufferedReader b = new BufferedReader(r);
				json = b.readLine();
				b.close();
				r.close();
				ff.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "read:\n" + e.toString());
				e.printStackTrace();
			}
		if (json==null) {//если ничего то не идем дальше
			return;
		}
		tempLocations= gBilder.fromJson(json,new TypeToken<ArrayList<MapLocationToSave>>(){}.getType());//чтение Json с указанеим типа

		for (int i=0; i < tempLocations.size();i++){//тут записваем все прочитаное в основной класс с помщю которого все рабоет
				newLocation();
				for (int j = 0; j < tempLocations.get(i).x.length; j++) {
					MapLoc.p.addPoint(tempLocations.get(i).x[j],tempLocations.get(i).y[j]);
				}
				MapLoc.TypeLocation=tempLocations.get(i).TypeLocation;
				locations.add(MapLoc);
		}
		newLocation();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	protected void writeJson() {
		Gson gBilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();//ghjrfxtyfz dthcbz
		MapLocationToSave locToSave;
		ArrayList<MapLocationToSave> tempLocations = new ArrayList<MapLocationToSave>();//временное хранилище перобразованых локаций для хранения в json
		String json;//текст файла json
		if(locations.size()>0)
		for (int i = 0; i < locations.size(); i++) {//переносим все точки полигона в масивы в специальный класс т.к. Gson(библиотека) не умеет хранить Poligon
													//при добавлении новой перемнной в класс MapLocation нужно добавить в класс помошник и добавть ниже присвение перемнной из основного в помошник
			locToSave = new MapLocationToSave();

			/*locToSave.x = locations.get(i).p.xpoints.clone();//я не заню почему не работает это
			locToSave.y = locations.get(i).p.ypoints.clone();*///поэтому пришлось писать это:
			locToSave.x= new int[locations.get(i).p.npoints];
			locToSave.y= new int[locations.get(i).p.npoints];
			for (int j = 0; j < locations.get(i).p.npoints; j++) {
				locToSave.x[j] = locations.get(i).p.xpoints[j];
				locToSave.y[j] = locations.get(i).p.ypoints[j];
			}
			locToSave.TypeLocation=locations.get(i).TypeLocation;
			tempLocations.add(locToSave);
		}
		json = gBilder.toJson(tempLocations);


		File f = new File("location.txt");//создание и сохранение файла
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
	public void mouseMoved(MouseEvent e) {
		drowingX = e.getX();
		drowingY = e.getY();
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

		if (e.getButton() == 1 &&deletPoligon == false) {
			if(MapLoc==null) {
				newLocation();
			}

			if (MapLoc.p .npoints>=3&&
					MapLoc.p.xpoints[0] >= (int) (((e.getX()) / Map.scale) - X)-10 &&
					MapLoc.p.xpoints[0]<= (int) (((e.getX()) / Map.scale) - X)+10 &&
					MapLoc.p .ypoints[0] >= (int) (((e.getY()) / Map.scale) - Y)-10 &&
					MapLoc.p .ypoints[0] <= (int) (((e.getY()) / Map.scale) - Y)+10
					)//завершение полигона то есть проверка на нажатие на последнию точку

			{
				locations.add(MapLoc);
				newLocation();
				return;
			}


			MapLoc.p.addPoint((int) (((e.getX()) / Map.scale) - X), (int) ((e.getY()) / Map.scale - Y));//добавление точки полигона


		}else if (e.getButton() == 3 && deletPoligon == false && MapLoc !=null&&MapLoc.p.npoints>0){//уделение поледнего ребра полигона
			Polygon tempPoligon;
			tempPoligon = MapLoc.p;
			MapLoc.p = new Polygon();

			for (int i = 0; i < tempPoligon.npoints-1; i++) {
				MapLoc.p.addPoint(tempPoligon.xpoints[i],tempPoligon.ypoints[i]);

			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drowingX = e.getX();
		drowingY = e.getY();

	}

	public void shiftXY(Point2D shift) {
		X += (int) shift.getX();
		Y += (int) shift.getY();
	}
	public void newLocation() {//создание новой локации
		MapLoc = new MapLocation();
		MapLoc.p =new Polygon();
	}

	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2.5f));

		for (int i = 0; i < locations.size(); i++) {

			for (int j = 0; j < locations.get(i).p.npoints; j++) {

				/*for (int a = 0; a < radBtn.length; a++) {//цвета нарисованых полигонов подумать как перделать этот код плох т.к. используються буквы в условиях
					if (radBtn[a].isSelected()) {
						switch (location.get(keys.get(i))) {
						case ("forest"):
							g.setColor(Color.GREEN);
							break;
						case ("city"):
							g.setColor(Color.YELLOW);
							break;
						case ("woter"):
							g.setColor(Color.BLUE);
							break;

						}
					}
				}*/
				if (locations.get(i).p.npoints > j + 1) {//отврисовка полигона если он не редактируеться
					g2.drawLine((int) ((Math.round(locations.get(i).p.xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(locations.get(i).p.ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(locations.get(i).p.xpoints[j + 1]) + X) * Map.scale),
							(int) ((Math.round(locations.get(i).p.ypoints[j + 1]) + Y) * Map.scale));
				} else {
					g2.drawLine((int) ((Math.round(locations.get(i).p.xpoints[j]) + X) * Map.scale),
							(int) ((Math.round(locations.get(i).p.ypoints[j]) + Y) * Map.scale),
							(int) ((Math.round(locations.get(i).p.xpoints[0]) + X) * Map.scale),
							(int) ((Math.round(locations.get(i).p.ypoints[0]) + Y) * Map.scale));

				}
			}

		}

		if (MapLoc!=null &&MapLoc.p.npoints >= 1) {
			/*for (int a = 0; a < radBtn.length; a++) {//цвета нарисованых полигонов подумать как перделать, этот код плох т.к. используються буквы в условиях
				if (radBtn[a].isSelected()) {
					switch (radBtn[a].getText()) {
						case ("forest"):
							g.setColor(Color.GREEN);
							break;
						case ("city"):
							g.setColor(Color.YELLOW);
							break;
						case ("woter"):
							g.setColor(Color.BLUE);
							break;

					}
				}
			}*/

			g2.drawOval((int) ((Math.round(MapLoc.p.xpoints[0]) + X) * Map.scale - 5),
					(int) ((Math.round(MapLoc.p.ypoints[0]) + Y) * Map.scale - 5), 10, 10);//большая точка в начело полигона на менте создания пполигона
			g2.drawOval((int) ((Math.round(MapLoc.p.xpoints[0]) + X) * Map.scale - 1),
					(int) ((Math.round(MapLoc.p.ypoints[0]) + Y) * Map.scale - 1), 1, 1);//маленькая точка в начело полигона на менте создания пполигона
			if (drowingX != 0 && drowingY != 0)//линия идущяя за мышкой в момент создания полигона
				g2.drawLine((int) ((Math.round(MapLoc.p.xpoints[MapLoc.p.npoints - 1]) + X) * Map.scale),
						(int) ((Math.round(MapLoc.p.ypoints[MapLoc.p.npoints - 1]) + Y) * Map.scale), drowingX, drowingY);

			for (int i = 0; i < MapLoc.p.npoints; i++) {// отрисовка на момент создания и редактирования полигона

				if (MapLoc.p.npoints > i + 1){

					g2.drawLine((int) ((Math.round(MapLoc.p.xpoints[i]) + X) * Map.scale),
							(int) ((Math.round(MapLoc.p.ypoints[i]) + Y) * Map.scale),
							(int) ((Math.round(MapLoc.p.xpoints[i + 1]) + X) * Map.scale),
							(int) ((Math.round(MapLoc.p.ypoints[i + 1]) + Y) * Map.scale));
				}
			}
		}

	}

}
//locations.get(locations.size()-1).p. .... надо в отдельную функцию запихнуть