package game;

//import Camera.Shifting;
import Camera.Camera;
import GameMode.Drawable;
import GameMode.GamePanel;
import GameMode.Scene;
import Map.MapLoder;
import PawnObject.GameObject;
import PawnObject.MainCharacter;
import PawnObject.Pawn;
import Redactor.Redactor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

class MainGamePanel extends GamePanel {




    // -------------------------------------------------------
    public MainGamePanel() {
        setBounds(0, 0, Okno.windowWidth, Okno.windowHeight);
        setLayout(null);
        System.out.println("Panel hes been create");
    }

    @Override
    public void initializeObjects() {
        Camera camera = new Camera(15000-1920,10000-1080);
        setupInput(camera);
        setCamera(camera);
        MapLoder ml = new MapLoder(camera);
        addDrawable(ml);


        GameObject go;
        for (int i =0;i < 100;i+=2){
            go = new GameObject(200*i,200,200,200,null);
            addGameObject(go);
        }


        if (GLOBALS.mode.equals("editor")) {
            Redactor redactor = new Redactor();
            addMouseListener(redactor);
            addMouseMotionListener(redactor);
            addDrawable(redactor);
        }else {
            MainCharacter mainCharacter = new MainCharacter();
            addGameObject(mainCharacter);
            // setupInput(mainCharacter);
        }
    }



    // -------------------------------------------------------


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
}
