package deletedClass;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageScroller extends JPanel {
    private long lastTime = System.nanoTime();
    private int frames = 0;
    private int fps = 0;


    private BufferedImage image;
    private int xOffset = 0;
    private static final int Y = 50, WIDTH = 1920, HEIGHT = 1080; // Фиксированная область
    private final ImageReader reader;
    private ImageInputStream input;
    Rectangle rect;
    ImageReadParam param;
    boolean bu = false;
    ImageReader newReader;
    ImageReader newReader2;
    ImageInputStream input2;

    public ImageScroller(String imagePath) throws IOException {
        rect = new Rectangle();
        input = ImageIO.createImageInputStream(new File(imagePath));
        reader = ImageIO.getImageReaders(input).next();
        reader.setInput(input);
        param = reader.getDefaultReadParam();


        // Создаём новый поток и ридер
        input = ImageIO.createImageInputStream(new File("src/img/map1_1920x1080.jpg"));
        newReader = ImageIO.getImageReaders(input).next();
        newReader.setInput(input, true, true);


         input2 = ImageIO.createImageInputStream(new File("src/img/map1_3840x2160.jpg"));
         newReader2 = ImageIO.getImageReaders(input2).next();
        newReader2.setInput(input2, true, true);



        JButton b = new JButton("");
        b.setBounds(0, 0, 100, 100);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bu) {
                    loadPartialImage2();
                    bu = false;
                } else {
                    bu = true;
                    loadPartialImage();
                }
            }
        });
        b.setVisible(true);
        add(b);
        loadPartialImage();

    }

    private void updateImage() {
        xOffset++;
        loadPartialImage();
    }


    private void loadPartialImage() {
        try {
            if (image != null) {
                image.flush();
                image = null;
            }



            // Обновляем параметры чтения
            rect.setBounds(xOffset, Y, WIDTH, HEIGHT);
            param = newReader.getDefaultReadParam();
            param.setSourceRegion(rect);

            // Читаем только нужную область
            image = newReader.read(0, param);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPartialImage2() {
        try {
            if (image != null) {
                image.flush();
                image = null;
            }


            // Обновляем параметры чтения
            rect.setBounds(xOffset, Y, WIDTH, HEIGHT);
            param = newReader2.getDefaultReadParam();
            param.setSourceRegion(rect);

            // Читаем только нужную область
            image = newReader2.read(0, param);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long currentTime = System.nanoTime();
        frames++;
        if (currentTime - lastTime >= 1_000_000_000L) { // Каждую секунду
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }

        if (image != null)
            g.drawImage(image, 0, 0, null);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("FPS: " + fps, 10, 20);

    }

    public void closeResources() {
        try {
            reader.dispose();
            input.close();
        } catch (IOException ignored) {
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Image Scroller");
                ImageScroller panel = new ImageScroller("src/img/map.png");

                frame.add(panel);
                frame.setSize(WIDTH, HEIGHT);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                panel.repaint();
               new Timer(10, e -> {
                    //  panel.updateImage();
                    panel.repaint();
                }).start();
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        panel.closeResources();
                    }
                });

                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
