package no.fusiontd.components;


import no.fusiontd.CloneableComponent;

import java.util.Arrays;
import java.util.List;

public class Targeting implements CloneableComponent<Targeting> {
    public float range;
    public float attackspeed;
    public float timeSinceLastAttack;
    public List<CloneableComponent> attack;

    public Targeting(float range, float attackspeed, CloneableComponent... attack) {
        this(range, attackspeed, Arrays.asList(attack));
    }


    public Targeting(float range, float attackspeed, List<CloneableComponent> attack) {
        this.range = range;
        this.attackspeed = attackspeed;
        this.attack = attack;
    }

    @Override
    public Targeting cloneComponent() {
        return new Targeting(range, attackspeed, attack);
    }
}
