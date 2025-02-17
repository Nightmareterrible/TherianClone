package Map;

import Camera.Camera;
import game.GLOBALS;

import java.awt.image.VolatileImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

class MapManager {
    public static void main(String[] args) {
    }

    Camera camera;

    public MapManager() {
        init();
    }

    public void init() {
        Properties p = loadProperty();

        camera = new Camera(Integer.parseInt( p.getProperty("LOD0.Width")),Integer.parseInt(  p.getProperty("LOD0.Height")));

        MapLoder ml = new MapLoder(camera);
        camera.addObserver(ml);

    }

    private Properties loadProperty() {
        File fileProperty = new File("tiles/MapData.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileProperty));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }


}