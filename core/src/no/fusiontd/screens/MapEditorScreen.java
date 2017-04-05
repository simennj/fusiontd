package no.fusiontd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import no.fusiontd.FusionTD;
import no.fusiontd.game.CreepSpawner;
import no.fusiontd.game.EntityComponentManager;
import no.fusiontd.game.GameController;
import no.fusiontd.game.Map;
import no.fusiontd.game.TowerSpawner;
import no.fusiontd.maps.MapReader;

public class MapEditorScreen implements Screen, InputProcessor {


    private static final float WIDTH = 16, HEIGHT = 9;
    public SpriteBatch batch;
    private float w, h;
    private EntityComponentManager engine;
    private FusionTD game;
    private Map map;
    private GameController controller;
    private CreepSpawner creepSpawner;
    private TowerSpawner towerSpawner;
    private OrthographicCamera camera;
    private float aspectRatio;
    private float tilesize;
    private int screenWidth, screenHeight;
    private float heightOffset, widthOffset;
    private boolean multiplayer;
    private String mapName;
    private MapReader mapReader;


    public MapEditorScreen(FusionTD game) {
        this.game = game;
    }


    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        map = new Map(mapName);
        tilesize = Math.min(WIDTH / map.TILECOLS, HEIGHT / map.TILEROWS);
        //controller = new GameController(map, this);
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        //engine = new EntityComponentManager(this);
        towerSpawner = new TowerSpawner(engine);

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
        int tile = map.getTile(getCameraX(screenX), getCameraY(screenY));
        map.placeTile((int)getCameraX(screenX), (int)getCameraY(screenY));

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

    public int[][] newMap(){
        int[][] initMap = new int[map.TILEROWS][map.TILECOLS];
        for (int i=0; i<map.TILEROWS; i++){
            for (int j=0; j<map.TILECOLS; j++){
                initMap[i][j]=0;
            }
        }
        return initMap;
    }

    public int[][] loadMap(String mapName){
        int[][] mapLoad = mapReader.loadMap(mapName, map.TILEROWS, map.TILECOLS);
        return mapLoad;
    }
    public float getCameraX(int screenX) {
        return (screenX - widthOffset) * w / screenWidth;
    }

    public float getCameraY(int screenY) {
        return HEIGHT - (screenY - heightOffset) * h / screenHeight;
    }
}
