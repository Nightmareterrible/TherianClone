package game;

import GameMode.GameMode;
import GameMode.Scene;
import GameMode.HUD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameFactory {
    public static final String MAIN_GAME_SCENE = "MAIN_GAME_SCENE";
    public static final String LOBBY_SCENE = "LOBBY_SCENE";
    MainGamePanel mainGamePanel;
    GameMode gameMode;

    public GameFactory() {
        gameMode = new GameMode(main.okno.getLayeredPane());

        createMainGameScene();
        createLobbyScene();


        gameMode.selectScene(MAIN_GAME_SCENE, JLayeredPane.DEFAULT_LAYER, 0);
    }

    public void createLobbyScene() {
        gameMode.createScene(LOBBY_SCENE);
        Scene lobby = gameMode.getSceneByName(LOBBY_SCENE);

        JButton startGame = new JButton("start game");
        startGame.setBounds(Okno.windowWidth / 2 - 100, Okno.windowHeight / 2 - 100, 100, 100);
        startGame.setVisible(true);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode.removeScen(LOBBY_SCENE);
                gameMode.selectScene(MAIN_GAME_SCENE, JLayeredPane.DEFAULT_LAYER, 0);
            }
        });
        lobby.addComponentToPanel(startGame);


    }


    public void createMainGameScene() {
        gameMode.createScene(MAIN_GAME_SCENE);
        Scene mainGameScene = gameMode.getSceneByName(MAIN_GAME_SCENE);
        mainGamePanel = new MainGamePanel();

        mainGameScene.setPanel(mainGamePanel);
       // mainGamePanel.setScene(mainGameScene);

        //mainGamePanel.incializationObjectOnPanel();

        gameMode.getHud().createScen("exit");

        JButton exitToLobby = new JButton("exit");
        exitToLobby.setBounds(Okno.windowWidth - 100, 0, 100, 100);
        exitToLobby.setVisible(true);
        exitToLobby.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMode.removeScen(MAIN_GAME_SCENE);
                gameMode.selectScene(LOBBY_SCENE, JLayeredPane.DEFAULT_LAYER, 0);
            }
        });
        gameMode.getHud().getSceneByName("exit").addComponentToPanel(exitToLobby);




        JButton b = new JButton("ghbdtn");
        b.setBounds(100, 100, 100, 100);
        b.setVisible(true);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ufhaiufas");
            }
        });
        gameMode.getHud().getSceneByName("exit").addComponentToPanel(b);
        mainGameScene.addHUD("exit");
    }


}
