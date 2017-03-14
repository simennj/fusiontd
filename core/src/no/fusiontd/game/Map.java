package no.fusiontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import no.fusiontd.maps.MapReader;

public class Map {
    private int[][] map;
    private Texture groundTex, roadTex, towerWhiteTex, towerBlueTex, pathStartTex, pathEndTex;
    private MapReader mapReader = new MapReader();
    private static final int TILESIZE = 128, TILEROWS = 8, TILECOLS = 15;

    public Map() {
        map = mapReader.loadMap("testmap.txt", TILECOLS, TILEROWS);
        groundTex = new Texture("tiles/024.png");
        roadTex = new Texture("tiles/050.png");
        towerBlueTex = new Texture("tiles/128.png");
        towerWhiteTex = new Texture("tiles/123.png");
        pathStartTex = new Texture("tiles/091.png");
        pathEndTex = new Texture("tiles/090.png");
    }


    public void draw(float delta, SpriteBatch batch) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                batch.draw(getSprite(map[r][c]), c*TILESIZE, r*TILESIZE, TILESIZE, TILESIZE);
            }
        }
    }

    private Texture getSprite(int type) {
        switch (type) {
            case 0: return groundTex;
            case 1: return roadTex;
            case 2: return towerWhiteTex;
            case 3: return towerBlueTex;
            case 4: return pathStartTex;
            case 5: return pathEndTex;
            default: return groundTex;
        }
    }

    public void placeTower(int x, int y) {
        if (map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] == 0 )
        { map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] = 2; } // placing white tower
        else if (map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] == 2)
        { map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] = 3; } // placing blue tower
        else if (map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] == 3)
        { map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] = 0; } // placing grass
    }


    public void creepSpawn(int spawnNumber){
    }

    public static List<Point2D> findPath(int[][] adj){


        // Remembers which cells have been checked
        boolean[][] checked = new boolean[TILECOLS][TILEROWS]; // default value is false

        List<Point2D> points = new ArrayList<Point2D>();
        Point2D startPoint = null;
        Point2D endPoint = null;

        //Iterate over matrix to find start and end point
        for (int i = 0; i < TILECOLS; i++){
            for (int j = 0; j < TILEROWS; j++){

                if (adj[i][j] == 4){

                    startPoint = new Point2D.Double(i, j);
                    points.add(startPoint);
                    checked[i][j] = true;

                }

                if (adj[i][j] == 5){
                    endPoint = new Point2D.Double(i, j);
                    checked[i][j] = true;
                }

            }
        }

        //Start search
        Queue<Point2D> queue = new ArrayDeque<Point2D>();
        queue.add(startPoint);

        while (!queue.isEmpty()){

            Point2D current = queue.remove();
            int x = (int) current.getX();
            int y = (int) current.getY();

            if (x - 1 >= 0 && adj[x - 1][y] == 1 && !checked[x - 1][y]){

                Point2D point = new Point2D.Double(x - 1, y);
                queue.add(point);
                points.add(point);
                checked[x - 1][y] = true;
            }
            if (x + 1 < TILECOLS && adj[x + 1][y] == 1  && !checked[x + 1][y]){

                Point2D point = new Point2D.Double(x + 1, y);
                queue.add(point);
                points.add(point);
                checked[x + 1][y] = true;
            }
            if (y - 1 >= 0 && adj[x][y - 1] == 1 && !checked[x][y - 1]){

                Point2D point = new Point2D.Double(x, y - 1);
                queue.add(point);
                points.add(point);
                checked[x][y - 1] = true;
            }
            if (y + 1 < TILEROWS && adj[x][y + 1] == 1 && !checked[x][y + 1]){

                Point2D point = new Point2D.Double(x, y + 1);
                queue.add(point);
                points.add(point);
                checked[x][y + 1] = true;
            }
        }
        points.add(endPoint);

        return points;
    }
}