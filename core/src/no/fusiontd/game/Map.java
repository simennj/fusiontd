package no.fusiontd.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import no.fusiontd.Graphics;
import no.fusiontd.maps.MapReader;

import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Map {

    //Map is represented as a grid (int[][]), where a cell-value of 0 means grass, 1 means road/path,
    //2 means start (for path), and 3 means end of path.
    public final int TILEROWS = 9, TILECOLS = 16;
    public CatmullRomSpline<Vector2> path;
    private int[][] map;
    private int[][] padMap;
    private MapReader mapReader = new MapReader();
    private TextureAtlas tilesAtlas = new TextureAtlas("tiles_new.atlas");

    public Map(String mapName) {
        map = mapReader.loadMap(mapName + ".txt", TILEROWS, TILECOLS);
        path = getPath();
        padMap = padMap(map);
    }

    public void mapEditor(String mapName){
        map = mapReader.loadMap(mapName + ".txt", TILEROWS, TILECOLS);
    }

    public int[][] padMap(int[][] map) {
        int[][] paddedMap = new int[TILEROWS+2][TILECOLS+2];
        String str = "";
        int y_max = TILEROWS + 1;
        for (int i = 0; i < TILEROWS + 2; i++) {
            for (int j = 0; j < TILECOLS + 2; j++) {
                if (i == 0 || j == 0 || i == TILEROWS + 1 || j == TILECOLS + 1) { //  || map[y_max - i - 1][j-1] == 2 || map[y_max - i- 1][j-1] == 3
                    paddedMap[y_max - i][j] = 0;
                } else {
                    paddedMap[y_max - i][j] = Math.min(map[y_max - i - 1][j-1], 1);
                } str = str + paddedMap[y_max - i][j];
            } str = str + "\n";
        }
        System.out.println(str);
        return paddedMap;
    }

    public int getTile(float x, float y) {
        return map[getMapRow(y)][getMapCol(x)];
    }

    private int getMapCol(float x) {
        return MathUtils.floorPositive(MathUtils.clamp(x, 0, TILECOLS - 1));
    }

    private int getMapRow(float y) {
        return MathUtils.floorPositive(MathUtils.clamp(y, 0, TILEROWS - 1));
    }

    public void placeTile(int x, int y){

        int tile = getTile(x, y);
        if (tile == 0){
            map[getMapRow(y)][getMapCol(x)] = 1;//place roadTexture
        }
        if (tile == 1){
            map[getMapRow(y)][getMapCol(x)] = 2;//place startPoint
        }
        if (tile == 2){
            map[getMapRow(y)][getMapCol(x)] = 3;//place endPoint
        }
        if (tile == 3){
            map[getMapRow(y)][getMapCol(x)] = 0;//place grassTexture
        }
    }

    public enum tileType{
        PATH,
        GRASS,
        NORTH,
        EAST,
        SOUTH,
        WEST,
        NORTHEAST,
        NORTHWEST,
        SOUTHEAST,
        SOUTHWEST,
    }

    // (0,0) is in upper right corner (northeast) and going clockwise around the point

    private int[] grass = {0,0,0,0,0,0,0,0},
            roadNorth1 = {1,0,0,0,0,0,1,1}, roadNorth2 = {1,0,0,0,0,0,0,1}, roadNorth3 = {0,0,0,0,0,0,1,1},
            roadWest1 = {0,0,0,0,1,1,1,0}, roadWest2 = {0,0,0,0,0,1,1,0}, roadWest3 = {0,0,0,0,1,1,0,0},
            roadSouth1 = {0,0,1,1,1,0,0,0}, roadSouth2 = {0,0,0,1,1,0,0,0}, roadSouth3 = {0,0,1,1,0,0,0,0},
            roadEast1 = {1,1,1,0,0,0,0,0}, roadEast2 = {0,1,1,0,0,0,0,0}, roadEast3 = {1,1,0,0,0,0,0,0},
            roadNorthEast  = {1,0,0,0,0,0,0,0}, roadNorthWest  = {0,0,0,0,0,0,1,0}, roadSouthEast = {0,0,1,0,0,0,0,0}, roadSouthWest = {0,0,0,0,1,0,0,0},
            cornerNorthEast1 = {1,1,1,0,0,0,1,1}, cornerNorthEast2 = {1,1,0,0,0,0,1,1}, cornerNorthEast3 = {1,1,1,0,0,0,0,1}, cornerNorthEast4 = {1,1,0,0,0,0,0,1},
            cornerNorthWest1  = {1,0,0,0,1,1,1,1}, cornerNorthWest2 = {0,0,0,0,1,1,1,1}, cornerNorthWest3 = {1,0,0,0,0,1,1,1}, cornerNorthWest4 = {0,0,0,0,0,1,1,1},
            cornerSouthEast1 = {1,1,1,1,1,0,0,0}, cornerSouthEast2 = {0,1,1,1,1,0,0,0}, cornerSouthEast3 = {1,1,1,1,0,0,0,0}, cornerSouthEast4 = {0,1,1,1,0,0,0,0},
            cornerSouthWest1  = {0,0,1,1,1,1,1,0}, cornerSouthWest2  = {0,0,1,1,1,1,0,0}, cornerSouthWest3 = {0,0,0,1,1,1,1,0}, cornerSouthWest4 = {0,0,0,1,1,1,0,0},
            neighbours = {0,0,0,0,0,0,0,0};

    public TextureAtlas.AtlasRegion getTileGraphic(int x, int y) {
        if (padMap[y][x] == 1 || padMap[y][x] == 2 || padMap[y][x] == 3){
            return tilesAtlas.findRegion("t_p0");
        }
        return checkNeighbours(padMap,y,x);
    }

    private TextureAtlas.AtlasRegion checkNeighbours(int[][] map, int x, int y){

        // (0,0) is in upper right corner (northeast) and going clockwise around the point

        neighbours[0] = map[x-1][y-1];
        neighbours[1] = map[x][y-1];
        neighbours[2] = map[x+1][y-1];
        neighbours[3] = map[x+1][y];
        neighbours[4] = map[x+1][y+1];
        neighbours[5] = map[x][y+1];
        neighbours[6] = map[x-1][y+1];
        neighbours[7] = map[x-1][y];


        //OBS!: Unfortunately inverted graphics from arrey, i.e. RoadWest gives GroundEast tile etc.
        // The corners are seriously messed up. BEWARE!!!

        if (Arrays.equals(neighbours,grass)){
            return tilesAtlas.findRegion("t_g0");
        } else if (Arrays.equals(neighbours,roadNorth1) || Arrays.equals(neighbours,roadNorth2) || Arrays.equals(neighbours,roadNorth3)){
            return tilesAtlas.findRegion("t_g_N0");
        } else if (Arrays.equals(neighbours,roadWest1) || Arrays.equals(neighbours,roadWest2) || Arrays.equals(neighbours,roadWest3)){
            return tilesAtlas.findRegion("t_g_W0");
        } else if (Arrays.equals(neighbours,roadSouth1) || Arrays.equals(neighbours,roadSouth2) || Arrays.equals(neighbours,roadSouth3)){
            return tilesAtlas.findRegion("t_g_S0");
        } else if (Arrays.equals(neighbours,roadEast1) || Arrays.equals(neighbours,roadEast2) || Arrays.equals(neighbours,roadEast3)){
            return tilesAtlas.findRegion("t_g_E0");
        } else if (Arrays.equals(neighbours,roadNorthEast)){
            return tilesAtlas.findRegion("t_g_NE0");
        } else if (Arrays.equals(neighbours,roadNorthWest)){
            return tilesAtlas.findRegion("t_g_NW0");
        } else if (Arrays.equals(neighbours,roadSouthEast)){
            return tilesAtlas.findRegion("t_g_SE0");
        } else if (Arrays.equals(neighbours,roadSouthWest)){
            return tilesAtlas.findRegion("t_g_SW0");
        } else if (Arrays.equals(neighbours,cornerNorthEast1) || Arrays.equals(neighbours,cornerNorthEast2) || Arrays.equals(neighbours,cornerNorthEast3) || Arrays.equals(neighbours,cornerNorthEast4)){
            return tilesAtlas.findRegion("t_p_SW0");
        } else if (Arrays.equals(neighbours,cornerNorthWest1) || Arrays.equals(neighbours,cornerNorthWest2) || Arrays.equals(neighbours,cornerNorthWest3) || Arrays.equals(neighbours,cornerNorthWest4)){
            return tilesAtlas.findRegion("t_p_SE0");
        } else if (Arrays.equals(neighbours,cornerSouthEast1) || Arrays.equals(neighbours,cornerSouthEast2) || Arrays.equals(neighbours,cornerSouthEast3) || Arrays.equals(neighbours,cornerSouthEast4)){
            return tilesAtlas.findRegion("t_p_NW0");
        } else if (Arrays.equals(neighbours,cornerSouthWest1) || Arrays.equals(neighbours,cornerSouthWest2) || Arrays.equals(neighbours,cornerSouthWest3) || Arrays.equals(neighbours,cornerSouthWest4)){
            return tilesAtlas.findRegion("t_p_NE0");
        }
        return tilesAtlas.findRegion("t_g0");
        // return null;
    }

    public List<Point2D> findPath(int[][] adj) {
        //Function that takes a map as input, and returns a list (with Point2D-objects) of path as a return value.
        // Remembers which cells have been checked
        boolean[][] checked = new boolean[TILEROWS][TILECOLS]; // default value is false

        List<Point2D> points = new ArrayList<Point2D>();
        Point2D startPoint = null;
        Point2D endPoint = null;

        //Iterate over matrix to find start and end point
        for (int i = 0; i < TILEROWS; i++) {
            for (int j = 0; j < TILECOLS; j++) {

                if (adj[i][j] == 2) {

                    startPoint = new Point2D.Double(i, j);
                    points.add(startPoint);
                    checked[i][j] = true;

                }

                if (adj[i][j] == 3) {
                    endPoint = new Point2D.Double(i, j);
                    checked[i][j] = true;
                }
            }
        }

        //Start search
        Queue<Point2D> queue = new ArrayDeque<Point2D>();
        queue.add(startPoint);

        while (!queue.isEmpty()) {

            Point2D current = queue.remove();
            int x = (int) current.getX();
            int y = (int) current.getY();

            if (x - 1 >= 0 && adj[x - 1][y] == 1 && !checked[x - 1][y]) {

                Point2D point = new Point2D.Double(x - 1, y);
                queue.add(point);
                points.add(point);
                checked[x - 1][y] = true;
            }
            if (x + 1 < TILEROWS && adj[x + 1][y] == 1 && !checked[x + 1][y]) {

                Point2D point = new Point2D.Double(x + 1, y);
                queue.add(point);
                points.add(point);
                checked[x + 1][y] = true;
            }
            if (y - 1 >= 0 && adj[x][y - 1] == 1 && !checked[x][y - 1]) {

                Point2D point = new Point2D.Double(x, y - 1);
                queue.add(point);
                points.add(point);
                checked[x][y - 1] = true;
            }
            if (y + 1 < TILECOLS && adj[x][y + 1] == 1 && !checked[x][y + 1]) {

                Point2D point = new Point2D.Double(x, y + 1);
                queue.add(point);
                points.add(point);
                checked[x][y + 1] = true;
            }
        }
        points.add(endPoint);

        return points;
    }

    private CatmullRomSpline<Vector2> getPath() {
        List<Point2D> points = findPath(map);
        ArrayList<Vector2> vectors = new ArrayList<Vector2>();
        vectors.add(getVectorFromPoint(points.get(0)));
        for (int i = 0; i < points.size(); i++) {
            vectors.add(getVectorFromPoint(points.get(i)));
        }
        vectors.add(getVectorFromPoint(points.get(points.size() - 1)));
        Vector2[] vectorArray = new Vector2[vectors.size()];
        vectors.toArray(vectorArray);
        return new CatmullRomSpline<Vector2>(vectorArray, false);
    }

    private Vector2 getVectorFromPoint(Point2D point) {
        return new Vector2((float) point.getY() + .5f, (float) point.getX() + .5f);
    }

}