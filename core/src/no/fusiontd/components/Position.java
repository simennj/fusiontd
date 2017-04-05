package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Vector2 implements Component {
    public float rotation;

    public Position() {
        this(0, 0, 0);
    }

    public Position(Vector2 vector2, float rotation) {
        this(vector2.x, vector2.y, rotation);
    }

    public Position(float x, float y, float rotation) {
        super(x, y);
        this.rotation = rotation;
    }

}
