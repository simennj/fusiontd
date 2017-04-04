package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.fusiontd.FusionTD;
import no.fusiontd.game.CreepSpawner;
import no.fusiontd.game.EntityComponentManager;
import no.fusiontd.game.GameController;
import no.fusiontd.game.Map;

public class MapSelectScreen implements Screen, InputProcessor {

    private static final float WIDTH = 16, HEIGHT = 9;
    private SpriteBatch batch;
    private Texture img;
    private FusionTD game;
    private OrthographicCamera camera;
    private float aspectRatio;
    private float tilesize;
    private int screenWidth, screenHeight;
    private float heightOffset, widthOffset;
    private float w, h;

    public MapSelectScreen(FusionTD game) {
        this.game = game;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        img = new Texture("ui/green_button00.png");
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        tilesize = Math.min(WIDTH/screenWidth, HEIGHT/screenHeight);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 385, 250);
        batch.draw(img, 385, 150);
        batch.draw(img, 385, 50);
        batch.end();
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
        batch.dispose();
        img.dispose();
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

        System.out.println(screenX + "," + screenY);
        if (400 <= screenX && screenX <= 590 && 240 <= screenY && screenY <= 290) {
            game.startGame("map1");
            return false;
        } else if (400 <= screenX && screenX <= 590 && 340 <= screenY && screenY <= 390) {
            game.startGame("map2");
            return false;
        } else if(400 <= screenX && screenX <= 590 && 440 <= screenY && screenY <= 490) {
            game.startGame("map3");
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
}
