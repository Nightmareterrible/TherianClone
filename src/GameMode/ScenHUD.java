package GameMode;

import game.Okno;

import javax.swing.*;
import java.awt.*;

public class ScenHUD {
    private JPanel scenPanel = null;
    private String name;

    public ScenHUD(String name) {
        this.name = name;
    }

    public void addComponentToPanel(Object object) {
        if (scenPanel == null) {
            scenPanel = new JPanel();
            scenPanel.setBounds(0, 0, Okno.windowWidth, Okno.windowHeight);
            scenPanel.setLayout(null);
            scenPanel.setOpaque(false); // Установка прозрачности панели
            scenPanel.setVisible(true);
            setPanel(scenPanel);
        }
        scenPanel.add((Component) object);
    }

    public JPanel getPanel() {
        return scenPanel;
    }

    public void setPanel(JPanel panel) {
        this.scenPanel = panel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
