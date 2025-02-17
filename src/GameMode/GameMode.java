package GameMode;

import javax.swing.*;
import java.util.ArrayList;


public class GameMode {
    private ArrayList<Scene> listOfScene = new ArrayList<>();
    private JLayeredPane layeredPane; // Хранение ссылки на JLayeredPane
    private HUD hud;
    private ArrayList<String> nameSceneNowSelected = new ArrayList<>();

    public GameMode(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane; // Сохраняем ссылку на JLayeredPane
        System.out.println("GameMode.GameMode has been create");
        hud = new HUD(layeredPane);
    }

    public ArrayList<String> getNameSceneNowSelected() {
        return nameSceneNowSelected;
    }

    public HUD getHud() {
        return hud;
    }

    public void setHud(HUD hud) {
        this.hud = hud;
    }


    public void createScene(String name) {
        for (Scene s : listOfScene) {
            if (s.getName().equals(name)) {
                System.out.println("\u001B сцена с именем" + name +"\u001B уже существует");
                return;
            }
        }
        Scene scene = new Scene(name);
        System.out.println("scene has been create");
        listOfScene.add(scene);
    }

    public Scene getSceneByName(String name) {
        for (Scene scene : listOfScene) {
            if (name.equals(scene.getName())) {
                return scene;
            }
        }
        System.out.println("GameMode.Scene of this name not found");
        return null;
    }

    public void deleteScene(Scene s) {
        listOfScene.remove(s);
    }

    public void removeScen(String name) {
        for (Scene scene : listOfScene) {
            if (name.equals(scene.getName())) {
                layeredPane.remove(scene.getPanel());
                nameSceneNowSelected.remove(name);
                for (int i = 0; i < scene.getHudSceneNames().size(); i++) {
                    hud.removeScen(hud.getSceneByName(scene.getHudSceneNames().get(i)));
                }

                layeredPane.revalidate(); // Перерисовка контента
                layeredPane.repaint(); // Перерисовка контента
                return;
            }
        }
    }


    public void selectScene(String name, Integer JLayeredPaneLayer, int position) {
        for (Scene scene : listOfScene) {
            if (name.equals(scene.getName())) {
                if (scene.getPanel().getParent() != null) {
                    scene.getPanel().getParent().remove(scene.getPanel());
                }

                layeredPane.add(scene.getPanel(), JLayeredPaneLayer, position);//устанавливаем основную панель layeredPane.add(scene.getPanel(), JLayeredPane.DEFAULT_LAYER);
                nameSceneNowSelected.add(name);
                for (int i = 0; i < scene.getHudSceneNames().size(); i++) {
                    hud.SelectScen(scene.getHudSceneNames().get(i), JLayeredPane.PALETTE_LAYER, 0);
                }

                layeredPane.revalidate(); // Перерисовка контента
                layeredPane.repaint(); // Перерисовка контента

                System.out.println("GameMode.Scene has been selected, name: " + name);

                return;
            }

        }
        System.out.println("GameMode.Scene not found");
    }

    public ArrayList<Scene> getListOfScene() {
        return listOfScene;
    }

    public void setListOfScene(ArrayList<Scene> listOfScene) {
        this.listOfScene = listOfScene;
    }
}

