package deletedClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Класс GameObject представляет объект в мире
class GameObject {
    int x, y, width, height;
    Color color;

    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(Graphics2D g, Camera camera) {
        // Преобразуем мировые координаты в экранные
        int screenX = camera.getScreenX(x);
        int screenY = camera.getScreenY(y);
        int screenWidth = (int) (width * camera.getZoom());
        int screenHeight = (int) (height * camera.getZoom());

        // Отрисовываем объект
        g.setColor(color);
        g.fillRect(screenX, screenY, screenWidth, screenHeight);
    }
}

// Класс Camera управляет преобразованием координат и обработкой событий мыши
class Camera implements MouseListener, MouseMotionListener, MouseWheelListener {
    private int x, y; // Позиция камеры в мире
    private float zoom; // Масштаб
    private int lastMouseX, lastMouseY; // Последние координаты мыши для перетаскивания
    private boolean isDragging; // Флаг перетаскивания камеры

    public Camera() {
        this.x = 0;
        this.y = 0;
        this.zoom = 1.0f;
        this.isDragging = false;
    }

    // Преобразует мировую координату X в экранную
    public int getScreenX(int worldX) {
        return (int) ((worldX - x) * zoom);
    }

    // Преобразует мировую координату Y в экранную
    public int getScreenY(int worldY) {
        return (int) ((worldY - y) * zoom);
    }

    // Преобразует экранную координату X в мировую
    public int getWorldX(int screenX) {
        return (int) (screenX / zoom + x);
    }

    // Преобразует экранную координату Y в мировую
    public int getWorldY(int screenY) {
        return (int) (screenY / zoom + y);
    }

    // Возвращает текущий масштаб
    public float getZoom() {
        return zoom;
    }

    // Устанавливает масштаб
    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    // Перемещает камеру
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    // Обработка событий мыши
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            //isDragging = true;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            //isDragging = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

            // Вычисляем разницу в мировых координатах
            int currentMouseX = e.getX();
            int currentMouseY = e.getY();
            int dx = getWorldX(currentMouseX) - getWorldX(lastMouseX);
            int dy = getWorldY(currentMouseY) - getWorldY(lastMouseY);

            // Двигаем камеру в противоположную сторону
            move(-dx, -dy);

            // Обновляем последние координаты мыши
            lastMouseX = currentMouseX;
            lastMouseY = currentMouseY;

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        if (rotation < 0) {
            setZoom(zoom * 1.1f); // Приближение
        } else {
            setZoom(zoom / 1.1f); // Отдаление
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}

// Основной класс игры
public class Game extends JPanel {
    private List<GameObject> objects;
    private Camera camera;

    public Game() {
        // Инициализация камеры
        camera = new Camera();

        // Создаем несколько объектов
        objects = new ArrayList<>();
        objects.add(new GameObject(100, 100, 50, 50, Color.RED));
        objects.add(new GameObject(200, 200, 50, 50, Color.BLUE));
        objects.add(new GameObject(300, 300, 50, 50, Color.GREEN));

        // Настройка окна
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);

        // Добавляем слушатели событий мыши
        addMouseListener(camera);
        addMouseMotionListener(camera);
        addMouseWheelListener(camera);

        // Запуск цикла обновления экрана (60 FPS)
        Timer timer = new Timer(1000 / 60, e -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Отрисовываем все объекты с учетом камеры
        for (GameObject obj : objects) {
            obj.draw(g2d, camera);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Game with Camera");
        Game game = new Game();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}