package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;
import no.fusiontd.menu.ExitButton;
import no.fusiontd.menu.NormalTextButtonFactory;
import no.fusiontd.menu.TextButtonFactory;

public class MapSelectScreen implements Screen {

    private FusionTD game;
    private MenuStage stage;
    private TextButtonFactory textButtonFactory;
    private ExitButton exitButton;

    public MapSelectScreen(FusionTD game) {
        this.game = game;
        exitButton = ExitButton.create(game);
    }

    @Override
    public void show() {
        stage = new MenuStage();
        textButtonFactory = new NormalTextButtonFactory();
        Gdx.input.setInputProcessor(stage);

        Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        stage.setBackground(new Image(backgroundImage));

        stage.addMenuContent(textButtonFactory.createTextButton("Map 1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame("map1");
            }
        }));

        stage.addMenuContent(textButtonFactory.createTextButton("Map 2", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame("test");
            }
        }));

        stage.addMenuContent(textButtonFactory.createTextButton("Map 3", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.startGame("map3");
            }
        }));

        stage.addImageButton(exitButton);
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
        textButtonFactory.dispose();
        exitButton.dispose();
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
