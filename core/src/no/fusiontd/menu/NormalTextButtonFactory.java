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
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class NormalTextButtonFactory implements TextButtonFactory {
    private TextureAtlas uiAtlas;
    private Skin skin;

    public NormalTextButtonFactory() {
        uiAtlas = new TextureAtlas("ui_new.atlas");
        skin = new Skin();
        populateSkin();
    }

    private void populateSkin() {
        BitmapFont font = generateBitmapFont();
        SpriteDrawable blueButton = new SpriteDrawable(uiAtlas.createSprite("button0"));
        SpriteDrawable blueButtonPressed = new SpriteDrawable(uiAtlas.createSprite("button1"));
        SpriteDrawable redButton = new SpriteDrawable(uiAtlas.createSprite("button0"));
        SpriteDrawable redButtonPressed = new SpriteDrawable(uiAtlas.createSprite("button1"));
        skin.add("font", font, BitmapFont.class);
        skin.add("default", new TextButton.TextButtonStyle(blueButton, blueButtonPressed, blueButtonPressed, font));
        skin.add("red", new TextButton.TextButtonStyle(redButton, redButtonPressed, redButtonPressed, font));
        skin.add("red", new Button.ButtonStyle(redButton, redButtonPressed, redButtonPressed));
    }

    private BitmapFont generateBitmapFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/CANDY-SHOP-BLACK.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        //parameter.borderWidth = 8;
        parameter.color = Color.BLACK;
        //parameter.borderColor = Color.BLUE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public TextButton createTextButton(String text, ChangeListener listener) {
        TextButton button = new TextButton(text, skin);
        button.addListener(listener);
        button.pad(18);
        return button;
    }

    @Override
    public void dispose() {
        uiAtlas.dispose();
        skin.dispose();
    }
}
