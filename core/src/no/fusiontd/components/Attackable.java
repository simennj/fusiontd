package no.fusiontd.components;

import com.badlogic.ashley.core.Component;


public class Attackable implements Component {
    public float creepradius;

    public Attackable(float newRadius) {
        this.creepradius = newRadius;
    }
}
