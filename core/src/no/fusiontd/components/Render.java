package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Render extends Sprite implements Component {

    public Render() {
        this(new TextureRegion(new Texture("tiles/128.png")));
    }

    public Render(TextureRegion rect) {
        this(rect, 1, 1);
    }

    public Render(TextureRegion rect, float w, float h) {
        super(rect);
        setScale(1, 1);
    }

    @Override
    public void setPosition(float x, float y) {
        this.setPosition(x - this.getOriginX(), y - this.getOriginY());
    }

    @Override
    public void setScale(float x, float y) {
        this.setScale(x / 128, y / 128);
    }

}
