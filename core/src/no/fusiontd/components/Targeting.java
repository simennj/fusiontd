package no.fusiontd.components;


import com.badlogic.ashley.core.Component;

public class Targeting implements Component {
    public float range;
    public float attackspeed;
    public float timeSinceLastAttack;


    public Targeting(float range, float attackspeed) {
        this.range = range;
        this.attackspeed = attackspeed;
    }
}
