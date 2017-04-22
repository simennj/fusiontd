package no.fusiontd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class MenuSkin extends Skin {
    private TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");
    private BitmapFont font;

    public MenuSkin() {
        SpriteDrawable gearButton = new SpriteDrawable(uiAtlas.createSprite("gear0"));
        SpriteDrawable gearButtonPressed = new SpriteDrawable(uiAtlas.createSprite("gear1"));
        add("gearButton", new Button.ButtonStyle(gearButton, gearButtonPressed, gearButtonPressed));

        SpriteDrawable backButton = new SpriteDrawable(uiAtlas.createSprite("back0"));
        SpriteDrawable backButtonPressed = new SpriteDrawable(uiAtlas.createSprite("back1"));
        add("backButton", new Button.ButtonStyle(backButton, backButtonPressed, backButtonPressed));

        font = generateBitmapFont();

        add("font", font, BitmapFont.class);

        SpriteDrawable button = new SpriteDrawable(uiAtlas.createSprite("button0"));
        SpriteDrawable buttonPressed = new SpriteDrawable(uiAtlas.createSprite("button1"));
        add("default", new TextButton.TextButtonStyle(button, buttonPressed, buttonPressed, font));
        add("default", new Window.WindowStyle(font, Color.BLACK, button));

        {
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = font;
            add("default", labelStyle, Label.LabelStyle.class);
        }

    }

    private static BitmapFont generateBitmapFont() {
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
    public void dispose() {
        super.dispose();
        uiAtlas.dispose();
    }
}
