package no.fusiontd.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.List;

import no.fusiontd.CloneableComponent;

/**
 * Created by Andreas on 19.04.2017.
 */

public class Upgradeable implements CloneableComponent<Upgradeable> {
    public float cost;
    public List<CloneableComponent> upgrades;
    public Upgradeable(float cost, List<CloneableComponent> upgrades){
        this.cost = cost;
        this.upgrades = upgrades;
    }
    public Upgradeable(float cost, CloneableComponent... cloneableComponents) {
        this(cost, Arrays.asList(cloneableComponents));
    }
    public Upgradeable cloneComponent() {
        return new Upgradeable(cost, upgrades);
    }
}
