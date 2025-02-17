package PawnObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pawn extends GameObject {

    private int speed = 90;//скорость в секунду
    protected Timer move;


    public Pawn() {
        super(100,500,100,100, null);

    }

    public void moveTo(int moveToThisX, int moveToThisY) {
        float delay = 10.0F;//чем меньше, тем плавнее двигается персонаж
         move = new Timer( (int) delay, new ActionListener() {

            final int deltaMoveToThisByX = (int) (moveToThisX - getWorldX());
            final int deltaMoveToThisByY = (int) (moveToThisY - getWorldY());
            final double lengthWay = Math.sqrt((Math.pow(deltaMoveToThisByX, 2) + Math.pow(deltaMoveToThisByY, 2)));

            double countSteps = lengthWay / (speed * delay / 1000);

            final double stepByX = deltaMoveToThisByX / countSteps;

            final double stepByY = deltaMoveToThisByY / countSteps;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (countSteps > 0) {
                    //setWorldX ((int) (getWorldX() + stepByX));
                   // setWorldY ((int) (getWorldY() + stepByY));
                    countSteps--;
                } else {
                    move.stop();
                }
            }
        });
        move.start();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
