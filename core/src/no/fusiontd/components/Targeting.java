package no.fusiontd.components;


import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;
import no.fusiontd.game.CloneableComponentList;

public class Targeting implements CloneableComponent<Targeting> {
    public float range;
    public float attackspeed;
    public float timeSinceLastAttack;
    public boolean aim;
    public CloneableComponentList attack;
    public Vector2 projectileDisplacement;

    public Targeting(float range, float attackspeed, boolean aim, CloneableComponent... attack) {
        this(range, attackspeed, aim, new CloneableComponentList(attack));
    }

    public Targeting(float range, float attackspeed, Vector2 projectileDisplacement, boolean aim, CloneableComponent... attack) {
        this(range, attackspeed, projectileDisplacement, aim, new CloneableComponentList(attack));
    }

    private Targeting(float range, float attackspeed, boolean aim, CloneableComponentList attack) {
        this(range, attackspeed, new Vector2(), aim, attack);
    }

    private Targeting(float range, float attackspeed, Vector2 projectileDisplacement, boolean aim, CloneableComponentList attack) {
        this.range = range;
        this.attackspeed = attackspeed;
        this.attack = attack;
        this.aim = aim;
        this.projectileDisplacement = new Vector2(projectileDisplacement);
    }

    @Override
    public Targeting cloneComponent() {
        return new Targeting(range, attackspeed, projectileDisplacement, aim, attack);
    }
}
