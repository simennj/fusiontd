package no.fusiontd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class BitTextButtonFactory implements TextButtonFactory {
    private TextureAtlas uiAtlas;
    private Skin skin;

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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Kenney Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.borderWidth = 8;
        parameter.borderColor = Color.BLUE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public TextButton createTextButton(String text, ChangeListener listener) {
        TextButton button = new TextButton(text, skin);
        button.addListener(listener);
        button.pad(8, 8, 8, 8);
        return button;
    }

    @Override
    public void dispose() {
        uiAtlas.dispose();
        skin.dispose();
    }
}
