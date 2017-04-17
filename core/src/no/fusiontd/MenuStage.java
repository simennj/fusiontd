package no.fusiontd;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import no.fusiontd.menu.ExitButton;
import no.fusiontd.menu.TextButtonFactory;

public class MenuStage extends Stage {

    private VerticalGroup mainGroup;

    public MenuStage() {
        super(new FitViewport(1280, 720));
        createMenuGroup();
        //addImageButton();
    }

    private void createMenuGroup() {
        mainGroup = new VerticalGroup();
        addActor(mainGroup);
        mainGroup.setPosition(getWidth() / 2, getHeight() / 2);
        mainGroup.center();
        mainGroup.space(8);
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
    /*
    public TextButton createTextButton(String text, ChangeListener listener) {
        TextButton button = buttonFactory.createTextButton(text, listener);
        mainGroup.addActor(button);
        return button;
    }*/

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
