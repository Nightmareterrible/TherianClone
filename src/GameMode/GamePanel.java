package GameMode;

import Camera.Camera;
import PawnObject.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Панель для отрисовки игровых объектов
 */
public class GamePanel extends JPanel {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<Drawable> drawObjects = new ArrayList<>();
    private Camera camera;
    private Scene scene;

    public GamePanel(Camera camera) {
        this.camera = camera;
        initializeObjects();
    }

    public GamePanel() {
        initializeObjects();
    }


    /**
     * Инициализирует игровые объекты
     */
    public void initializeObjects() {
    }

    /**
     * Настраивает обработку ввода
     */
    public void setupInput(Object object) {
        addMouseListener((MouseListener) object);
        addMouseMotionListener((MouseMotionListener) object);
        addMouseWheelListener((MouseWheelListener) object);
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public ArrayList<Drawable> getDrawObjects() {
        return drawObjects;
    }

    public void setDrawObjects(ArrayList<Drawable> drawObjects) {
        this.drawObjects = drawObjects;
    }

    public void addDrawable(Drawable drawable) {
        drawObjects.add(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        drawObjects.remove(drawable);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (GameObject obj : gameObjects) {
            if (camera != null)
                obj.render(g2d, camera);
        }

        if (scene != null) {
            for (Drawable drawable : getDrawObjects()) {
                try {
                    drawable.draw(g);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}