package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.fusiontd.FusionTD;
import no.fusiontd.game.GameController;
import no.fusiontd.game.Map;

public class PlayScreen implements Screen {

    public static final float WIDTH = 16, HEIGHT = 9;
    public float w, h;

    private FusionTD game;
    private Map map;
    private GameController controller;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private float aspectRatio;
    private float tilesize;
    private int screenWidth, screenHeight;
    private float heightOffset, widthOffset;

    private Texture groundTex, roadTex, towerWhiteTex, towerBlueTex, pathStartTex, pathEndTex;


    public PlayScreen(FusionTD game) {
        this.game = game;
    }


    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        map = new Map();
        tilesize = Math.min(WIDTH / map.TILECOLS, HEIGHT / map.TILEROWS);
        controller = new GameController(map, this);
        Gdx.input.setInputProcessor(controller);
        batch = new SpriteBatch();
        initializeTextures();
    }

    public void initializeTextures() {
        groundTex = new Texture("tiles/024.png");
        roadTex = new Texture("tiles/050.png");
        towerBlueTex = new Texture("tiles/128.png");
        towerWhiteTex = new Texture("tiles/123.png");
        pathStartTex = new Texture("tiles/091.png");
        pathEndTex = new Texture("tiles/090.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawMap(map, batch);
        batch.end();
    }

    private void drawMap(Map map, SpriteBatch batch) {
        for (int y = 0; y < map.TILEROWS; y++) {
            for (int x = 0; x < map.TILECOLS; x++) {
                batch.draw(getSprite(map.getTile(x, y)), x * tilesize, y * tilesize, tilesize, tilesize);
            }
        }
    }

    private Texture getSprite(int type) {
        switch (type) {
            case 0:
                return groundTex;
            case 1:
                return roadTex;
            case 2:
                return towerWhiteTex;
            case 3:
                return towerBlueTex;
            case 4:
                return pathStartTex;
            case 5:
                return pathEndTex;
            default:
                return groundTex;
        }
    }

    public float getCameraX(int screenX) {
        return (screenX - widthOffset) * w / screenWidth;
    }

    public float getCameraY(int screenY) {
        return HEIGHT - (screenY - heightOffset) * h / screenHeight;
    }

    @Override
    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        aspectRatio = (float) (width) / (float) (height);
        if (aspectRatio > 16.0 / 9.0) {
            camera.viewportWidth = w = (HEIGHT * aspectRatio);
            camera.viewportHeight = h = HEIGHT;
            heightOffset = 0;
            widthOffset = (screenWidth - WIDTH * screenWidth / w) / 2;
        } else {
            camera.viewportHeight = h = (WIDTH / aspectRatio);
            camera.viewportWidth = w = WIDTH;
            heightOffset = (screenHeight - HEIGHT * screenHeight / h) / 2;
            widthOffset = 0;
        }
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
