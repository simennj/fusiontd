package no.fusiontd.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
    private Integer[][] map;
    private Texture img;

    public Map() {
        map = new Integer[32][32];
        img = new Texture("badlogic.jpg");
    }


    public void draw(float delta, SpriteBatch batch) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                batch.draw(img, c*10, r*10);
            }
        }
    }
}
