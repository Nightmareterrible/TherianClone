
import java.awt.*;

import java.awt.geom.Point2D;


class Map {
    private panel panel;
    final int MAP_TILE_WGHT = 500;

    final int MAP_TILE_HIGHT = 500;
    int amountTileX = 30;
    int amountTileY = 20;

    MapTile mapTiles[][] = new MapTile[amountTileX][amountTileY];//массив тайлов условная карта раздела на маленькие части


    public Map(panel p) {
        panel = p;
        loadFullMapTiles();

    }

    public void draw(Graphics g) {
        for (int i = 0; i < amountTileX; i++) {
            for (int j = 0; j < amountTileY; j++) {

                    g.drawImage(mapTiles[i][j].tileImage, (int) Math.ceil((mapTiles[i][j].getX() + okno.p.shifting.getShiftX())*okno.p.shifting.getScale()),
                                                        (int) Math.ceil((mapTiles[i][j].getY() + okno.p.shifting.getShiftY())*okno.p.shifting.getScale()),
                                                        (int) Math.ceil(MAP_TILE_WGHT*okno.p.shifting.getScale()),
                                                        (int) Math.ceil(MAP_TILE_WGHT*okno.p.shifting.getScale()),
                                                        null);


            }
        }
    }

    public void loadFullMapTiles() {
        for (int i = 0; i < amountTileX; i++) {
            for (int j = 0; j < amountTileY; j++) {
                MapTile tile = new MapTile();
                tile.setNumberX(i);
                tile.setNumberY(j);
                tile.setCoordinates(new Point(MAP_TILE_WGHT * i, MAP_TILE_HIGHT * j));
                try {
                    tile.tileImage = panel.globalLoadImg.MapTileLoad(i, j);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                mapTiles[i][j] = tile;
            }
        }
    }
}


class MapTile {

    Point2D coordinates;
    int numberX;
    int numberY;
    Image tileImage;

    public MapTile() {
    }


    //geters and seters

    public int getX() {//тоже самое что и getCoordinates() только сокращеное написание
        return (int) coordinates.getX();
    }

    public int getY() {//тоже самое что и getCoordinates() только сокращеное написание
        return (int) coordinates.getY();
    }

    public Point2D getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point2D coordinates) {
        this.coordinates = coordinates;
    }

    public int getNumberX() {
        return numberX;
    }

    public void setNumberX(int numberX) {
        this.numberX = numberX;
    }

    public int getNumberY() {
        return numberY;
    }

    public void setNumberY(int numberY) {
        this.numberY = numberY;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }
}