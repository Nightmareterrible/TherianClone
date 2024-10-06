
import java.awt.*;

import java.awt.geom.Point2D;


class Map {
    private panel panel;
    public static final int MAP_TILE_WIDTH = 500;

    public static final int MAP_TILE_HEIGHT = 500;
    public static final int amountTileX = 30;
    public static final int amountTileY = 20;


    static MapTile[][] mapTiles = new MapTile[amountTileX][amountTileY];//массив тайлов условная карта раздела на маленькие части


    public Map(panel p) {
        panel = p;
        loadFullMapTiles();
        System.gc();

    }

    public static void updateMap() {
        int sizeMapOnScreenX = (int) ((okno.windowWidth) * Math.pow(Shifting.getScale(), -1));//
        int sizeMapOnScreenY = (int) ((okno.windowHight) * Math.pow(Shifting.getScale(), -1));
        int countMapTitelVisobilitiX = sizeMapOnScreenX / MAP_TILE_WIDTH;
        int countMapTitelVisobilitiY = sizeMapOnScreenY / MAP_TILE_HEIGHT;
        int mapTiless[][] = new int[amountTileX][amountTileY];

        for (int i = 0; i < amountTileX; i++) {
            for (int j = 0; j < amountTileY; j++) {
                if (Shifting.MAX_MAP_SIZE_ON_SREEN_X - Math.abs(Shifting.getShiftX()) >= i*500 - 500 &&
                        i*500 + 500 >= Math.abs(Shifting.getShiftX()) &&
                        Shifting.MAX_MAP_SIZE_ON_SREEN_Y - Math.abs(Shifting.getShiftY()) >= j*500 - 500 &&
                        j*500 + 500 >= Math.abs(Shifting.getShiftY())
                ) {
                    if (mapTiles[i][j].getTileImage() == null) {

                        try {
                            mapTiles[i][j].tileImage = okno.p.globalLoadImg.MapTileLoad(i, j);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }


                        // mapTiles[i][j].tileImage = okno.p.globalLoadImg.MapTileLoad(i, j);
                    }
                    mapTiless[i][j] = 1;
                } else {
                    mapTiles[i][j].setTileImage(null);
                    mapTiless[i][j] = 0;
                }
            }

        }
        System.gc();

        for (int j = 0; j < amountTileY; j++) {
            for (int i = 0; i < amountTileX; i++) {
                System.out.print(mapTiless[i][j] + " ");
            }
            System.out.println("" + Math.abs(Shifting.getShiftX()));
        }


        //System.out.println("");
    }

    public void draw(Graphics g) {
        for (int i = 0; i < amountTileX; i++) {
            for (int j = 0; j < amountTileY; j++) {
                if (mapTiles!= null && mapTiles[i][j] != null) {
                    Shifting.drowAutoScaleAndShiftingImage(g, mapTiles[i][j].tileImage, mapTiles[i][j].getX(), mapTiles[i][j].getY(), MAP_TILE_WIDTH, MAP_TILE_HEIGHT, null);
                    g.drawRect(Shifting.getWindowPointFromMapCoordinatesX(mapTiles[i][j].getX()), Shifting.getWindowPointFromMapCoordinatesY(mapTiles[i][j].getY()), 500, 500);
                }
            }
        }
    }

    public void loadFullMapTiles() {
        for (int i = 0; i < amountTileX; i++) {
            for (int j = 0; j < amountTileY; j++) {
                MapTile tile = new MapTile();
                tile.setNumberX(i);
                tile.setNumberY(j);
                tile.setCoordinates(new Point(MAP_TILE_WIDTH * i, MAP_TILE_HEIGHT * j));
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
    Image tileImageLink;
    boolean isVis;

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