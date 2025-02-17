package deletedClass;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameMap extends Application {
    private static final String IMAGE_PATH = "src/img/map.png";
    private static final int TILE_SIZE = 1024; // Размер плитки для оптимизации

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Game Map");

        // Создаем Canvas для рисования
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Загружаем изображение частями
        try {
            Image mapImage = new Image(new FileInputStream(IMAGE_PATH));
            PixelReader pixelReader = mapImage.getPixelReader();

            // Разбиваем изображение на плитки и рисуем их
            for (int x = 0; x < mapImage.getWidth(); x += TILE_SIZE) {
                for (int y = 0; y < mapImage.getHeight(); y += TILE_SIZE) {
                    int tileWidth = Math.min(TILE_SIZE, (int) mapImage.getWidth() - x);
                    int tileHeight = Math.min(TILE_SIZE, (int) mapImage.getHeight() - y);
                    WritableImage tile = new WritableImage(pixelReader, x, y, tileWidth, tileHeight);
                    gc.drawImage(tile, x, y);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Не удалось загрузить изображение: " + IMAGE_PATH);
            e.printStackTrace();
        }

        // Создаем сцену и добавляем Canvas
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Увеличиваем размер кучи памяти
        System.setProperty("java.opts", "-Xmx4g");
        launch(args);
    }
}
