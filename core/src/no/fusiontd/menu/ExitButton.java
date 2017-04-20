package no.fusiontd.menu;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;

import no.fusiontd.FusionTD;

public class ExitButton extends Button implements Disposable{
    private TextureAtlas uiAtlas;
    private Skin skin;

    private ExitButton(final FusionTD game, Skin skin, TextureAtlas uiAtlas) {
        super(skin, "red");
        this.skin = skin;
        this.uiAtlas = uiAtlas;
        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.returnToMenu();
            }
        });

    }

    public static ExitButton create(FusionTD game) {
        TextureAtlas uiAtlas = new TextureAtlas("ui_new.atlas");
        Skin skin = new Skin();

        SpriteDrawable redButton = new SpriteDrawable(uiAtlas.createSprite("back0"));
        SpriteDrawable redButtonPressed = new SpriteDrawable(uiAtlas.createSprite("back1"));
        skin.add("red", new Button.ButtonStyle(redButton, redButtonPressed, redButtonPressed));
        // exitButton.setBounds(getWidth() - 96, 32, 64, 64);

        return new ExitButton(game, skin, uiAtlas);
    }

    @Override
    public void dispose() {
        uiAtlas.dispose();
        skin.dispose();
    }
}