package PawnObject;

import Camera.Camera;
import game.GlobalLoadImg;

import java.awt.*;
import java.awt.event.*;


public class MainCharacter extends Pawn implements MouseListener, MouseMotionListener {
    int moveToThisXForLine;
    int moveToThisYForLine;

    public MainCharacter() {
        //setIcon(GlobalLoadImg.CharacterLoad());
        //setWorldX(500);
        //setWorldY(500);
        //setHeight(50);
       // setWidth(50);
        System.out.println("MainCharacter has been created");
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (move == null || !move.isRunning()) {
            moveToThisXForLine = e.getX();
            moveToThisYForLine = e.getY();
            moveTo(e.getX(), e.getY());

        }
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

