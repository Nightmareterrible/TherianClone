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

    static int frames = 0;
    static int fps = 0;
    private static Map<String, BufferedImage> mapOfBfImage = new HashMap<>();
    private static Map<String, ImageInputStream> mapOfInpStream = new HashMap<>();

    private static LinkedList<String> cacheNameStream = new LinkedList<>();


    public MapLoder(Camera camera) {
        this.camera = camera;
        loder(camera);
    }

    public static void loder(Camera camera) {
        final int MAX_COUNT_TILE_ON_WINDOW = 4;
        final int TILE_WIDTH = 640;
        final int TILE_HIGHT = 360;
        Runnable task = new Runnable() {
            int updatTime = 1000 / 10;
            double scalel;
            Rectangle viewport;

            @Override
            public void run() {
                while (true) {
                    scalel = camera.getScaleLevel();
                    viewport = camera.getViewport();
                    int tileX = (int) Math.floor(viewport.getX() / TILE_WIDTH);//получение тайлов которые мы видим
                    int tileY = (int) Math.floor(viewport.getY() / TILE_HIGHT);
                    int lodLevel = 0;//TODO получение уровня приблежения


                    for (int i = tileX; i < MAX_COUNT_TILE_ON_WINDOW + tileX; i++) {
                        for (int j = tileY; j < MAX_COUNT_TILE_ON_WINDOW + tileY; j++) {
                            System.out.println("Thread-0 update    x= "+(tileX + i )+"    j= "+(tileY + j ));
                            if (tileX + i <= 23 && tileY + j <= 27) {
                                if (!mapOfBfImage.containsKey((tileX + i) + "," + (tileY + j))) {
                                    if (mapOfInpStream.containsKey((tileX + i) + "," + (tileY + j))) {
                                        ImageReader reader = ImageIO.getImageReaders(mapOfInpStream.get(i + "," + j)).next();
                                        try {
                                            mapOfBfImage.put(tileX + "," + tileY, reader.read(0));
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    } else {
                                        BufferedImage bf = null;
                                        try {
                                            bf = ImageIO.read(new File("tiles/LOD" + lodLevel + "/tile_" + (tileX + i) + "_" + (tileY + j) + ".png"));
                                        } catch (IOException e) {
                                            System.out.println("not have img" + "                  " + "tiles/LOD" + lodLevel + "/tile_" + (tileX + i) + "_" + (tileY + j) + ".png");
                                            // throw new RuntimeException(e);

                                        }

                                        mapOfBfImage.put((tileX + i) + "," + (tileY + j), bf);
                                        bf.flush();
                                        System.out.println((tileX + i) + "," + (tileY + j));
                                    }
                                }
                            }
                        }
                    }
                    //             System.out.println("new cicel lode");
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
