package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class Durability implements Component {
    public float life = 1;

    public Durability(float maxLife) {
        this.life = maxLife;
    }

}
