package no.fusiontd.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;



public class LabelFactory {

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

    public Label createLabel(String labelText) {
        BitmapFont font = generateBitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label label = new Label(labelText, labelStyle);
        return label;
    }

}
