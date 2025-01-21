import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Okno extends JFrame {
    static panel p;
    private JLayeredPane layeredPane;

    public static final int windowWidth = 937;
    public static final int windowHeight = 625;

    public Okno() {
        setBounds(0, 0, windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        p = new panel();
        p.setBounds(0, 0, windowWidth, windowHeight);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0,0,windowWidth, windowHeight);
        layeredPane.add(p, JLayeredPane.DEFAULT_LAYER);


        p.addMouseListener(p.shifting);
        p.addMouseMotionListener(p.shifting);
        p.addMouseWheelListener(p.shifting);

        HUD hud = new HUD(layeredPane);
        hud.createScen("test");
        hud.SelectScen("test");


        setContentPane(layeredPane);
        setVisible(true);
        int fps = (int) (1000 / 60);// fps
        Timer repaintTimer = new Timer(fps, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Okno.p.repaint();
            }
        });
        repaintTimer.start();
    }

}