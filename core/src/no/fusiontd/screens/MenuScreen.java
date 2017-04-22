package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;
import no.fusiontd.menu.ExitButton;
import no.fusiontd.menu.NormalTextButtonFactory;
import no.fusiontd.menu.OptionsButton;

public class MenuScreen implements Screen{

    private FusionTD game;
    private MenuStage stage;
    private NormalTextButtonFactory textButtonFactory;
    private OptionsButton optionsButton;

    public MenuScreen(FusionTD game) {
        this.game = game;
        optionsButton = OptionsButton.create(game);
    }

    @Override
    public void show(){
        stage = new MenuStage();
        textButtonFactory = new NormalTextButtonFactory();
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        stage.setBackground(new Image(backgroundImage));*/

        stage.addMenuContent(textButtonFactory.createTextButton("Singleplayer", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.selectMap();
            }
        }));

        stage.addMenuContent(textButtonFactory.createTextButton("Multiplayer", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openMultiplayer();
            }
        }));

        stage.addMenuContent(textButtonFactory.createTextButton("Map Editor", new ChangeListener() {
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


        stage.addImageButton(optionsButton);

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
        textButtonFactory.dispose();
        optionsButton.dispose();
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
