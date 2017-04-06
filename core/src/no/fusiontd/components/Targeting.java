package no.fusiontd.components;


import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;

import java.util.Arrays;
import java.util.List;

public class Targeting implements CloneableComponent<Targeting> {
    public float range;
    public float attackspeed;
    public float timeSinceLastAttack;
    public List<CloneableComponent> attack;
    public Vector2 projectileDisplacement;

    public Targeting(float range, float attackspeed, CloneableComponent... attack) {
        this(range, attackspeed, Arrays.asList(attack));
    }

    public Targeting(float range, float attackspeed, Vector2 projectileDisplacement, CloneableComponent... attack) {
        this(range, attackspeed, projectileDisplacement, Arrays.asList(attack));
    }

    public Targeting(float range, float attackspeed, List<CloneableComponent> attack) {
        this(range, attackspeed, new Vector2(), attack);
    }

    public Targeting(float range, float attackspeed, Vector2 projectileDisplacement, List<CloneableComponent> attack) {
        this.range = range;
        this.attackspeed = attackspeed;
        this.attack = attack;
        this.projectileDisplacement = new Vector2(projectileDisplacement);
    }

    @Override
    public Targeting cloneComponent() {
        return new Targeting(range, attackspeed, projectileDisplacement, attack);
    }
}
