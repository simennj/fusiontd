package no.fusiontd.components;

import no.fusiontd.CloneableComponent;

/**
 * Created by Andreas on 19.04.2017.
 */

public class Value implements CloneableComponent<Value> {
    public int cost;

    public Value(int cost) {
        this.cost = cost;
    }
    public Value cloneComponent() {
        return new Value(cost);
    }
}
