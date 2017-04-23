package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

public class Attackable implements CloneableComponent<Attackable> {
    public float creepradius;

    public Attackable(float newRadius) {
        this.creepradius = newRadius;
    }

    @Override
    public Attackable cloneComponent() {
        return new Attackable(creepradius);
    }
}
