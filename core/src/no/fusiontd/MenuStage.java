package no.fusiontd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import no.fusiontd.menu.MenuSkin;
import no.fusiontd.menu.NormalTextButtonFactory;

public class MenuStage extends Stage {

    private VerticalGroup mainGroup;
    private NormalTextButtonFactory textButtonFactory;
    private Skin skin = new MenuSkin();

    public MenuStage() {
        super(new FitViewport(1280, 720));
        Texture backgroundImage = new Texture(Gdx.files.internal("backgrounds/main_menu_with_creeps.png"));
        setBackground(new Image(backgroundImage));
        createMenuGroup();
        this.textButtonFactory = new NormalTextButtonFactory();
    }

    private void createMenuGroup() {
        mainGroup = new VerticalGroup();
        addActor(mainGroup);
        mainGroup.setPosition(getWidth() / 2, getHeight() / 2.6f);
        mainGroup.center();
        mainGroup.space(18);
        mainGroup.setZIndex(1);
    }

    public void addImageButton(String buttonStyle, EventListener listener) {
        //exitButton = ExitButton.create(game);
        Button button = new Button(skin, buttonStyle);
        button.addListener(listener);
        button.setBounds(getWidth() - 96, 32, 64, 64);
        addActor(button);
    }

    public void addMenuContent(Actor actor) {
        mainGroup.addActor(actor);
    }


    public TextButton createTextButton(String text, ChangeListener listener) {
        TextButton button = textButtonFactory.createTextButton(text, listener);
        mainGroup.addActor(button);
        return button;
    }

    public Dialog createDialog(String title, String text) {
        Dialog dialog = new Dialog(title, skin);
        dialog.text(text, skin.get(Label.LabelStyle.class));
        return dialog;
    }

    public void setBackground(Image backgroundImage) {
        backgroundImage.setFillParent(true);
        this.addActor(backgroundImage);
        backgroundImage.setZIndex(0);
    }

    @Override
    public void dispose() {
        skin.dispose();
        //exitButton.dispose();
        //buttonFactory.dispose();
        super.dispose();
    }

}
