package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

public class Attack implements CloneableComponent<Attack> {
    public float projradius;
    public float expradius;
    public float damage;

    public Attack(float projradius, float damage) {
        this.projradius = projradius;
        this.damage = damage;
    }

    @Override
    public Attack cloneComponent() {
        return new Attack(projradius, damage);
    }
}
