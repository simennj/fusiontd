package no.fusiontd.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Map {
    private int[][] map;
    public final int TILESIZE = 1, TILEROWS = 9, TILECOLS = 16;

    public Map() {
        map = new int[TILEROWS][TILECOLS];
        for (int r = 0; r < map.length; r++) {
            map[r] = new int[TILECOLS];
        }
    }

    public int getTower(int x, int y) {
        return map[y][x];
    }

    public void placeTower(float x, float y) {
        placeTower((int) x, (int) y);
    }

    public void placeTower(int x, int y) {
        if (map[MathUtils.clamp(y / TILESIZE, 0, TILEROWS - 1)][MathUtils.clamp(x / TILESIZE, 0, TILECOLS - 1)] == 6) {
            map[MathUtils.clamp(y / TILESIZE, 0, TILEROWS - 1)][MathUtils.clamp(x / TILESIZE, 0, TILECOLS - 1)] = 0;
        }
        map[MathUtils.clamp(y / TILESIZE, 0, TILEROWS - 1)][MathUtils.clamp(x / TILESIZE, 0, TILECOLS - 1)]++;
    }


    public void creepSpawn() {
    }

    public void pathFinder() {
    }
}
