package no.fusiontd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuStage extends Stage {

    private TextureAtlas uiAtlas;
    private VerticalGroup mainGroup;
    private Skin skin;
    private FusionTD game;

    public MenuStage(FusionTD game) {
        super(new FitViewport(1280, 720));
        this.game = game;
        uiAtlas = new TextureAtlas("ui.atlas");
        skin = new Skin();
        populateSkin();
        createMenuGroup();
        createExitButton();
    }

    private void populateSkin() {
        BitmapFont font = generateBitmapFont();
        NinePatchDrawable blueButton = new NinePatchDrawable(uiAtlas.createPatch("blue_button"));
        NinePatchDrawable blueButtonPressed = new NinePatchDrawable(uiAtlas.createPatch("blue_button_pressed"));
        NinePatchDrawable redButton = new NinePatchDrawable(uiAtlas.createPatch("red_button"));
        NinePatchDrawable redButtonPressed = new NinePatchDrawable(uiAtlas.createPatch("red_button_pressed"));
        skin.add("font", font, BitmapFont.class);
        skin.add("default", new TextButton.TextButtonStyle(blueButton, blueButtonPressed, blueButtonPressed, font));
        skin.add("red", new TextButton.TextButtonStyle(redButton, redButtonPressed, redButtonPressed, font));
        skin.add("red", new Button.ButtonStyle(redButton, redButtonPressed, redButtonPressed));
    }

    private BitmapFont generateBitmapFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Kenney Blocks.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    private void createMenuGroup() {
        mainGroup = new VerticalGroup();
        addActor(mainGroup);
        mainGroup.setPosition(getWidth() / 2, getHeight() / 2);
        mainGroup.center();
        mainGroup.space(8);
    }

    private void createExitButton() {
        Button exitButton = new Button(skin, "red");
        exitButton.setBounds(getWidth() - 96, 32, 64, 64);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.returnToMenu();
            }
        });
        addActor(exitButton);
    }

    public TextButton createTextButton(String text, ChangeListener listener) {
        TextButton button = new TextButton(text, skin);
        button.addListener(listener);
        button.pad(8, 8, 8, 8);
        mainGroup.addActor(button);
        return button;
    }

    @Override
    public void dispose() {
        uiAtlas.dispose();
        skin.dispose();
        super.dispose();
    }

}
