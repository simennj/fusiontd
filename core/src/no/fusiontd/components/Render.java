package no.fusiontd.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import no.fusiontd.CloneableComponent;

public class Render extends Sprite implements CloneableComponent<Render> {

    private Animation<TextureRegion> animation;
    private float stateTime;

    private Render(Render render) {
        this(render.animation);
    }

    public Render(Array<TextureAtlas.AtlasRegion> textures) {
        this(.1f, textures);
    }

    public Render(float frameDuration, Array<TextureAtlas.AtlasRegion> textures) {
        this(new Animation<TextureRegion>(frameDuration, textures, Animation.PlayMode.LOOP));
    }

    public Render(Animation<TextureRegion> animation) {
        super(animation.getKeyFrame(0));
        this.animation = cloneAnimation(animation);
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

    public void animate(float deltaTime) {
        stateTime += deltaTime;
        setRegion(animation.getKeyFrame(stateTime));
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
