package no.fusiontd.components;

import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;

public class Geometry extends Vector2 implements CloneableComponent<Geometry> {
    public float rotation;
    public float radius;

    public Geometry() {
        this(0, 0, 0, 0);
    }

    public Geometry(Geometry geometry) {
        this(geometry.x, geometry.y, geometry.rotation, geometry.radius);
    }

    public Geometry(Vector2 vector2, float rotation, float radius) {
        this(vector2.x, vector2.y, rotation, radius);
    }

    public Geometry(float x, float y, float rotation, float radius) {
        super(x, y);
        this.rotation = rotation;
        this.radius = radius;
    }

    @Override
    public Geometry cloneComponent() {
        return new Geometry(this, rotation, radius);
    }
}
