package no.fusiontd.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.fusiontd.FusionTD;
import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Rotation;
import no.fusiontd.components.Velocity;
import no.fusiontd.game.EntityComponentManager;
import no.fusiontd.game.GameController;
import no.fusiontd.game.Map;

public class PlayScreen implements Screen, InputProcessor {

    public static final float WIDTH = 16, HEIGHT = 9;
    public float w, h;
    public SpriteBatch batch;
    public EntityComponentManager engine;
    private FusionTD game;
    private Map map;
    private GameController controller;
    private OrthographicCamera camera;
    private float aspectRatio;
    private float tilesize;
    private int screenWidth, screenHeight;
    private float heightOffset, widthOffset;
    private State state = State.RUN;

    private TextureAtlas tileAtlas = new TextureAtlas("tiles.atlas");
    private TextureAtlas.AtlasRegion groundTex;
    private TextureAtlas.AtlasRegion roadTex;
    private TextureAtlas.AtlasRegion towerWhiteTex;
    private TextureAtlas.AtlasRegion towerBlueTex;
    private TextureAtlas.AtlasRegion pathStartTex;
    private TextureAtlas.AtlasRegion pathEndTex;

    public PlayScreen(FusionTD game) {
        this.game = game;
    }


    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        map = new Map();
        tilesize = Math.min(WIDTH / map.TILECOLS, HEIGHT / map.TILEROWS);
        controller = new GameController(map, this);
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        initializeTextures();
        engine = new EntityComponentManager(this);
        engine.addEntity(new Entity().add(new Position()).add(new Rotation()).add(new Render(new TextureRegion(new Texture("tiles/246.png")))).add(new Velocity(1, 1)));
    }

    private void initializeTextures() {
        groundTex = tileAtlas.findRegion("024");
        roadTex = tileAtlas.findRegion("050");
        towerBlueTex = tileAtlas.findRegion("128");
        towerWhiteTex = tileAtlas.findRegion("123");
        pathStartTex = tileAtlas.findRegion("091");
        pathEndTex = tileAtlas.findRegion("090");
    }

    @Override
    public void render(float delta) {

        switch (state) {
            case RUN:
                Gdx.gl.glClearColor(0, 1, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                drawMap(map, batch);
                engine.update(delta);
                batch.end();
                break;
            case PAUSE:
                //Do nothing
                break;
        }
    }

    private void drawMap(Map map, SpriteBatch batch) {
        for (int y = 0; y < map.TILEROWS; y++) {
            for (int x = 0; x < map.TILECOLS; x++) {
                batch.draw(getSprite(map.getTile(x, y)), x * tilesize, y * tilesize, tilesize, tilesize);
            }
        }
    }

    private TextureAtlas.AtlasRegion getSprite(int type) {
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
        this.state = State.PAUSE;
        //When the state is set to Pause, it causes the Render method to do nothing.
    }

    @Override
    public void resume() {
        this.state = State.RUN;
        //With the state set to run, the Render method is set to run normally.
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int tile = map.getTile(getCameraY(screenY), getCameraX(screenX));
        if (tile == 4) {
            return false;
        } else if (tile == 0 || tile == 2 || tile == 3) {
            map.placeTower(getCameraX(screenX), getCameraY(screenY));
            return false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public enum State {
        PAUSE,
        RUN,
    }

}
