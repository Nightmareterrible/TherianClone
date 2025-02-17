package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Okno extends JFrame implements Runnable {
    private JLayeredPane layeredPane;

    public static final int windowWidth = 1920;
    public static final int windowHeight = 1080;

    public Okno() {
        setBounds(0, 0, windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, windowWidth, windowHeight);
        setContentPane(layeredPane);

        setVisible(true);
        int fps = (int) (1000 / 60);// fps
        Timer repaintTimer = new Timer(fps, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                layeredPane.repaint();
            }
        });
        repaintTimer.start();
    }

    @Override
    public void run() {

    }
}