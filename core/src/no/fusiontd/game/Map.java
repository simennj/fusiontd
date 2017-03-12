package no.fusiontd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Map {
    private int[][] map;
    private Texture groundTex, roadTex, towerWhiteTex, towerBlueTex, pathStartTex, pathEndTex;
    private static final int TILESIZE = 128, TILEROWS = 8, TILECOLS = 15;

    public Map() {
        map = new int[TILEROWS][TILECOLS];
        for (int r = 0; r < map.length; r++) {
            map[r] = new int[TILECOLS];
        }
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
        if (map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] == 6)
        { map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)] = 0; }
        map[MathUtils.clamp(y/TILESIZE, 0, TILEROWS-1)][MathUtils.clamp(x/TILESIZE, 0, TILECOLS-1)]++;
    }


    public void creepSpawn() {
    }

    public void pathFinder(){
    }
}
