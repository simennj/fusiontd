package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.fusiontd.FusionTD;
import no.fusiontd.MenuStage;
import no.fusiontd.menu.CheckButton;
import no.fusiontd.menu.NormalTextButtonFactory;
import no.fusiontd.menu.TextButtonFactory;

public class OptionScreen implements Screen {

    private FusionTD game;
    private MenuStage stage;
    private TextButtonFactory textButtonFactory;
    private CheckButton checkButton;

    public OptionScreen(FusionTD game) {
        this.game = game;
    }

    @Override
    public void show(){
        stage = new MenuStage();
        /*Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        stage.setBackground(new Image(backgroundImage));*/
        textButtonFactory = new NormalTextButtonFactory();
        checkButton = CheckButton.create("MyButton");
        checkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("FIRE!");
                checkButton.setChecked(true); // this should not be done by the view
            }
        });
        Gdx.input.setInputProcessor(stage);

        stage.addMenuContent(checkButton);

        stage.addMenuContent(textButtonFactory.createTextButton("Button 1", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        }));

        stage.addMenuContent(textButtonFactory.createTextButton("Button 2", new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("2!");
            }
        }));

        stage.addImageButton("backButton", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.returnToMenu();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(3 / 255f, 73 / 255f, 114 / 255f, 1);
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
        checkButton.dispose();
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
