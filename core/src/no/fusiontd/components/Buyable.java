package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

/**
 * Created by Andreas on 19.04.2017.
 */

public class Buyable implements CloneableComponent<Buyable> {
    public int cost;

    public Buyable(int cost) {
        this.cost = cost;
    }
    public Buyable cloneComponent() {
        return new Buyable(cost);
    }
}
