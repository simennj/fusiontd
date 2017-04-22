package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;
import no.fusiontd.menu.NormalTextButtonFactory;

public class MenuScreen implements Screen {

    private final FusionTD game;
    private MenuStage stage;
    private NormalTextButtonFactory textButtonFactory;

    public MenuScreen(final FusionTD game) {
        this.game = game;

    }

    @Override
    public void show() {
        stage = new MenuStage();
        textButtonFactory = new NormalTextButtonFactory();
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        stage.setBackground(new Image(backgroundImage));*/

        stage.addMenuContent(textButtonFactory.createTextButton("SINGLEPLAYER", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.selectMap();
            }
        }));

        stage.addMenuContent(textButtonFactory.createTextButton("MULTIPLAYER", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openMultiplayer();
            }
        }));

        stage.addMenuContent(textButtonFactory.createTextButton("MAP EDITOR", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openMapEditor();
            }
        }));

        /*
        final TextButton mySpecialButton = textButtonFactory.createTextButton("mySpecial", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("lolol");
                game.openMapEditor();
            }
        });
        stage.addMenuContent(mySpecialButton);

        stage.addMenuContent(textButtonFactory.createTextButton("testButton", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.removeMenuContent(mySpecialButton);

            }
        }));*/


        stage.addImageButton("gearButton", new ChangeListener() {
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

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        textButtonFactory.dispose();
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
