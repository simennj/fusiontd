package no.fusiontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Map {
    private int[][] map;
    private Texture groundTex, roadTex;
    private static final int TILESIZE = 128, TILEROWS = 8, TILECOLS = 15;

    public Map() {
        map = new int[TILEROWS][TILECOLS];
        for (int r = 0; r < map.length; r++) {
            map[r] = new int[TILECOLS];
        }
        groundTex = new Texture("tiles/024.png");
        roadTex = new Texture("tiles/050.png");
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
            default: return groundTex;
        }
    }

    public void placeTower(int x, int y) {
        map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] = 1;
    }
}
