package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;
import no.fusiontd.components.*;

public class TargetingSystem extends IteratingSystem {
    private ComponentMapper<Placement> mPos = ComponentMapper.getFor(Placement.class);
    private ComponentMapper<Targeting> mTar = ComponentMapper.getFor(Targeting.class);
    private ComponentMapper<PathFollow> mPat = ComponentMapper.getFor(PathFollow.class);
    private ComponentMapper<Velocity> mVel = ComponentMapper.getFor(Velocity.class);
    private ImmutableArray<Entity> creeps;

    public TargetingSystem() {
        super(Family.all(Placement.class, Targeting.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        creeps = engine.getEntitiesFor(Family.all(Attackable.class, Placement.class, PathFollow.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Targeting targeting = mTar.get(entity);
        Placement entityPlacement = mPos.get(entity);
        if (reloading(deltaTime, targeting)) return;
        Entity firstInRange = getFirstInRange(targeting, entityPlacement);
        if (firstInRange != null) {
            getEngine().addEntity(getMissile(targeting, entityPlacement, firstInRange));
        }
    }

    private Entity getMissile(Targeting targeting, Placement entityPlacement, Entity firstInRange) {
        Vector2 diffVector = mPos.get(firstInRange).cpy().sub(entityPlacement);
        float rotation = diffVector.angle();
        entityPlacement.rotation = rotation - 90;
        Entity missile = new Entity().add(getPlacement(targeting, entityPlacement, rotation));
        for (CloneableComponent component : targeting.attack) {
            missile.add(component.cloneComponent());
        }
        Velocity velocity = mVel.get(missile);
        if (velocity != null) velocity.setAngle(diffVector.angle());
        return missile;
    }

    private Entity getFirstInRange(Targeting targeting, Placement towerPlacement) {
        Entity firstInRange = null;
        float timeFirstInRange = 0;
        for (Entity creep : creeps) {
            if (isInRange(towerPlacement, mPos.get(creep), targeting.range) && mPat.get(creep).time > timeFirstInRange) {
                firstInRange = creep;
                timeFirstInRange = mPat.get(creep).time;
            }
        }
        return firstInRange;
    }

    private boolean isInRange(Placement towerPlacement, Placement creepPlacement, float range) {
        Vector2 diffVector = creepPlacement.cpy().sub(towerPlacement);
        return diffVector.len2() < range * range;
    }

    private Placement getPlacement(Targeting targeting, Placement entityPlacement, float rotation) {
        Placement projectilePlacement = new Placement(entityPlacement);
        projectilePlacement.add(targeting.projectileDisplacement.setAngle(rotation));
        return projectilePlacement;
    }

    private boolean reloading(float deltaTime, Targeting targeting) {
        targeting.timeSinceLastAttack += deltaTime;
        if (targeting.timeSinceLastAttack < targeting.attackspeed) {
            return true;
        }
        targeting.timeSinceLastAttack = 0;
        return false;
    }
}
