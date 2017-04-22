package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;
import no.fusiontd.maps.MapWriter;
import no.fusiontd.menu.ExitButton;

public class MapEditorScreen implements Screen, Input.TextInputListener, InputProcessor {


    private static final float WIDTH = 16, HEIGHT = 9;
    public final int TILEROWS = 9, TILECOLS = 16;
    public SpriteBatch batch;
    private FusionTD game;
    private MenuStage stage;
    private float w, h;
    private OrthographicCamera camera;
    private float aspectRatio;
    private float tilesize;
    private int screenWidth, screenHeight;
    private float heightOffset, widthOffset;
    private MapEditorScreen.State state = MapEditorScreen.State.METADATA;
    private String mapName;
    private int[][] map;
    private ExitButton exitButton;
    private TextButton btnCreateMap;
    private TextureAtlas.AtlasRegion play;
    private TextureAtlas tileAtlas = new TextureAtlas("tiles.atlas");
    private TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");

    public MapEditorScreen(FusionTD game) {
        this.game = game;
    }

    @Override
    public void show(){
        stage = new MenuStage();
        Gdx.input.setInputProcessor(stage);
        exitButton = ExitButton.create(game);
        stage.addImageButton(exitButton);

        btnCreateMap = stage.createTextButton("Create Map", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.getTextInput(MapEditorScreen.this, "Enter Map name", "", "Map Name");
            }
        });

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        tilesize = Math.min(WIDTH / TILECOLS, HEIGHT / TILEROWS);
        batch = new SpriteBatch();
        map = new int[TILEROWS][TILECOLS];
        setup();
    }

    public void setup(){
        play = new TextureAtlas.AtlasRegion(uiAtlas.findRegion("play0"));
        play.flip(true,false);
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
                drawMap(map, batch);
                batch.draw(uiAtlas.findRegion("back0"), 15.0f, 0.0f, 1f, 1f); // back button
                batch.draw(play, 0.0f , 0.0f, 1f, 1f);
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

    private void drawMap(int[][] map, SpriteBatch batch) {
        for (int y = 0; y < TILEROWS; y++) {
            for (int x = 0; x < TILECOLS; x++) {
                batch.draw(getSprite(map[MathUtils.floorPositive(MathUtils.clamp(y, 0, TILEROWS - 1))][MathUtils.floorPositive(MathUtils.clamp(x, 0, TILECOLS - 1))]), x * tilesize, y * tilesize, tilesize, tilesize);
            }
        }
    }

    private TextureAtlas.AtlasRegion getSprite(int type) {
        switch (type) {
            case 0:
                return tileAtlas.findRegion("groundTex");
            case 1:
                return tileAtlas.findRegion("roadTex");
            case 2:
                return tileAtlas.findRegion("pathStartTex");
            case 3:
                return tileAtlas.findRegion("pathEndTex");
            default:
                return tileAtlas.findRegion("groundTex");
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
        switch (state){
            case METADATA:
                stage.getViewport().update(width, height, true);
                break;
            case EDITING:
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
    public boolean keyDown(int keycode) { return false; }

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
                if (getCameraX(screenX) > 15.0f && getCameraX(screenX) < 16.0f && getCameraY(screenY) > 0.0f && getCameraY(screenY) < 1.0f) {
                    game.returnToMenu();
                } else if (getCameraX(screenX) > 0.0f && getCameraX(screenX) < 1.0f && getCameraY(screenY) > 0.0f && getCameraY(screenY) < 1.0f) {
                    saveMap();
                } else if (map[MathUtils.floorPositive(MathUtils.clamp(getCameraY(screenY), 0, TILEROWS - 1))][MathUtils.floorPositive(MathUtils.clamp(getCameraX(screenX), 0, TILECOLS - 1))] <= 3){
                    map[MathUtils.floorPositive(MathUtils.clamp(getCameraY(screenY), 0, TILEROWS - 1))][MathUtils.floorPositive(MathUtils.clamp(getCameraX(screenX), 0, TILECOLS - 1))]++;
                } else {
                    map[MathUtils.floorPositive(MathUtils.clamp(getCameraY(screenY), 0, TILEROWS - 1))][MathUtils.floorPositive(MathUtils.clamp(getCameraX(screenX), 0, TILECOLS - 1))] = 0;
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

    public boolean saveMap(){
        MapWriter mapWriter = new MapWriter();
        mapWriter.saveMap(map,mapName);
        return false;
    }

    public enum State {
        METADATA,
        EDITING,
    }
}

