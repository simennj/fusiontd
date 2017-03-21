package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class Attack implements Component {
    public int projradius;
    public int damage;

    public Attack() {
    }

    public void setRadius(int newRadius) {
        this.projradius = newRadius;
    }

    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }

}
