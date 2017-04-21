package no.fusiontd.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.fusiontd.CloneableComponent;
import no.fusiontd.Graphics;

public class Render extends Sprite implements CloneableComponent<Render> {

    Animation<TextureRegion> animation;

    public Render(String name) {
        this(Graphics.getRegion(name));
    }

    public Render(Render render) {
        super(render);
        if (render.animation != null) {
            this.animation = cloneAnimation(render.animation);
        }
    }

    public Render(TextureRegion rect) {
        this(rect, 1, 1);
    }

    public Render(TextureRegion rect, float w, float h) {
        super(rect);
        setScale(1, 1);
    }

    private static Animation<TextureRegion> cloneAnimation(Animation<TextureRegion> original) {
        Animation<TextureRegion> clone = new Animation<TextureRegion>(
                original.getFrameDuration(),
                original.getKeyFrames()
        );
        clone.setPlayMode(original.getPlayMode());
        return clone;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - super.getOriginX(), y - super.getOriginY());
    }

    @Override
    public void setScale(float x, float y) {
        super.setScale(x / 128, y / 128);
    }

    @Override
    public Render cloneComponent() {
        return new Render(this);
    }
}
