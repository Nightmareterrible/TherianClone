import javax.swing.*;
import java.awt.event.*;
import java.awt.image.ReplicateScaleFilter;

public class Shifting extends JFrame implements MouseMotionListener, MouseListener, MouseWheelListener {
    int previousMouseX = 0;
    int deltaShiftX = 0;
    static int shiftX = 0;
    int previousMouseY = 0;
    int deltaShiftY = 0;
    static int shiftY = 0;

    static double scale = 1;


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
        colculeteShiftXAndY(e);
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        colculeteScale(e);
    }

    private static void colculeteScale(MouseWheelEvent e) {
        if(e.getWheelRotation() == 1)//вверх
        {
            if (scale/2>=0.0625)
                scale=scale/2;
        }else if(e.getWheelRotation() == -1){// вниз
            if (scale*2<=1)
                scale=scale*2;
        }
    }


    private void colculeteShiftXAndY(MouseEvent e) {
        //   X   //////////////////////////////////////////////
        if (previousMouseX != 0) {
            deltaShiftX =  e.getX() - previousMouseX;
            if (shiftX + deltaShiftX*Math.pow(scale,-1)<=0) {//TODO посчитать ограничение в дргуую сторону
                shiftX += deltaShiftX * Math.pow(scale, -1);
            }
            previousMouseX = e.getX();
        } else {
            previousMouseX = e.getX();
        }
        //   Y   /////////////////////////////////
        if (previousMouseY != 0) {
            deltaShiftY = e.getY() - previousMouseY;
            if (shiftY + deltaShiftY*Math.pow(scale,-1)<=0) {//TODO посчитать ограничение в дргуую сторону
                shiftY += deltaShiftY * Math.pow(scale, -1);
            }
            previousMouseY = e.getY();
        } else {
            previousMouseY = e.getY();
        }
    }

    private void ResetPreviousMouseCoordinates() {
        previousMouseX = 0;
        previousMouseY = 0;
    }
    //getters and setters

    public static int getShiftX() {
        return shiftX;
    }

    public void setShiftX(int shiftX) {
        this.shiftX = shiftX;
    }

    public static int getShiftY() {
        return shiftY;
    }

    public void setShiftY(int shiftY) {
        this.shiftY = shiftY;
    }

    public static double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
