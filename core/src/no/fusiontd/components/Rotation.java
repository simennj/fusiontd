package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class Rotation implements Component {
    public float rotation;

    public Rotation() {
        this(0);
    }

    public Rotation(float r) {
        this.rotation = r;
    } // angle in radians
}