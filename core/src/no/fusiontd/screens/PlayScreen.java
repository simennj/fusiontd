package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import no.fusiontd.FusionTD;
import no.fusiontd.Graphics;
import no.fusiontd.components.Geometry;
import no.fusiontd.game.CreepSpawner;
import no.fusiontd.game.EntityComponentManager;
import no.fusiontd.game.GameController;
import no.fusiontd.game.Map;
import no.fusiontd.game.UI;

public class PlayScreen implements Screen, InputProcessor {

    private static final float WIDTH = 16, HEIGHT = 9;
    public SpriteBatch batch;
    private float w, h;
    private EntityComponentManager engine;
    private FusionTD game;
    private Map map;
    private GameController controller;
    private CreepSpawner creepSpawner;
    private OrthographicCamera camera;
    private float aspectRatio;
    private float tilesize;
    private int screenWidth, screenHeight;
    private float heightOffset, widthOffset;
    private State state = State.RUN;
    private boolean multiplayer;
    private String mapName;
    private UI ui;

    public PlayScreen(FusionTD game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        map = new Map(mapName);
        tilesize = Math.min(WIDTH / map.TILECOLS, HEIGHT / map.TILEROWS);
        controller = new GameController(map, this);
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        engine = new EntityComponentManager(this);
        creepSpawner = new CreepSpawner(map.path, engine);
        ui = new UI(game);
    }

    public void setMap(String mapName) {
        this.mapName = mapName;
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
                creepSpawner.update(delta);
                ui.render(delta);
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
                return Graphics.getRegion("groundTex");
            case 1:
                return Graphics.getRegion("roadTex");
            case 4:
                return Graphics.getRegion("pathStartTex");
            case 5:
                return Graphics.getRegion("pathEndTex");
            default:
                return Graphics.getRegion("groundTex");
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
        System.out.println(keycode);
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
        if (engine.checkTower(new Geometry(getCameraX(screenX), getCameraY(screenY), 0, .5f))) {
            // selected Tower
            System.out.println("tower");
        } else if (engine.checkCreep(new Geometry(getCameraX(screenX), getCameraY(screenY), 0, .5f))) {
            // selected Creep
            System.out.println("Creep");
        } else if (map.getTile(getCameraX(screenX), getCameraY(screenY)) == 1 || map.getTile(getCameraX(screenX), getCameraY(screenY)) == 4 || map.getTile(getCameraX(screenX), getCameraY(screenY)) == 5){
            // is on road (or end or start), do nothing
            System.out.println("road");
            return false;
        } else {
            // open tower setting menu
            System.out.println("Set tower");
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int tile = map.getTile(getCameraX(screenX), getCameraY(screenY));
        if (tile == 0) {
            engine.spawnTower("missileTower", new Geometry(getCameraX(screenX), getCameraY(screenY), 0, .5f));
        } else {
            engine.spawnTower("flameTower", new Geometry(getCameraX(screenX), getCameraY(screenY), 0, .5f));
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
