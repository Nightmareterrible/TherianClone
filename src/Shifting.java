import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Shifting extends JFrame implements MouseMotionListener, MouseListener, MouseWheelListener {
    public static final double MAX_SCALE = 1;

    public static final double MIN_SCALE = 0.0625;
    public static final int MAX_MAP_SIZE_ON_SREEN_X = (int) ((okno.windowWidth) * Math.pow(MIN_SCALE, -1));

    public static final int MAX_MAP_SIZE_ON_SREEN_Y = (int) ((okno.windowHight) * Math.pow(MIN_SCALE, -1));
    int previousMouseX = 0;//previous-предыдущий
    int deltaShiftX = 0;
    static int shiftX = 0;
    int previousMouseY = 0;//previous-предыдущий
    int deltaShiftY = 0;
    static int shiftY = 0;
    static boolean shifting = true;
    static boolean scaling = true;
    static double scale = MIN_SCALE;


    public Shifting() {


    }


    //mouse////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isShifting())
            ResetPreviousMouseCoordinates();
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isShifting())
            colculeteShiftXAndY(e);
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (isScaling())
            colculeteScale(e);

    }


    private void colculeteScale(MouseWheelEvent e) {
        if (e.getWheelRotation() == 1) {// down
            if (scale / 2 >= MIN_SCALE) {
                scale = scale / 2;
                adjustShiftOnScrollDown(e);
            }
        } else if (e.getWheelRotation() == -1) {// up
            if (scale * 2 <= MAX_SCALE) {
                scale = scale * 2;
                adjustShiftOnScrollUp(e);
            }
        }
        updateShifting();
    }

    private void adjustShiftOnScrollDown(MouseWheelEvent e) {//adjust-регулировать
        int sizeMapOnScreenX = (int) ((okno.windowWidth) * Math.pow(scale, -1));
        int sizeMapOnScreenY = (int) ((okno.windowHight) * Math.pow(scale, -1));
        if (Math.abs(shiftX + deltaShiftX * Math.pow(scale / 2, -1)) >= MAX_MAP_SIZE_ON_SREEN_X  - sizeMapOnScreenX) {
            shiftX = (MAX_MAP_SIZE_ON_SREEN_X - sizeMapOnScreenX) * -1 / 2;
        }
        if (Math.abs(shiftY + deltaShiftY * Math.pow(scale, -1)) >= MAX_MAP_SIZE_ON_SREEN_Y  - sizeMapOnScreenY) {

            shiftY = (MAX_MAP_SIZE_ON_SREEN_Y - sizeMapOnScreenY) * -1 / 2;
        }
        updateShifting();
    }

    private void adjustShiftOnScrollUp(MouseWheelEvent e) {//adjust-регулировать
        shiftX = (int) (shiftX - e.getX() / scale);
        shiftY = (int) (shiftY - e.getY() / scale);
        updateShifting();
    }


    private void colculeteShiftXAndY(MouseEvent e) {
        //   X   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (previousMouseX != 0) {
            deltaShiftX = e.getX() - previousMouseX;
            int sizeMapOnScreenX = (int) ((okno.windowWidth) * Math.pow(scale, -1));

            if (shiftX + deltaShiftX * Math.pow(scale, -1) <= 0 && Math.abs(shiftX + deltaShiftX * Math.pow(scale, -1)) <= MAX_MAP_SIZE_ON_SREEN_X  - sizeMapOnScreenX) {

                shiftX += deltaShiftX * Math.pow(scale, -1);
            }
            previousMouseX = e.getX();
        } else {
            previousMouseX = e.getX();
        }
        //   Y   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (previousMouseY != 0) {
            deltaShiftY = e.getY() - previousMouseY;
            int sizeMapOnScreenY = (int) ((okno.windowHight) * Math.pow(scale, -1));
            if (shiftY + deltaShiftY * Math.pow(scale, -1) <= 0 && Math.abs(shiftY + deltaShiftY * Math.pow(scale, -1)) <= MAX_MAP_SIZE_ON_SREEN_Y  - sizeMapOnScreenY) {

                shiftY += deltaShiftY * Math.pow(scale, -1);
            }
            previousMouseY = e.getY();
        } else {
            previousMouseY = e.getY();
        }
        updateShifting();
    }

    private void ResetPreviousMouseCoordinates() {//previous-предыдущий
        previousMouseX = 0;
        previousMouseY = 0;
    }

    public static void drowAutoScaleAndShiftingImage(Graphics g, Image image, int x, int y, int width, int height, java.awt.image.ImageObserver observer) {
        g.drawImage(image, getWindowPointFromMapCoordinatesX(x), getWindowPointFromMapCoordinatesY(y), getDrowWidth(width), getDrowHeight(height), observer);
    }
    public static void drowAutoScaleAndShiftingImage(Graphics g, Image image, int x, int y, java.awt.image.ImageObserver observer) {
        g.drawImage(image, getWindowPointFromMapCoordinatesX(x), getWindowPointFromMapCoordinatesY(y), observer);
    }

    public static void updateShifting(){//этот метод вызывается кадый раз когда карта обнавляется
                                        //и вызывает метод из интерфейся ShiftingAdapter который реализуется в панели
        okno.p.updateShifting();
    }
    //getters and setters


    public static boolean isScaling() {
        return scaling;
    }

    public static void setScaling(boolean scaling) {
        updateShifting();
        Shifting.scaling = scaling;
    }

    public static boolean isShifting() {
        return shifting;
    }

    public static void setShifting(boolean shifting) {
        updateShifting();
        Shifting.shifting = shifting;
    }

    public static int getDrowWidth(int width) {//получить длину для отрисовки
        return (int) Math.ceil(width * scale);
    }

    public static int getDrowHeight(int height) {//получить высоту для отрисовки
        return (int) Math.ceil(height * scale);
    }

    public static int getWindowPointFromMapCoordinatesX(int x) {//получить координату на окне относительно точки на карте (подходит для отрисовки)
        return (int) Math.ceil((x + shiftX) * scale);
    }

    public static int getWindowPointFromMapCoordinatesY(int y) {//получить координату на карте относительно точки на карте (подходит для отрисовки)
        return (int) Math.ceil((y + shiftY) * scale);
    }

    public static int getCoordinatesOnMapFromWindowPointX(int x) {//получить координату на карте относительно точки на окне
        // (подходит для получения координаты(к примеру от клика мыши))
        return (int) (x / getScale() - shiftX);
    }

    public static int getCoordinatesOnMapFromWindowPointY(int y) {//получить координату на карте относительно точки на окне
        // (подходит для получения координаты(к примеру от клика мыши))
        return (int) (y / getScale() - shiftY);
    }

    public static int getShiftX() {
        return shiftX;
    }

    public void setShiftX(int shiftX) {
        updateShifting();
        this.shiftX = shiftX;
    }

    public static int getShiftY() {
        return shiftY;
    }

    public void setShiftY(int shiftY) {
        updateShifting();
        this.shiftY = shiftY;
    }

    public static double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        updateShifting();
        this.scale = scale;
    }

}
