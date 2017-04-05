package no.fusiontd.components;

import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;


public class Velocity extends Vector2 implements CloneableComponent<Velocity> {

    public Velocity() {
        this(0, 0);
    }

    public Velocity(Vector2 vector) {
        this(vector.x, vector.y);
    }

    public Velocity(float x, float y) {
        super(x, y);
    }

    @Override
    public Velocity cloneComponent() {
        return new Velocity(this);
    }
}
