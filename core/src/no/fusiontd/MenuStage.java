package no.fusiontd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import no.fusiontd.menu.NormalTextButtonFactory;
import sun.security.util.DisabledAlgorithmConstraints;

import no.fusiontd.menu.ExitButton;
import no.fusiontd.menu.TextButtonFactory;

public class MenuStage extends Stage {

    private VerticalGroup mainGroup;
    private NormalTextButtonFactory textButtonFactory;

    public MenuStage() {
        super(new FitViewport(1280, 720));
        createMenuGroup();
        this.textButtonFactory = new NormalTextButtonFactory();
        //addImageButton();
    }

    private void createMenuGroup() {
        mainGroup = new VerticalGroup();
        addActor(mainGroup);
        mainGroup.setPosition(getWidth() / 2, getHeight() / 3);
        mainGroup.center();
        mainGroup.space(18);
        mainGroup.setZIndex(1);
    }

    public void addImageButton(Actor actor) {
        //exitButton = ExitButton.create(game);
        actor.setBounds(getWidth() - 96, 32, 64, 64);
        addActor(actor);
    }

    public void addMenuContent(Actor actor) {
        mainGroup.addActor(actor);
    }


    public TextButton createTextButton(String text, ChangeListener listener) {
        TextButton button = textButtonFactory.createTextButton(text, listener);
        mainGroup.addActor(button);
        return button;
    }

    public void setBackground(Image backgroundImage) {
        backgroundImage.setFillParent(true);
        this.addActor(backgroundImage);
        backgroundImage.setZIndex(0);
    }

    @Override
    public void dispose() {
        //uiAtlas.dispose();
        //skin.dispose();
        //exitButton.dispose();
        //buttonFactory.dispose();
        super.dispose();
    }

}
