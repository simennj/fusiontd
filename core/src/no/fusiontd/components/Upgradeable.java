package no.fusiontd.components;

import com.badlogic.gdx.utils.Array;

import no.fusiontd.CloneableComponent;

/**
 * Created by Andreas on 19.04.2017.
 */

public class Upgradeable implements CloneableComponent<Upgradeable> {
    public float cost;
    public Array<CloneableComponent> upgrades;
    public Upgradeable(float cost, Array<CloneableComponent> upgrades){
        this.cost = cost;
        this.upgrades = upgrades;

    }
    public Upgradeable cloneComponent() {
        return new Upgradeable(cost, upgrades);
    }
}
