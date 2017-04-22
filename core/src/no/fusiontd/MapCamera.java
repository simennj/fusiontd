package no.fusiontd;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.fusiontd.game.Map;

public class MapCamera extends OrthographicCamera {
    static final float WIDTH = 16, HEIGHT = 9;
    final float tilesize;
    private float screenHeight, screenWidth, widthOffset, heightOffset;
    private TextureAtlas tileAtlas = new TextureAtlas("tiles.atlas");

    public MapCamera(float tileCols, float tileRows) {
        super(WIDTH, HEIGHT);
        tilesize = Math.min(WIDTH / tileCols, HEIGHT / tileRows);
    }

    public void resize(float screenWidth, float screenHeight) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        float aspectRatio = screenWidth / screenHeight;
        if (aspectRatio > 16.0 / 9.0) {
            viewportWidth = HEIGHT * aspectRatio;
            viewportHeight = HEIGHT;
            heightOffset = 0;
            widthOffset = (screenWidth - WIDTH * screenWidth / viewportWidth) / 2;
        } else {
            viewportHeight = WIDTH / aspectRatio;
            viewportWidth = WIDTH;
            heightOffset = (screenHeight - HEIGHT * screenHeight / viewportHeight) / 2;
            widthOffset = 0;
        }
        position.set(WIDTH / 2, HEIGHT / 2, 0);
        update();
    }

    public float transformedX(int screenX) {
        return (screenX - widthOffset) * viewportWidth / screenWidth;
    }

    public float transformedY(int screenY) {
        return HEIGHT - (screenY - heightOffset) * viewportHeight / screenHeight;
    }

    public void drawMap(Map map, SpriteBatch batch) {
        for (int y = 1; y < map.TILEROWS + 1; y++) {
            for (int x = 1; x < map.TILECOLS + 1; x++) {
                batch.draw(map.getTileGraphic(x,y), (x-1) * tilesize, (y-1) * tilesize, tilesize, tilesize);
            }
        }
    }

}