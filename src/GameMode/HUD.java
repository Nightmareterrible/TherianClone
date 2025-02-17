package GameMode;

import javax.swing.*;
import java.util.ArrayList;

public class HUD {
    private ArrayList<ScenHUD> listOfHUDScen = new ArrayList<>();
    private JLayeredPane layeredPane; // Хранение ссылки на JLayeredPane

    public HUD(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane; // Сохраняем ссылку на JLayeredPane
        System.out.println("GameMode.HUD has been create");
    }

    public void createScen(String name) {
        ScenHUD scen = new ScenHUD(name);
        System.out.println("scenHUD has been create");
        listOfHUDScen.add(scen);
    }

    public void deleteScen(ScenHUD s) {
        listOfHUDScen.remove(s);
    }
    public void removeScen(ScenHUD s) {
        layeredPane.remove(s.getPanel());
    }

    public ScenHUD getSceneByName(String name){
        for (ScenHUD scene : listOfHUDScen) {
            if (name.equals(scene.getName())) {
                return scene;
            }
        }
        System.out.println("GameMode.Scene of this name not found");
        return null;
    }

    public void SelectScen(String name, Integer JLayeredPaneLayer ,int position) {
        for (ScenHUD scen : listOfHUDScen) {
            if (name.equals(scen.getName())) {
                if (scen.getPanel().getParent() != null) {
                    scen.getPanel().getParent().remove(scen.getPanel());
                }
                layeredPane.add(scen.getPanel());
                layeredPane.setLayer(scen.getPanel(), JLayeredPaneLayer, position); // Установка слоя   layeredPane.setLayer(scen.getPanel(), JLayeredPane.PALETTE_LAYER, 0);

                layeredPane.revalidate(); // Перерисовка контента
                layeredPane.repaint(); // Перерисовка контента
                System.out.println("GameMode.ScenHUD has been selected, name: " + name);

                return;

            }

        }
        System.out.println("GameMode.ScenHUD not found");
    }

    public ArrayList<ScenHUD> getListOfHUDScen() {
        return listOfHUDScen;
    }

    public void setListOfHUDScen(ArrayList<ScenHUD> listOfHUDScen) {
        this.listOfHUDScen = listOfHUDScen;
    }
}




