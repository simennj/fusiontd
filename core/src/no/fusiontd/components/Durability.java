package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

public class Durability implements CloneableComponent<Durability> {
    public float life = 1;

    public Durability(float maxLife) {
        this.life = maxLife;
    }

    @Override
    public Durability cloneComponent() {
        return new Durability(life);
    }
}
