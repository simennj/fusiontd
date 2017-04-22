package no.fusiontd.components;

import no.fusiontd.CloneableComponent;
import no.fusiontd.game.CloneableComponentList;

public class Upgradeable implements CloneableComponent<Upgradeable> {
    public int cost;
    public CloneableComponentList upgrades;

    public Upgradeable(int cost, CloneableComponentList upgrades) {
        this.cost = cost;
        this.upgrades = upgrades;
    }

    public Upgradeable(int cost, CloneableComponent... cloneableComponents) {
        this(cost, new CloneableComponentList(cloneableComponents));
    }

    public Upgradeable cloneComponent() {
        return new Upgradeable(cost, upgrades.cloneList());
    }
}
