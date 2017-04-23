package no.fusiontd.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import no.fusiontd.CloneableComponent;

import java.util.Random;

public class Render extends Sprite implements CloneableComponent<Render> {

    private Animation<TextureRegion> animation;
    private float stateTime;
    private float animationDuration;
    private float nextAnimation;

    private Render(Render render) {
        this(render.animation);
    }

    public Render(Array<TextureAtlas.AtlasRegion> textures) {
        this(.1f, textures);
    }

    private Render(float frameDuration, Array<TextureAtlas.AtlasRegion> textures) {
        this(new Animation<TextureRegion>(frameDuration, textures, Animation.PlayMode.REVERSED));
    }

    private Render(Animation<TextureRegion> animation) {
        super(animation.getKeyFrame(0));
        this.animation = cloneAnimation(animation);
        this.animationDuration = animation.getAnimationDuration();
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
        if (stateTime <= animationDuration) {
            setRegion(animation.getKeyFrame(stateTime));
        } else if (stateTime > nextAnimation) {
            nextAnimation = new Random().nextInt(10) * animationDuration;
            stateTime = 0;
        }
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
