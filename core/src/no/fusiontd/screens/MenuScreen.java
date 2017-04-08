package no.fusiontd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import no.fusiontd.FusionTD;

public class MenuScreen implements Screen , Input.TextInputListener{

    private FusionTD game;
    private Stage stage;

    public MenuScreen(FusionTD game) {
        this.game = game;
    }

    @Override
    public void show(){
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void create () {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");
        TextureRegion upRegion = uiAtlas.findRegion("blue_button00");
        TextureRegion downRegion = uiAtlas.findRegion("blue_button03");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Kenney Blocks.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(upRegion);
        style.down = new TextureRegionDrawable(downRegion);
        style.font = font12;

        Table table1 = new Table();
        stage.addActor(table1);
        table1.setPosition(stage.getWidth()/2, stage.getHeight()/2);

        TextButton button1 = new TextButton("Singleplayer", style);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.selectMap(false);
            }
        });
        table1.add(button1);

        Table table2 = new Table();
        stage.addActor(table2);
        table2.setFillParent(true);
        table2.setPosition(0,-stage.getHeight()/8);

        TextButton button2 = new TextButton("Multiplayer", style);
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.getTextInput(MenuScreen.this, "Your Name?", "Saltminer", "");
            }
        });
        table2.add(button2);

        Table table3 = new Table();
        stage.addActor(table3);
        table3.setFillParent(true);
        table3.setPosition(0,-stage.getHeight()/4);

        TextButton button3 = new TextButton("Options", style);
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.openOptions();
            }
        });
        table3.add(button3);

        // back button
        Button.ButtonStyle exitStyle = new Button.ButtonStyle();
        exitStyle.up = new TextureRegionDrawable(uiAtlas.findRegion("grey_box"));
        exitStyle.down = new TextureRegionDrawable(uiAtlas.findRegion("blue_boxCross"));

        Table exitTable = new Table();
        stage.addActor(exitTable);
        exitTable.setFillParent(true);
        exitTable.setPosition(stage.getWidth()/2 - stage.getWidth()/8,-stage.getHeight()/2 + stage.getHeight()/8);

        Button exitButton = new Button(exitStyle);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // set code here
            }
        });
        exitTable.add(exitButton);
    }

    public void render () {
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

    @Override
    public void input(String playerName) {
        game.connectMP(playerName);
    }

    @Override
    public void canceled() {

    }
}
