package Map;


import Camera.Camera;
import Camera.CameraListener;
import GameMode.Drawable;
import GameMode.GamePanel;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MapLoder extends JPanel implements CameraListener, Drawable {
    Camera camera;


    private static Map<String, BufferedImage> mapOfBfImage = new ConcurrentHashMap<>();
    private static Map<String, ImageInputStream> mapOfInpStream = new HashMap<>();

    private static LinkedList<String> cacheNameStream = new LinkedList<>();
    private static LinkedList<String> cacheNameImage = new LinkedList<>();

    public MapLoder(Camera camera) {
        this.camera = camera;
        loder(camera);
    }

    final static double TILE_WIDTH = 640;
    final static double TILE_HIGHT = 360;

    public static void loder(Camera camera) {
        final int MAX_COUNT_TILE_ON_WINDOW = 6;

        Runnable task = new Runnable() {
            int updatTime = 1000 / 10;
            double scalel;
            Rectangle viewport;

            @Override
            public void run() {
                while (true) {
                    scalel = camera.getScaleLevel();
                    viewport = camera.getViewport();
                    int tileX = (int) Math.floor(camera.getWorldX() / TILE_WIDTH);//получение тайлов которые мы видим
                    int tileY = (int) Math.floor(camera.getWorldY() / TILE_HIGHT);

                    int lodLevel = 0;//TODO получение уровня приблежения
                    for (int i = tileX - 1; i < MAX_COUNT_TILE_ON_WINDOW + tileX; i++) {
                        for (int j = tileY - 1; j < MAX_COUNT_TILE_ON_WINDOW + tileY; j++) {
                            if (i <= 23 && j <= 27 && i >= 0 && j >= 0) {
                               // System.out.println(i + "      " + j);
                                if (!mapOfBfImage.containsKey((i) + "," + (j))) {
                                    try {
                                        mapOfBfImage.put(i + "," + j, ImageIO.read(new File("tiles/LOD0/tile_" + (i) + "_" + (j) + ".png")));
                                        cacheNameImage.add(i + "," + j);

                                        if (cacheNameImage.size() > 64) {
                                            System.out.println("CLEARNG   "+cacheNameImage.size() +"    "+cacheNameImage.getFirst());
                                            mapOfBfImage.remove(cacheNameImage.getFirst());
                                            cacheNameImage.removeFirst();
                                        }
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }else{
                                    cacheNameImage.remove(i + "," + j);
                                    cacheNameImage.add(i + "," + j);
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(updatTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread th = new Thread(task);
        th.start();
    }


    @Override
    public void updateViewport() {

    }


    @Override
    public void draw(Graphics g) throws IOException {

        for (String name : mapOfBfImage.keySet()) {
            int x = (int) (Integer.parseInt(name.split(",")[0]) * 640 - camera.getWorldX());
            int y = (int) (Integer.parseInt(name.split(",")[1]) * 360 - camera.getWorldY());
            g.drawImage(mapOfBfImage.get(name), x, y, null);
            g.drawRect(x, y, 640, 360);
            g.drawString(name, x + 100, y + 100);

        }

    }
}
