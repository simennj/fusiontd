package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Render extends Sprite implements Component {

    public Render(TextureRegion rect) {
        this(rect, 1, 1);
    }

    public Render(TextureRegion rect, float w, float h) {
        super(rect);
        setScale(1, 1);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - super.getOriginX(), y - super.getOriginY());
    }

    @Override
    public void setScale(float x, float y) {
        super.setScale(x / 128, y / 128);
    }

}
