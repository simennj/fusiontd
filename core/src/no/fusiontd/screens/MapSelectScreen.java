package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;

public class MapSelectScreen implements Screen {

    private FusionTD game;
    private MenuStage stage;

    public MapSelectScreen(FusionTD game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new MenuStage();
        Gdx.input.setInputProcessor(stage);

        stage.createTextButton("Map 1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame("map1");
            }
        });

        stage.createTextButton("Map 2", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame("map2");
            }
        });

        stage.createTextButton("Map 3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame("map3");
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
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
}
