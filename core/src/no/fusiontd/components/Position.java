package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Vector2 implements Component {

    public Position() {
        this(0, 0);
    }

    public Position(Vector2 vector2) {
        this(vector2.x, vector2.y);
    }

    public Position(float x, float y) {
        super(x, y);
    }

}
