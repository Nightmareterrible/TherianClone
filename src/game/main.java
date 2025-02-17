package game;

public class main {

    static Okno okno;
    static GameFactory gameFactory;
    static GlobalLoadImg globalLoadImg;
    public static void main(String[] args) {
        GLOBALS.mode = "player";
        System.out.println("Max heap size: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + " MB");
        okno = new Okno();
        globalLoadImg = new GlobalLoadImg();
        gameFactory = new GameFactory();
        //SwingUtilities.invokeLater(okno); // Вызов метода run() через интерфейс Runnable

    }


}





