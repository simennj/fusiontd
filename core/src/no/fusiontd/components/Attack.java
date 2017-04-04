package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class Attack implements Component {
    public float projradius;
    public float expradius;

    public Attack(float projradius) {
        this.projradius = projradius;
    }

}
