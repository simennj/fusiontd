package no.fusiontd.maps;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class PathFinder {

    private int[][] map;
    private CatmullRomSpline<Vector2> path;

    PathFinder(int[][] map) {
        this.map = map;
        this.path = generatePath(map);
    }

    private static CatmullRomSpline<Vector2> generatePath(int[][] map) {
        List<Vector2> points = findPath(map);
        ArrayList<Vector2> vectors = new ArrayList<Vector2>();
        vectors.add(getVectorFromPoint(points.get(0)));
        for (Vector2 point : points) {
            vectors.add(getVectorFromPoint(point));
        }
        vectors.add(getVectorFromPoint(points.get(points.size() - 1)));
        Vector2[] vectorArray = new Vector2[vectors.size()];
        vectors.toArray(vectorArray);
        return new CatmullRomSpline<Vector2>(vectorArray, false);
    }

    private static List<Vector2> findPath(int[][] adj) {
        final int tileRows = adj.length;
        final int tileCols = adj[0].length;
        //Function that takes a map as input, and returns a list (with Point2D-objects) of path as a return value.
        // Remembers which cells have been checked
        boolean[][] checked = new boolean[tileRows][tileCols]; // default value is false

        List<Vector2> points = new ArrayList<Vector2>();
        Vector2 startPoint = null;
        Vector2 endPoint = null;

        //Iterate over matrix to find start and end point
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {

                if (adj[i][j] == 2) {

                    startPoint = new Vector2(i, j);
                    points.add(startPoint);
                    checked[i][j] = true;

                }

                if (adj[i][j] == 3) {
                    endPoint = new Vector2(i, j);
                    checked[i][j] = true;
                }
            }
        }

        //Start search
        Queue<Vector2> queue = new ArrayDeque<Vector2>();
        queue.add(startPoint);

        while (!queue.isEmpty()) {

            Vector2 current = queue.remove();
            int x = (int) current.x;
            int y = (int) current.y;

            if (x - 1 >= 0 && adj[x - 1][y] == 1 && !checked[x - 1][y]) {

                Vector2 point = new Vector2(x - 1, y);
                queue.add(point);
                points.add(point);
                checked[x - 1][y] = true;
            }
            if (x + 1 < tileRows && adj[x + 1][y] == 1 && !checked[x + 1][y]) {

                Vector2 point = new Vector2(x + 1, y);
                queue.add(point);
                points.add(point);
                checked[x + 1][y] = true;
            }
            if (y - 1 >= 0 && adj[x][y - 1] == 1 && !checked[x][y - 1]) {

                Vector2 point = new Vector2(x, y - 1);
                queue.add(point);
                points.add(point);
                checked[x][y - 1] = true;
            }
            if (y + 1 < tileCols && adj[x][y + 1] == 1 && !checked[x][y + 1]) {

                Vector2 point = new Vector2(x, y + 1);
                queue.add(point);
                points.add(point);
                checked[x][y + 1] = true;
            }
        }
        points.add(endPoint);

        return points;
    }

    private static Vector2 getVectorFromPoint(Vector2 point) {
        return new Vector2(point.y + .5f, point.x + .5f);
    }

    CatmullRomSpline<Vector2> getPath() {
        return path;
    }
}