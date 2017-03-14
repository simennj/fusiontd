package no.fusiontd.screens;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.fusiontd.FusionTD;
import no.fusiontd.components.Position;
import no.fusiontd.components.Velocity;
import no.fusiontd.game.Map;
import no.fusiontd.systems.VelocitySystem;

public class PlayScreen implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public int w, h;

    private FusionTD game;
    private Map map;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Texture velocitySystemTest;
    public PlayScreen(FusionTD game) {
        this.game = game;
    }

    WorldConfiguration config = new WorldConfigurationBuilder()
            .with(
                    new VelocitySystem()
                    ).build();
    World world = new World(config);
    int testEntity = world.create();


    @Override
    public void show() {
        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        map = new Map();
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        velocitySystemTest = new Texture("tiles/019.png");
        Position.mapper.create(testEntity);
        Velocity.mapper.create(testEntity);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        map.draw(delta, batch);
        batch.draw(velocitySystemTest,world.getEntity(testEntity).getComponent(Position.class).vec.x,world.getEntity(testEntity).getComponent(Position.class).vec.y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        w = width;
        h = height;
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
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        map.placeTower(screenX * WIDTH / w, HEIGHT - screenY * HEIGHT / h);
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
    public boolean scrolled(int amount) {
        return false;
    }
}
