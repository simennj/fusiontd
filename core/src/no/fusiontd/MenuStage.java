package no.fusiontd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import sun.security.util.DisabledAlgorithmConstraints;

public class MenuStage extends Stage {

    private TextureAtlas uiAtlas;
    private VerticalGroup mainGroup;
    private Skin skin;
    private FusionTD game;
    private Label.LabelStyle popUpTextStyle;

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
        BitmapFont font = generateBitmapFont(64);
        BitmapFont tfFont = generateBitmapFont(22);
        NinePatchDrawable blueButton = new NinePatchDrawable(uiAtlas.createPatch("blue_button"));
        NinePatchDrawable blueButtonPressed = new NinePatchDrawable(uiAtlas.createPatch("blue_button_pressed"));
        NinePatchDrawable redButton = new NinePatchDrawable(uiAtlas.createPatch("red_button"));
        NinePatchDrawable redButtonPressed = new NinePatchDrawable(uiAtlas.createPatch("red_button_pressed"));
        NinePatchDrawable yellowPopup = new NinePatchDrawable(uiAtlas.createPatch("yellow_popUp"));

        Label.LabelStyle labelStyle = new Label.LabelStyle(tfFont, Color.WHITE);
        labelStyle.background = redButton;

        popUpTextStyle = new Label.LabelStyle(tfFont, Color.BLACK);

        skin.add("font", font, BitmapFont.class);
        skin.add("tfFont", tfFont, BitmapFont.class);
        skin.add("default", new TextButton.TextButtonStyle(blueButton, blueButtonPressed, blueButtonPressed, font));
        skin.add("red", new TextButton.TextButtonStyle(redButton, redButtonPressed, redButtonPressed, font));
        skin.add("red", new Button.ButtonStyle(redButton, redButtonPressed, redButtonPressed));
        skin.add("default", new TextField.TextFieldStyle(tfFont, Color.WHITE, redButton, redButtonPressed, redButtonPressed));
        skin.add("default", labelStyle);
        skin.add("default", new Window.WindowStyle(tfFont, Color.BLACK, yellowPopup));

    }

    private BitmapFont generateBitmapFont(int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Kenney Blocks.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
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

    public Label createLabel(String text){
        Label label = new Label(text, skin);
        label.setAlignment(Align.center);
        mainGroup.addActor(label);
        return label;
    }

    public Dialog createDialog(String title, String text){
        Dialog dialog = new Dialog(title, skin);
        dialog.text(text, popUpTextStyle);

        return dialog;
    }

    public TextField createTextField(String text){
        TextField textField = new TextField(text, skin);
        mainGroup.addActor(textField);
        return textField;
    }

    @Override
    public void dispose() {
        uiAtlas.dispose();
        skin.dispose();
        super.dispose();
    }

}
