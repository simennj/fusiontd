package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Placement extends Vector2 implements Component {
    public float rotation;

    public Placement() {
        this(0, 0, 0);
    }

    public Placement(Vector2 vector2, float rotation) {
        this(vector2.x, vector2.y, rotation);
    }

    public Placement(float x, float y, float rotation) {
        super(x, y);
        this.rotation = rotation;
    }

}
