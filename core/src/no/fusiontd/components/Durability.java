package no.fusiontd.components;

import com.badlogic.ashley.core.Component;

public class Durability implements Component {
    public int life;

    public Durability(int maxLife) {
        this.life = maxLife;
    }

}
