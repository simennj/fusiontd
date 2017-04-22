package no.fusiontd.components;

import no.fusiontd.CloneableComponent;
import no.fusiontd.game.CloneableComponentList;

public class Upgradeable implements CloneableComponent<Upgradeable> {
    public float cost;
    public CloneableComponentList upgrades;

    public Upgradeable(float cost, CloneableComponentList upgrades) {
        this.cost = cost;
        this.upgrades = upgrades;
    }

    public Upgradeable(float cost, CloneableComponent... cloneableComponents) {
        this(cost, new CloneableComponentList(cloneableComponents));
    }

    public Upgradeable cloneComponent() {
        return new Upgradeable(cost, upgrades.cloneList());
    }
}
