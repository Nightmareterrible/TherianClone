import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

import javax.swing.JPanel;

class panel extends JPanel {
    Map map;
    public GlobalLoadImg globalLoadImg;
    Shifting shifting;
    Pawn p;

    MainCharacter mainCharacter;



    // -------------------------------------------------------
    public panel() {
        setLayout(null);

        globalLoadImg = new GlobalLoadImg();
        shifting = new Shifting();
        map = new Map(this);
        if(GLOBALS.mode != "editor") {
            mainCharacter = new MainCharacter();
            addMouseListener(mainCharacter);
        }



    }

    // -------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        map.draw(g);
        if(GLOBALS.mode != "editor") {
            mainCharacter.draw(g);
        }
        if (redactor.r != null)
            redactor.r.draw(g);
    }


}
