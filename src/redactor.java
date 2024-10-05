import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class redactor implements MouseMotionListener, MouseListener {


    MapLocation mapLocations;//класс с хранение всех данных о локации
    ArrayList<MapLocation> locations = new ArrayList<>();//все локации

    static redactor r;

    int drowingX;//координаты линия идущяя за мышкой в момент создания полигона
    int drowingY;//координаты линия идущяя за мышкой в момент создания полигона
    boolean deletPoligon;
    JRadioButton radBtn[] = new JRadioButton[3];//типы локаций
    String[] nameLocation = {"forest", "city", "woter"};//имена локаций
    ButtonGroup bg = new ButtonGroup();

    public static void main(String[] args) {

        GLOBALS.mode = "editor";
        new okno();
        r = new redactor();
        okno.p.setFocusable(true);

    }

    public redactor() {
        readJson();// вызываем функцию по прочтению
        JButton btnNewButton_1 = new JButton("save");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_1.setBounds(0, 0, 120, 70);
        btnNewButton_1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                writeJson();

            }

        });
        JButton btnNewButton_2 = new JButton("delet");
        btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_2.setBounds(120, 0, 120, 70);
        btnNewButton_2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*if (deletPoligon == true) {
                    deletPoligon = false;
                    return;
                }
                deletPoligon = true;*/
                deletPoligon = !deletPoligon;
                btnNewButton_2.setFocusable(false);
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
        String json = null;
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
        if (json == null) {//если ничего то не идем дальше
            return;
        }
        tempLocations = gBilder.fromJson(json, new TypeToken<ArrayList<MapLocationToSave>>() {
        }.getType());//чтение Json с указанеим типа

        for (int i = 0; i < tempLocations.size(); i++) {//тут записваем все прочитаное в основной класс с помщю которого все рабоет
            newLocation();
            for (int j = 0; j < tempLocations.get(i).x.length; j++) {
                mapLocations.p.addPoint(tempLocations.get(i).x[j], tempLocations.get(i).y[j]);
            }
            mapLocations.TypeLocation = tempLocations.get(i).TypeLocation;
            locations.add(mapLocations);
        }
        newLocation();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    protected void writeJson() {
        Gson gBilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        MapLocationToSave mapLocationsToSave;
        ArrayList<MapLocationToSave> tempLocations = new ArrayList<MapLocationToSave>();//временное хранилище перобразованых локаций для хранения в json
        String json;
        if (locations.size() > 0)
            for (int i = 0; i < locations.size(); i++) {//переносим все точки полигона в масивы в специальный класс т.к. Gson(библиотека) не умеет хранить Poligon
                //при добавлении новой перемнной в класс MapLocation нужно добавить в класс помошник и добавть ниже присвение перемнной из основного в помошник
                mapLocationsToSave = new MapLocationToSave();

			    /*mapLocationsToSave.x = locations.get(i).p.xpoints.clone();//я не заню почему не работает это
			    mapLocationsToSave.y = locations.get(i).p.ypoints.clone();*///поэтому пришлось писать это:
                mapLocationsToSave.x = new int[locations.get(i).p.npoints];
                mapLocationsToSave.y = new int[locations.get(i).p.npoints];
                for (int j = 0; j < locations.get(i).p.npoints; j++) {
                    mapLocationsToSave.x[j] = locations.get(i).p.xpoints[j];
                    mapLocationsToSave.y[j] = locations.get(i).p.ypoints[j];
                }
                mapLocationsToSave.TypeLocation = locations.get(i).TypeLocation;
                tempLocations.add(mapLocationsToSave);
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
        createLocationAddPointLocation(e);

    }

    private void createLocationAddPointLocation(MouseEvent e) {


        if (e.getButton() == 1 && deletPoligon == false) {

            if (mapLocations == null) {
                newLocation();
            }
            if (mapLocations.p.npoints >= 3 &&
                    Shifting.getWindowPointFromMapCoordinatesX( mapLocations.p.xpoints[0]) >= e.getX() - 10 &&
                    Shifting.getWindowPointFromMapCoordinatesX( mapLocations.p.xpoints[0]) <= e.getX() + 10 &&
                    Shifting.getWindowPointFromMapCoordinatesY( mapLocations.p.ypoints[0]) >= e.getY() - 10 &&
                    Shifting.getWindowPointFromMapCoordinatesY( mapLocations.p.ypoints[0]) <= e.getY() + 10)//завершение полигона то есть проверка на нажатие на последнию точку

            {
                locations.add(mapLocations);
                newLocation();
                return;
            }


            mapLocations.p.addPoint(Shifting.getCoordinatesOnMapFromWindowPointX(e.getX()), Shifting.getCoordinatesOnMapFromWindowPointY(e.getY()));//добавление точки полигона


        } else if (e.getButton() == 3 && deletPoligon == false && mapLocations != null && mapLocations.p.npoints > 0) {//уделение поледнего ребра полигона
            Polygon tempPoligon;
            tempPoligon = mapLocations.p;
            mapLocations.p = new Polygon();

            for (int i = 0; i < tempPoligon.npoints - 1; i++) {
                mapLocations.p.addPoint(tempPoligon.xpoints[i], tempPoligon.ypoints[i]);

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drowingX = e.getX();
        drowingY = e.getY();

    }


    public void newLocation() {//создание новой локации
        mapLocations = new MapLocation();
        mapLocations.p = new Polygon();
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2.5f));

        for (int i = 0; i < locations.size(); i++) {

            for (int j = 0; j < locations.get(i).p.npoints; j++) {
                if (locations.get(i).p.npoints > j + 1) {//отврисовка полигона если он не редактируеться
                    g2.drawLine(Shifting.getWindowPointFromMapCoordinatesX(locations.get(i).p.xpoints[j]),
                            Shifting.getWindowPointFromMapCoordinatesY(locations.get(i).p.ypoints[j]),
                            Shifting.getWindowPointFromMapCoordinatesX(locations.get(i).p.xpoints[j + 1]),
                            Shifting.getWindowPointFromMapCoordinatesY(locations.get(i).p.ypoints[j + 1]));
                } else {
                    g2.drawLine(Shifting.getWindowPointFromMapCoordinatesX(locations.get(i).p.xpoints[j]),
                            Shifting.getWindowPointFromMapCoordinatesY(locations.get(i).p.ypoints[j]),
                            Shifting.getWindowPointFromMapCoordinatesX(locations.get(i).p.xpoints[0]),
                            Shifting.getWindowPointFromMapCoordinatesY(locations.get(i).p.ypoints[0]));
                }
            }
        }
            if (mapLocations != null && mapLocations.p.npoints >= 1) {//
                g2.drawOval(Shifting.getWindowPointFromMapCoordinatesX(mapLocations.p.xpoints[0]) - 5,
                        Shifting.getWindowPointFromMapCoordinatesY(mapLocations.p.ypoints[0]) - 5, 10, 10);//большая точка в начело полигона на менте создания пполигона

                g2.drawOval(Shifting.getWindowPointFromMapCoordinatesX(mapLocations.p.xpoints[0]) - 1,
                        Shifting.getWindowPointFromMapCoordinatesY(mapLocations.p.ypoints[0]) - 1, 1, 1);//маленькая точка в начело полигона на менте создания пполигона
                if (drowingX != 0 && drowingY != 0) {//линия идущяя за мышкой в момент создания полигона

                    g2.drawLine(Shifting.getWindowPointFromMapCoordinatesX(mapLocations.p.xpoints[mapLocations.p.npoints - 1]),
                            Shifting.getWindowPointFromMapCoordinatesY(mapLocations.p.ypoints[mapLocations.p.npoints - 1]), drowingX, drowingY);
                }

                for (int i = 0; i < mapLocations.p.npoints; i++) {// отрисовка на момент создания и редактирования полигона

                    if (mapLocations.p.npoints > i + 1) {
                        g2.drawLine(Shifting.getWindowPointFromMapCoordinatesX(mapLocations.p.xpoints[i]),
                                Shifting.getWindowPointFromMapCoordinatesY(mapLocations.p.ypoints[i]),
                                Shifting.getWindowPointFromMapCoordinatesX(mapLocations.p.xpoints[i + 1]),
                                Shifting.getWindowPointFromMapCoordinatesY(mapLocations.p.ypoints[i + 1]));
                    }
                }
            }


    }

}
