package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import no.fusiontd.FusionTD;
import no.fusiontd.Graphics;
import no.fusiontd.MenuStage;
import no.fusiontd.maps.MapWriter;

public class MapEditorScreen implements Screen, Input.TextInputListener, InputProcessor {


    private static final float WIDTH = 16, HEIGHT = 9;
    public final int TILEROWS = 9, TILECOLS = 16;
    public SpriteBatch batch;
    private int width,height;
    private FusionTD game;
    private TextureAtlas atlas;
    private Skin skin;
    private List<String> playerList;
    private String serverIP, MapName;
    private boolean serverRunning = false;
    private TextField typedIPField, serverIPField;
    private TextButton btnCreateMap, btnHostGame, btnAccept;
    private MenuStage stage;
    private float w, h;
    private OrthographicCamera camera;
    private float aspectRatio;
    private float tilesize;
    private int screenWidth, screenHeight;
    private float heightOffset, widthOffset;
    private MapEditorScreen.State state = MapEditorScreen.State.METADATA;
    public tileType tileType;
    private String mapName;
    private int[][] map;

    public MapEditorScreen(FusionTD game) {
        this.game = game;
        atlas = new TextureAtlas(Gdx.files.internal("ui.atlas"));
    }

    @Override
    public void show(){
        stage = new MenuStage();
        Gdx.input.setInputProcessor(stage);

        final TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Kenney Blocks.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        final BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        //Displayed when you try you press HostGame a second time
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font12;
        TextureRegion windowBackground = uiAtlas.findRegion("yellow_button03");
        windowStyle.background = new TextureRegionDrawable(windowBackground);

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

    public enum tileType{
        PATH,
        GRASS,
        NORTH,
        EAST,
        SOUTH,
        WEST,
        NORTHEAST,
        NORTHWEST,
        SOUTHEAST,
        SOUTHWEST,
    }

    private TextureAtlas.AtlasRegion checkNeighbours(int[][] map, int x, int y){

        int[] grass = {0,0,0,0,0,0,0,0};
        int[] neighbours = {0,0,0,0,0,0,0,0};
        neighbours[0] = map[x-1][y-1];
        neighbours[1] = map[x][y-1];
        neighbours[2] = map[x+1][y-1];
        neighbours[3] = map[x+1][y];
        neighbours[4] = map[x+1][y+1];
        neighbours[5] = map[x][y+1];
        neighbours[6] = map[x-1][y+1];
        neighbours[7] = map[x-1][y];


            if (neighbours.equals(grass)){
                Graphics.getTile(tileType.GRASS);
            }
        return Graphics.getTile(tileType.GRASS);
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
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 62:
                saveMap();
        }
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
                if (map[MathUtils.floorPositive(MathUtils.clamp(getCameraY(screenY), 0, TILEROWS - 1))][MathUtils.floorPositive(MathUtils.clamp(getCameraX(screenX), 0, TILECOLS - 1))] <= 3){
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

