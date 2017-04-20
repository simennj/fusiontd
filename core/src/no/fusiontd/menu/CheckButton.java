package no.fusiontd.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;

import no.fusiontd.FusionTD;

public class CheckButton extends ImageTextButton implements Disposable {
    private TextureAtlas uiAtlas;
    private Skin skin;

    private CheckButton(String name, Skin skin, TextureAtlas uiAtlas) {
        super(name, skin, "yellow");
        this.skin = skin;
        this.uiAtlas = uiAtlas;
        this.pad(18);
        this.setDisabled(true);
    }

    public static CheckButton create(String name) {
        TextureAtlas uiAtlas = new TextureAtlas("ui_new.atlas");
        Skin skin = new Skin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/CANDY-SHOP-BLACK.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        //parameter.borderWidth = 8;
        parameter.color = Color.BLACK;
        //parameter.borderColor = Color.BLUE;
        BitmapFont font = generator.generateFont(parameter);

        SpriteDrawable redButton = new SpriteDrawable(uiAtlas.createSprite("button0"));
        SpriteDrawable redButtonPressed = new SpriteDrawable(uiAtlas.createSprite("button1"));
        skin.add("yellow", new ImageTextButton.ImageTextButtonStyle(redButton, redButton, redButtonPressed, font));
        // exitButton.setBounds(getWidth() - 96, 32, 64, 64);

        return new CheckButton(name, skin, uiAtlas);
    }

    @Override
    public void dispose() {
        uiAtlas.dispose();
        skin.dispose();
    }
}
