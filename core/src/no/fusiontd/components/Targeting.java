package no.fusiontd.components;


import com.badlogic.ashley.core.Component;
import no.fusiontd.CloneableComponent;

import java.util.List;

public class Targeting implements Component {
    public float range;
    public float attackspeed;
    public float timeSinceLastAttack;
    public List<CloneableComponent> attack;


    public Targeting(float range, float attackspeed, List<CloneableComponent> attack) {
        this.range = range;
        this.attackspeed = attackspeed;
        this.attack = attack;
    }
}
