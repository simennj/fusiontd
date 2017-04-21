package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Upgradeable implements CloneableComponent<Upgradeable> {
    public float cost;
    public List<CloneableComponent> upgrades;

    public Upgradeable(float cost, List<CloneableComponent> upgrades) {
        this.cost = cost;
        this.upgrades = upgrades;
    }

    public Upgradeable(float cost, CloneableComponent... cloneableComponents) {
        this(cost, Arrays.asList(cloneableComponents));
    }

    public Upgradeable cloneComponent() {
        List<CloneableComponent> clonedUpgrades = new LinkedList<CloneableComponent>();
        for (CloneableComponent component : upgrades) {
            clonedUpgrades.add(component.cloneComponent());
        }
        return new Upgradeable(cost, clonedUpgrades);
    }
}
