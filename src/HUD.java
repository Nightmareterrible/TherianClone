import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HUD {
    ArrayList<Scen> listOfHUDScen = new ArrayList<>();
    private JLayeredPane layeredPane; // Хранение ссылки на JLayeredPane

    public HUD(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane; // Сохраняем ссылку на JLayeredPane
        System.out.println("HUD has been create");

    }

    public void createScen(String name) {
        Scen scen = new Scen(name);
        System.out.println("scen has been create");
        listOfHUDScen.add(scen);
    }

    public void deleteScen(Scen s) {
        listOfHUDScen.remove(s);
    }

    public void SelectScen(String name) {
        for (Scen scen : listOfHUDScen) {
            if (name.equals(scen.getName())) {
                if (scen.getPanel().getParent() != null) {
                    scen.getPanel().getParent().remove(scen.getPanel());
                }

                layeredPane.setLayer(scen.getPanel(), JLayeredPane.PALETTE_LAYER, 0); // Установка слоя
                layeredPane.revalidate(); // Перерисовка контентач
                layeredPane.repaint(); // Перерисовка контента
                System.out.println("Scen has been selected, name: " + name);

                return;

            }

        }
        System.out.println("Scen not found");
    }

}

class Scen {
    JPanel scenPanel;
    String name;

    public Scen(String name) {
        scenPanel = new JPanel();
        scenPanel.setBounds(0, 0, Okno.windowWidth, Okno.windowHeight);
        scenPanel.setLayout(null);
        this.name = name;
        scenPanel.setOpaque(false); // Установка прозрачности панели
        scenPanel.setVisible(true);
        setPanel(scenPanel);

    }
    public void addComponentToPanel(Object object) {
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