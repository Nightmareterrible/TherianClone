package PawnObject;

import Camera.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Представляет игровой объект в мировых координатах
 */
public class GameObject {
    private final int worldX;
    private final int worldY;
    private final int width;
    private final int height;
    private final BufferedImage icon;

    /**
     * Создает новый игровой объект
     *
     * @param worldX X-координата в мировом пространстве
     * @param worldY Y-координата в мировом пространстве
     * @param width  Ширина объекта в мировых единицах
     * @param height Высота объекта в мировых единицах
     */
    public GameObject(int worldX, int worldY, int width, int height, BufferedImage icon) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.width = width;
        this.height = height;
        this.icon = icon;
    }

    /**
     * Отрисовывает объект на экране с учетом текущих параметров камеры
     *
     * @param graphics Контекст графики для отрисовки
     */
    public void render(Graphics2D graphics, Camera camera) {
        Point screenPosition = camera.convertWorldToScreen(worldX, worldY);
        Dimension screenSize = camera.convertWorldToScreenSize(width, height);
        if (icon != null) {
            graphics.drawImage(icon, screenPosition.x - screenSize.width / 2,
                    screenPosition.y - screenSize.height / 2, screenSize.width, screenSize.height, null);
        }else{
            graphics.setColor(Color.RED);
            graphics.fillRect(screenPosition.x, screenPosition.y, screenSize.width, screenSize.height);
        }
    }


    public int getHeight() {
        return height;
    }



    public int getWidth() {
        return width;
    }



    public int getWorldY() {
        return worldY;
    }



    public int getWorldX() {
        return worldX;
    }


    public BufferedImage getIcon() {
        return icon;
    }

}
