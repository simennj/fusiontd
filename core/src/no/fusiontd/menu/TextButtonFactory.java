package no.fusiontd.menu;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;

public interface TextButtonFactory extends Disposable{

    TextButton createTextButton(String text, ChangeListener listener);

}
