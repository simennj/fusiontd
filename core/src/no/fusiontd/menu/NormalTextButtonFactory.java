package no.fusiontd.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class NormalTextButtonFactory implements TextButtonFactory {
    private Skin skin;

    public NormalTextButtonFactory() {
        skin = new MenuSkin();
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
        skin.dispose();
    }
}