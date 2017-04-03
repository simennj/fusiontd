package no.fusiontd.game;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.maps.MapReader;

import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Map {
    public final int TILEROWS = 9, TILECOLS = 16;
    public CatmullRomSpline<Vector2> path;
    private int[][] map;
    private MapReader mapReader = new MapReader();
    private Player localPlayer, mulPlayer;

    public Map(String mapName) {
        map = mapReader.loadMap(mapName + ".txt", TILEROWS, TILECOLS);
        path = getPath();
        int lives = 10; // should be given by difficulty
        int cash = 10;
        localPlayer = new Player(lives,cash,0);
        if (false){ // add some code for when is mp
            mulPlayer = new Player(lives,cash,0);
        }
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


    public void placeTower(float x, float y) {
        placeTower((int) x, (int) y);
    }

    public void placeTower(int x, int y) {
        int tile = getTile(x, y);
        if (tile == 0) {
            map[getMapRow(y)][getMapCol(x)] = 2;
        } // placing white tower
        else if (tile == 2) {
            map[getMapRow(y)][getMapCol(x)] = 3;
        } // placing blue tower
        else if (tile == 3) {
            map[getMapRow(y)][getMapCol(x)] = 0;
        } // placing grass
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

                if (adj[i][j] == 4) {

                    startPoint = new Point2D.Double(i, j);
                    points.add(startPoint);
                    checked[i][j] = true;

                }

                if (adj[i][j] == 5) {
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
        vectors.add(getVectorFromPoint(points.get(0)));
        for (int i = 1; i < points.size() - 1; i++) {
            vectors.add(getVectorFromPoint(points.get(i)));
        }
        vectors.add(getVectorFromPoint(points.get(points.size() - 1)));
        vectors.add(new Vector2(
                getVectorFromPoint(points.get(points.size() - 1)).x + 1,
                getVectorFromPoint(points.get(points.size() - 1)).y
        ));
        Vector2[] vectorArray = new Vector2[vectors.size()];
        vectors.toArray(vectorArray);
        for (Vector2 vector2 : vectorArray) {
            System.out.println(vector2);
        }
        return new CatmullRomSpline<Vector2>(vectorArray, false);
    }

    private Vector2 getVectorFromPoint(Point2D point) {
        return new Vector2((float) point.getY() + .5f, (float) point.getX() + .5f);
    }

}