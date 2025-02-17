package GameMode;

import game.Okno;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Scene {
    private GamePanel scenePanel = null;
    private String name;
    private ArrayList<String> hudSceneNames = new ArrayList<>();


    public Scene(String name) {
        this.name = name;

    }

    public void addComponentToPanel(Object object) {
        if(scenePanel == null)
            createPanel();
        scenePanel.add((Component) object);
    }

    public void createPanel() {

        scenePanel = new GamePanel();
        scenePanel.setBounds(0, 0, Okno.windowWidth, Okno.windowHeight);
        scenePanel.setLayout(null);
        scenePanel.setOpaque(false); // Установка прозрачности панели
        scenePanel.setVisible(true);
        setPanel(scenePanel);

    }

    public JPanel getPanel() {
        return scenePanel;
    }

    public void setPanel(GamePanel scenePanel) {
        this.scenePanel = scenePanel;
        scenePanel.setScene(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getHudSceneNames() {
        return hudSceneNames;
    }

    public void addHUD(String hudSceneNames) {
        this.hudSceneNames.add(hudSceneNames);
    }

    public void removeHUD(String hudSceneNames) {
        this.hudSceneNames.remove(hudSceneNames);
    }



}
