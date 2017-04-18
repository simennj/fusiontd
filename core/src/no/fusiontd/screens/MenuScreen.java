package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;

public class MenuScreen implements Screen{

    private FusionTD game;
    private MenuStage stage;

    public MenuScreen(FusionTD game) {
        this.game = game;
    }

    @Override
    public void show(){
        stage = new MenuStage(game);
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.createTextButton("Singleplayer", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.selectMap();
            }
        });

        stage.createTextButton("Multiplayer", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /*Input.TextInputListener til = new Input.TextInputListener() {
                    @Override
                    public void input(String playerName) {
                        game.connectMP(playerName);
                    }

                    @Override
                    public void canceled() {

                    }
                };
                Gdx.input.getTextInput(til, "Your Name?", "Saltminer", "");*/
                game.openMultiplayer();
            }
        });

        stage.createTextButton("Options", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openOptions();
            }
        });

        stage.createTextButton("Map Editor", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openOptions();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose () {
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
