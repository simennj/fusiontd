package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

public class Attack implements CloneableComponent<Attack> {
    public float projradius;
    public float expradius;

    public Attack(float projradius) {
        this.projradius = projradius;
    }

    @Override
    public Attack cloneComponent() {
        return new Attack(projradius);
    }
}
