package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GlobalLoadImg {

    public GlobalLoadImg() {

    }

    public BufferedImage MapTileLoad(int tileX, int tileY) throws Exception {// загрузка тайлов карты

        BufferedImage MapImages = null;
        try {
            MapImages = ImageIO.read(this.getClass().getResource("/img/maps/map" + tileX + "," + tileY + ".png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return MapImages;
    }

    public static BufferedImage CharacterLoad() {// Загрузка игрока
        BufferedImage CharacterImage = null;
        try {
            CharacterImage = ImageIO.read(GlobalLoadImg.class.getResource("/img/Character.png"));
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
