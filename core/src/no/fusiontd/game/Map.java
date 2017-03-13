package no.fusiontd.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import no.fusiontd.maps.MapReader;

public class Map {
    private int[][] map;
    public final int TILESIZE = 1, TILEROWS = 9, TILECOLS = 16;
    private MapReader mapReader = new MapReader();

    public Map() {
        map = mapReader.loadMap("testmap.txt", TILEROWS, TILECOLS);
    }

    public int getTower(int x, int y) {
        return map[y][x];
    }

    public void placeTower(float x, float y) {
        placeTower((int) x, (int) y);
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

    public void pathFinder(){
    }
}
