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
import no.fusiontd.MPAlternative.MPClient;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.components.Geometry;
import no.fusiontd.game.*;

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
    private Player localPlayer, mulPlayer;
    private MPServer mpServer;
    private MPClient mpClient;
    public PlayScreen(FusionTD game, boolean multiplayer) {
        this.game = game;
        this.multiplayer = multiplayer;
        int lives = 10; int cash = 10; //should be set by difficulty
        localPlayer = new Player(lives, cash, 0, game);
        if (true){ // should be for multiplayer only, but to avoid crashes before multiplayer is properly implemented we'll always have a mulPlayer
            mulPlayer = new Player(lives, cash, 0, game);
        }
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        map = new Map(mapName);
        tilesize = Math.min(WIDTH / map.TILECOLS, HEIGHT / map.TILEROWS);
        controller = new GameController(map, this);
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        engine = new EntityComponentManager(this, localPlayer, mulPlayer);
        creepSpawner = new CreepSpawner(map.path, engine);
        ui = new UI(game,localPlayer,mulPlayer, engine);
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
                ui.render(batch);
                batch.end();
                break;
            case PAUSE:
                //Do nothing
                break;
        }
    }

    private void drawMap(Map map, SpriteBatch batch) {
        for (int y = 1; y < map.TILEROWS + 1; y++) {
            for (int x = 1; x < map.TILECOLS + 1; x++) {
                batch.draw(map.getTileGraphic(x, y), (x-1) * tilesize, (y-1) * tilesize, tilesize, tilesize);
            }
        }
    }

    private TextureAtlas.AtlasRegion getSprite(int type) {
        switch (type) {
            case 0:
                return Graphics.getRegion("groundTex");
            case 1:
                return Graphics.getRegion("roadTex");
            case 2:
                return Graphics.getRegion("pathStartTex");
            case 3:
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
        System.out.println(getCameraX(screenX) + ";" + getCameraY(screenY));
        if (getCameraX(screenX) > 15.0f && getCameraX(screenX) < 16.0f && getCameraY(screenY) > 0.0f && getCameraY(screenY) < 1.0f) {
            game.returnToMenu();
        } else if (getCameraX(screenX) > 0.0f && getCameraX(screenX) < 1.0f && getCameraY(screenY) > 0.0f && getCameraY(screenY) < 1.0f) {
            if (multiplayer) {
                if (mpClient == null) {
                    creepSpawner.startNextWave();
                    mpServer.sendCreepWaveStarted();
                }
            } else {
                creepSpawner.startNextWave();
            }
        } else if (ui.isTowerSetting()) {
            ui.towerSet(getCameraX(screenX), getCameraY(screenY));
        } else if (engine.checkTower(new Geometry(getCameraX(screenX), getCameraY(screenY), 0, .5f))) {
            // selected Tower and upgrade
            ui.selectTower(getCameraX(screenX), getCameraY(screenY));
        } else if (engine.checkCreep(new Geometry(getCameraX(screenX), getCameraY(screenY), 0, .5f))) {
            // selected Creep
            ui.selectCreep(getCameraX(screenX), getCameraY(screenY));
        } else if (map.getTile(getCameraX(screenX), getCameraY(screenY)) == 1 || map.getTile(getCameraX(screenX), getCameraY(screenY)) == 2 || map.getTile(getCameraX(screenX), getCameraY(screenY)) == 3) {
            // is on road (or end or start), do nothing
            return false;
        } else if (!ui.isTowerSetting()) {
            // open tower setting menu
            ui.openTowerSet(getCameraX(screenX), getCameraY(screenY));
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

    public void setMpServer(MPServer mpServer){ this.mpServer = mpServer; ui.initMPServer(mpServer);}

    public void setMpClient(MPClient mpClient){ this.mpClient = mpClient; mpClient.initCreepSpawner(creepSpawner); ui.initMPClient(mpClient);}

    public enum State {
        PAUSE,
        RUN,
    }

}
