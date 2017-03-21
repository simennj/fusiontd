package no.fusiontd.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;


public class Velocity extends Vector2 implements Component {

    public Velocity() {
        this(0, 0);
    }

    public Velocity(float x, float y) {
        super(x, y);
    }

}
