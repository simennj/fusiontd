package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.fusiontd.FusionTD;
import no.fusiontd.MPAlternative.MPClient;
import no.fusiontd.MPAlternative.MPServer;
import no.fusiontd.MapCamera;
import no.fusiontd.components.Geometry;
import no.fusiontd.game.*;

public class PlayScreen implements Screen, InputProcessor {

    public SpriteBatch batch;
    private float w, h;
    private EntityComponentManager engine;
    private FusionTD game;
    private no.fusiontd.maps.Map map;
    private GameController controller;
    private CreepSpawner creepSpawner;
    private MapCamera camera;
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
        int lives = 20; int cash = 20; //should be set by difficulty
        localPlayer = new Player(lives, cash, 0, game);

        // should be for multiplayer only, but to avoid crashes before multiplayer is properly implemented we'll always have a mulPlayer
        mulPlayer = new Player(lives, cash, 0, game);
    }

    @Override
    public void show() {
        map = new no.fusiontd.maps.Map(mapName);
        camera = new MapCamera(map.TILECOLS, map.TILEROWS);
        controller = new GameController(map, this);
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        engine = new EntityComponentManager(this, localPlayer);
        creepSpawner = new CreepSpawner(map.getPath(), engine);
        ui = new UI(game,localPlayer,mulPlayer, engine,map);
    }

    public void setMap(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public void render(float delta) {

        switch (state) {
            case RUN:
                Gdx.gl.glClearColor(3/255f, 73/255f, 114/255f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                camera.drawMap(map, batch);
                engine.update(delta);
                creepSpawner.update(delta);
                ui.render(batch);
                batch.end();
                break;
            case PAUSE:
                batch.begin();
                ui.renderPause(batch);
                batch.end();
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
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
        switch (state) {
            case RUN:
                if (camera.transformedX(screenX) > 15.0f && camera.transformedX(screenX) < 16.0f && camera.transformedY(screenY) > 0.0f && camera.transformedY(screenY) < 1.0f) {
                    game.returnToMenu();
                } else if (camera.transformedX(screenX) > 0.0f && camera.transformedX(screenX) < 1.0f && camera.transformedY(screenY) > 0.0f && camera.transformedY(screenY) < 1.0f) {
                    if (multiplayer) {
                        if (mpClient == null) {
                            ui.creepWaveNumber++;
                            creepSpawner.startNextWave();
                            mpServer.sendCreepWaveStarted();
                        }
                    } else {
                        ui.creepWaveNumber++;
                        creepSpawner.startNextWave();
                    }
                } else if (camera.transformedX(screenX) > 1.0f && camera.transformedX(screenX) < 2.0f && camera.transformedY(screenY) > 0.0f && camera.transformedY(screenY) < 1.0f) {
                    switchState();
                } else if (ui.isTowerSetting()) {
                    ui.towerSet(camera.transformedX(screenX), camera.transformedY(screenY));
                } else if (engine.checkTower(new Geometry(camera.transformedX(screenX), camera.transformedY(screenY), 0, .5f))) {
                    // selected Tower and upgrade
                    ui.selectTower(camera.transformedX(screenX), camera.transformedY(screenY));
                } else if (engine.checkCreep(new Geometry(camera.transformedX(screenX), camera.transformedY(screenY), 0, .5f))) {
                    // selected Creep
                    ui.selectCreep(camera.transformedX(screenX), camera.transformedY(screenY));
                } else if (map.getTileType(camera.transformedX(screenX), camera.transformedY(screenY)) == 1 || map.getTileType(camera.transformedX(screenX), camera.transformedY(screenY)) == 2 || map.getTileType(camera.transformedX(screenX), camera.transformedY(screenY)) == 3) {
                    // is on road (or end or start), do nothing
                    return false;
                } else if (!ui.isTowerSetting()) {
                    // open tower setting menu
                    ui.openTowerSet(camera.transformedX(screenX), camera.transformedY(screenY));
                }
                return false;
            case PAUSE:
                if (camera.transformedX(screenX) > 1.0f && camera.transformedX(screenX) < 2.0f && camera.transformedY(screenY) > 0.0f && camera.transformedY(screenY) < 1.0f) {
                    switchState();
                }
                return false;
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

    public void switchState(){
        switch (state){
            case PAUSE:
                state = State.RUN;
                break;
            case RUN:
                state = State.PAUSE;
                break;
            default:
                state = State.RUN;
        }
    }

    public enum State {
        PAUSE,
        RUN,
    }

}
