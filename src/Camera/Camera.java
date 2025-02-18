package Camera;

import game.Okno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Управляет отображением игрового мира, преобразованием координат и обработкой ввода
 */
public class Camera implements MouseListener, MouseMotionListener, MouseWheelListener {
    /**
     * Минимальный допустимый масштаб (1/16)
     */
    public static final double MIN_SCALE = 0.25;
    /**
     * Максимальный допустимый масштаб (1:1)
     */
    public static final double MAX_SCALE = 1.0;

    private double worldX;
    private double worldY;
    private double scaleLevel;
    private Rectangle worldBounds;
    private Point lastMousePosition;
    private Point lastDragPoint;
    private int screenWidth, screenHeight;


    /**
     * Создает новую камеру с указанными границами мира
     *
     * @param initialWidth  Ширина игрового мира
     * @param initialHeight Высота игрового мира
     */
    public Camera(int initialWidth, int initialHeight) {
        this.scaleLevel = 1;
        this.worldBounds = new Rectangle(0, 0, initialWidth, initialHeight);
        System.out.println("Camera has bean created");
    }

    /**
     * Преобразует мировые координаты в экранные
     *
     * @param worldX X-координата в мире
     * @param worldY Y-координата в мире
     * @return Точка на экране
     */
    public Point convertWorldToScreen(double worldX, double worldY) {
        return new Point(
                (int) ((worldX - this.worldX) * scaleLevel),
                (int) ((worldY - this.worldY) * scaleLevel)
        );
    }

    /**
     * Преобразует экранные координаты в мировые
     *
     * @param screenX X-координата на экране
     * @param screenY Y-координата на экране
     * @param scale   Текущий масштаб
     * @param cameraX X-позиция камеры
     * @param cameraY Y-позиция камеры
     * @return Точка в мировых координатах
     */
    public static Point convertScreenToWorld(int screenX, int screenY, double scale, double cameraX, double cameraY) {
        return new Point(
                (int) (screenX / scale + cameraX),
                (int) (screenY / scale + cameraY)
        );
    }

    /**
     * Преобразует размеры из мировых в экранные
     *
     * @param width  Ширина в мире
     * @param height Высота в мире
     * @return Размеры на экране
     */
    public Dimension convertWorldToScreenSize(int width, int height) {
        return new Dimension(
                (int) (width * scaleLevel),
                (int) (height * scaleLevel)
        );
    }


    /**
     * Устанавливает абсолютную позицию камеры в мире
     *
     * @param x Новая X-координата
     * @param y Новая Y-координата
     */
    public void setWorldPosition(double x, double y) {
        double maxX = worldBounds.x + worldBounds.width - (screenWidth / scaleLevel);
        double maxY = worldBounds.y + worldBounds.height - (screenHeight / scaleLevel);

        worldX = clampValue(x, worldBounds.x, maxX);
        worldY = clampValue(y, worldBounds.y, maxY);
    }


    /**
     * Обновляет позицию камеры на основе смещения экранных координат
     *
     * @param screenDeltaX Смещение по X в экранных координатах
     * @param screenDeltaY Смещение по Y в экранных координатах
     */
    private void updateCameraPosition(int screenDeltaX, int screenDeltaY) {
        Point worldDelta = calculateWorldDelta(screenDeltaX, screenDeltaY);
        setWorldPosition(worldX - worldDelta.x, worldY - worldDelta.y);
    }


    /**
     * Изменяет масштаб с учетом множителя
     *
     * @param multiplier Множитель изменения масштаба (>1 - увеличение, <1 - уменьшение)
     */
    public void adjustScale(double multiplier) {
        double newScale = scaleLevel * multiplier;
        scaleLevel = Math.min(MAX_SCALE, Math.max(MIN_SCALE, newScale));
    }

    /**
     * Ограничивает значение в заданном диапазоне
     *
     * @param value Исходное значение
     * @param min   Минимальное допустимое значение
     * @param max   Максимальное допустимое значение
     * @return Значение в диапазоне [min, max]
     */
    public static double clampValue(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }


    /**
     * @return Текущий уровень масштаба
     */
    public double getScaleLevel() {
        return scaleLevel;
    }

    /**
     * Устанавливает новые границы игрового мира
     *
     * @param bounds Прямоугольник, описывающий границы мира
     */
    public void setWorldBounds(Rectangle bounds) {
        this.worldBounds = bounds;
        setWorldPosition(worldX, worldY);
    }


    /**
     * Вычисляет смещение в мировых координатах на основе экранного смещения
     *
     * @param screenDeltaX Смещение по X в пикселях
     * @param screenDeltaY Смещение по Y в пикселях
     * @return Смещение в мировых единицах
     */
    private Point calculateWorldDelta(int screenDeltaX, int screenDeltaY) {
        return new Point(
                (int) (screenDeltaX / scaleLevel),
                (int) (screenDeltaY / scaleLevel)
        );
    }

    /**
     * Возвращает видимую область мира в мировых координатах
     *
     * @param screenWidth  Ширина экранной области
     * @param screenHeight Высота экранной области
     * @return Прямоугольник видимой области в мировых координатах
     */
    public Rectangle getViewport(int screenWidth, int screenHeight) {
        // Рассчитываем размер видимой области в мировых координатах
        double visibleWidth = screenWidth / scaleLevel;
        double visibleHeight = screenHeight / scaleLevel;

        // Рассчитываем границы с учетом ограничений мира
        double clampedX = clampX(worldX);
        double clampedY = clampY(worldY);

        return new Rectangle(
                (int) clampedX,
                (int) clampedY,
                (int) visibleWidth,
                (int) visibleHeight
        );
    }

    public Rectangle getViewport() {
        double visibleWidth = screenWidth / scaleLevel;
        double visibleHeight = screenHeight / scaleLevel;
        return new Rectangle((int) worldX, (int) worldY, (int) visibleWidth, (int) visibleHeight);
    }


    private double clampX(double x) {
        if (worldBounds == null) return Math.max(0, x);
        return Math.max(0, Math.min(x, worldBounds.x + worldBounds.width - (screenWidth / scaleLevel)));
    }

    private double clampY(double y) {
        if (worldBounds == null) return Math.max(0, y);
        return Math.max(0, Math.min(y, worldBounds.y + worldBounds.height - (screenHeight / scaleLevel)));
    }

    public void updateScreenSize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }


    public double getWorldX() {
        return worldX;
    }

    public void setWorldX(double worldX) {
        this.worldX = worldX;
    }

    public double getWorldY() {
        return worldY;
    }

    public void setWorldY(double worldY) {
        this.worldY = worldY;
    }

    // Обработчики событий мыши
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            lastMousePosition = e.getPoint(); // Фиксируем точку начала перетаскивания
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastMousePosition != null && SwingUtilities.isLeftMouseButton(e)) {
            Point current = e.getPoint();
            updateCameraPosition(
                    current.x - lastMousePosition.x,
                    current.y - lastMousePosition.y
            );
            lastMousePosition = current;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        if (rotation < 0) {
            adjustScale(2);
        } else {
            adjustScale(0.5);
        }
    }

    // Неиспользуемые методы интерфейсов
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private static List<CameraListener> observers = new ArrayList<>();//реализвция метода update для интерфейса Camera.ShiftingListener

    public void addObserver(CameraListener observer) {
        observers.add(observer);
    }

    public void removeObserver(CameraListener observer) {
        observers.remove(observer);
    }

    public static void notifyObservers() {
        for (CameraListener observer : observers) {
            observer.updateViewport();
        }
    }

    public static void updateShifting() {
        notifyObservers(); // Уведомляем всех наблюдателей
    }


}



