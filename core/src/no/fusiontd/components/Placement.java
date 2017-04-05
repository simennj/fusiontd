package no.fusiontd.components;

import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;

public class Placement extends Vector2 implements CloneableComponent<Placement> {
    public float rotation;

    public Placement() {
        this(0, 0, 0);
    }

    public Placement(Placement placement) {
        this(placement.x, placement.y, placement.rotation);
    }

    public Placement(Vector2 vector2, float rotation) {
        this(vector2.x, vector2.y, rotation);
    }

    public Placement(float x, float y, float rotation) {
        super(x, y);
        this.rotation = rotation;
    }

    @Override
    public Placement cloneComponent() {
        return new Placement(this, rotation);
    }
}
