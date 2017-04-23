package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MapCamera;
import no.fusiontd.MenuStage;
import no.fusiontd.game.Map;
import no.fusiontd.maps.MapWriter;

public class MapEditorScreen implements Screen, Input.TextInputListener, InputProcessor {


    private static final float WIDTH = 16, HEIGHT = 9;
    public final int TILEROWS = 9, TILECOLS = 16;
    public SpriteBatch batch;
    private FusionTD game;
    private MenuStage stage;
    private MapCamera camera;
    private MapEditorScreen.State state = MapEditorScreen.State.METADATA;
    private String mapName;
    private Map map;
    private TextureAtlas.AtlasRegion play;
    private TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");

    public MapEditorScreen(FusionTD game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new MenuStage();
        Gdx.input.setInputProcessor(stage);
        stage.addImageButton("backButton", new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        game.returnToMenu();
                    }
                }
        );

        stage.createTextButton("Create Map", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.getTextInput(MapEditorScreen.this, "Enter Map name", "", "Map Name");
            }
        });

        stage.createTextButton("Delete Maps", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openDeleteScreen();
            }
        });

        camera = new MapCamera(TILECOLS, TILEROWS);
        batch = new SpriteBatch();
        map = new Map(TILEROWS, TILECOLS);
        setup();
    }

    private void setup() {
        play = new TextureAtlas.AtlasRegion(uiAtlas.findRegion("play0"));
        play.flip(true, false);
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case METADATA:
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
                break;
            case EDITING:
                Gdx.gl.glClearColor(0, 1, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                camera.drawMap(map, batch);
                batch.draw(uiAtlas.findRegion("back0"), 15.0f, 0.0f, 1f, 1f); // back button
                batch.draw(play, 0.0f, 0.0f, 1f, 1f);
                batch.end();
                break;
        }
    }

    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {

    }

    @Override
    public void input(String inputName) {
        mapName = inputName;
        state = State.EDITING;
        Gdx.input.setInputProcessor(this);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void canceled() {

    }

    public void setMap(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public void resize(int width, int height) {
        switch (state) {
            case METADATA:
                stage.getViewport().update(width, height, true);
                break;
            case EDITING:
                camera.resize(width, height);
                break;
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
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
            case METADATA:
                break;
            case EDITING:
                if (camera.transformedX(screenX) > 15.0f && camera.transformedX(screenX) < 16.0f && camera.transformedY(screenY) > 0.0f && camera.transformedY(screenY) < 1.0f) {
                    game.returnToMenu();
                    state = State.METADATA;
                } else if (camera.transformedX(screenX) > 0.0f && camera.transformedX(screenX) < 1.0f && camera.transformedY(screenY) > 0.0f && camera.transformedY(screenY) < 1.0f) {
                    saveMap();
                } else {
                    map.toggleTile(camera.transformedX(screenX), camera.transformedY(screenY));
                }
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        map.setTile(camera.transformedX(screenX), camera.transformedY(screenY));
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

    private void saveMap() {
        MapWriter mapWriter = new MapWriter();
        mapWriter.saveMap(map.copyMap(), mapName);
    }

    public enum State {
        METADATA,
        EDITING,
    }
}
