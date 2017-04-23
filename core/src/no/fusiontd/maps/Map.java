package no.fusiontd.maps;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;

public class Map {

    //Map is represented as a grid (int[][]), where a cell-value of 0 means grass, 1 means road/path,
    //2 means start (for path), and 3 means end of path.
    public final int TILEROWS = 9, TILECOLS = 16;
    private final MapGraphics mapGraphics;
    private final MapReader mapReader = new MapReader();
    private PathFinder pathFinder;
    private TileMatrix map;

    public Map(String mapName) {
        int[][] tileTypes = mapReader.loadMap(mapName + ".txt", TILEROWS, TILECOLS);
        map = new TileMatrix(tileTypes);
        pathFinder = new PathFinder(tileTypes);
        mapGraphics = new MapGraphics(tileTypes);
    }

    public Map(int tilerows, int tilecols) {
        int[][] tileTypes = new int[tilerows][tilecols];
        map = new TileMatrix(tileTypes);
        mapGraphics = new MapGraphics(tileTypes);
    }

    public void toggleTileType(float x, float y) {
        int row = getMapRow(y);
        int col = getMapCol(x);
        int tileType = (map.getType(row, col) + 1) % 4;
        map.setType(row, col, tileType);
        mapGraphics.setType(row, col, tileType);
    }

    public int getTileType(float x, float y) {
        return map.getType(getMapRow(y), getMapCol(x));
    }

    private int getMapCol(float x) {
        return MathUtils.floorPositive(MathUtils.clamp(x, 0, TILECOLS - 1));
    }

    private int getMapRow(float y) {
        return MathUtils.floorPositive(MathUtils.clamp(y, 0, TILEROWS - 1));
    }

    // (0,0) is in upper right corner (northeast) and going clockwise around the point

    public TextureAtlas.AtlasRegion getTileGraphic(int x, int y) {
        return mapGraphics.getTileGraphic(x, y);
    }

    public int[][] copyMap() {
        return map.getTileTypes();
    }

    public Path<Vector2> getPath() {
        return pathFinder.getPath();
    }

}