import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GlobalLoadImg {

    public GlobalLoadImg() {

    }

    public Image MapTileLoad(int tileX, int tileY) throws Exception {// загрузка тайлов карты

        Image MapImages = null;
        try {
            MapImages = ImageIO.read(this.getClass().getResource("/img/maps/map" + tileX + "," + tileY + ".png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return MapImages;
    }

    public Image CharacterLoad() {// Загрузка игрока
        Image CharacterImage;
        try {
            CharacterImage = ImageIO.read(this.getClass().getResource("/img/Character.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CharacterImage;
    }

    public Image InterfaceLoad(int tileX, int tileY) {// загрузка интерфейса (пока-что не  работает)
        Image InterfaceImages;
        try {
            InterfaceImages = ImageIO.read(this.getClass().getResource("/img/maps/map" + tileX + "," + tileY + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return InterfaceImages;
    }

}
