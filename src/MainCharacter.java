
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class MainCharacter extends Pawn implements MouseListener, MouseMotionListener {

    Image iconCharacter;
    int speed = 90;//скорость в секунду
    int moveToThisXLine; //TODO придумать норм имена
    int moveToThisYLine;
    Timer move;

    public MainCharacter() {
        iconCharacter = GlobalLoadImg.CharacterLoad();
        setX(3000);
        setY(3000);

    }


    public void draw(Graphics g) {
        if (move != null && move.isRunning()) {
            g.drawLine(Shifting.getWindowPointFromMapCoordinatesX((int) getX()), Shifting.getWindowPointFromMapCoordinatesY((int) getY()),
                    Shifting.getWindowPointFromMapCoordinatesX(moveToThisXLine), Shifting.getWindowPointFromMapCoordinatesY(moveToThisYLine));
        }
        Shifting.drowAutoScaleAndShiftingImage(g, iconCharacter, (int) (getX() - iconCharacter.getWidth(null) / 2),
                (int) (getY() - iconCharacter.getHeight(null) / 2),
                iconCharacter.getWidth(null), iconCharacter.getHeight(null), null);

    }


    public void moveTo(int moveToThisX, int moveToThisY) {
        float delay = 10.0F;//чем меньше, тем плавнее двигается персонаж
        moveToThisXLine = moveToThisX;
        moveToThisYLine = moveToThisY;
        move = new Timer((int) delay, new ActionListener() {

            int deltaMoveToThisByX = (int) (moveToThisX - getX());
            int deltaMoveToThisByY = (int) (moveToThisY - getY());
            double lengthWay = Math.sqrt((Math.pow(deltaMoveToThisByX, 2) + Math.pow(deltaMoveToThisByY, 2)));

            double countSteps = lengthWay / (speed * delay / 1000);

            double stepByX = deltaMoveToThisByX / countSteps;

            double stepByY = deltaMoveToThisByY / countSteps;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (countSteps > 0) {
                    setX(getX() + stepByX);
                    setY(getY() + stepByY);
                    countSteps--;
                } else {
                    move.stop();
                }
            }
        });
        move.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (move == null )
            moveTo(Shifting.getCoordinatesOnMapFromWindowPointX(e.getX()), Shifting.getCoordinatesOnMapFromWindowPointY(e.getY()));
        if (!move.isRunning())
            moveTo(Shifting.getCoordinatesOnMapFromWindowPointX(e.getX()), Shifting.getCoordinatesOnMapFromWindowPointY(e.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}

