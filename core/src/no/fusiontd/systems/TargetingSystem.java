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
    private ComponentMapper<Geometry> mPos = ComponentMapper.getFor(Geometry.class);
    private ComponentMapper<Targeting> mTar = ComponentMapper.getFor(Targeting.class);
    private ComponentMapper<PathFollow> mPat = ComponentMapper.getFor(PathFollow.class);
    private ComponentMapper<Velocity> mVel = ComponentMapper.getFor(Velocity.class);
    private ImmutableArray<Entity> creeps;

    public TargetingSystem() {
        super(Family.all(Geometry.class, Targeting.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        creeps = engine.getEntitiesFor(Family.all(Attackable.class, Geometry.class, PathFollow.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Targeting targeting = mTar.get(entity);
        Geometry entityGeometry = mPos.get(entity);
        targeting.timeSinceLastAttack += deltaTime;
        if (reloading(deltaTime, targeting)) return;
        Entity firstInRange = getFirstInRange(targeting, entityGeometry);
        if (firstInRange != null) {
            getEngine().addEntity(getMissile(targeting, entityGeometry, firstInRange));
        }
    }

    private Entity getMissile(Targeting targeting, Geometry entityGeometry, Entity firstInRange) {
        Vector2 diffVector = mPos.get(firstInRange).cpy().sub(entityGeometry);
        float rotation = diffVector.angle();

        if (targeting.aim) {
            entityGeometry.rotation = rotation - 90;
        }
        Entity missile = new Entity().add(getPlacement(targeting, entityGeometry, rotation));
        for (CloneableComponent component : targeting.attack) {
            missile.add(component.cloneComponent());
        }
        Velocity velocity = mVel.get(missile);
        if (velocity != null) velocity.setAngle(diffVector.angle());
        return missile;
    }

    private Entity getFirstInRange(Targeting targeting, Geometry towerGeometry) {
        Entity firstInRange = null;
        float timeFirstInRange = 0;
        for (Entity creep : creeps) {
            if (isInRange(towerGeometry, mPos.get(creep), targeting.range) && mPat.get(creep).time > timeFirstInRange) {
                firstInRange = creep;
                timeFirstInRange = mPat.get(creep).time;
            }
        }
        return firstInRange;
    }

    private boolean isInRange(Geometry towerGeometry, Geometry creepGeometry, float range) {
        Vector2 diffVector = creepGeometry.cpy().sub(towerGeometry);
        return diffVector.len2() < range * range;
    }

    private Geometry getPlacement(Targeting targeting, Geometry entityGeometry, float rotation) {
        Geometry projectileGeometry = new Geometry(entityGeometry);
        projectileGeometry.add(targeting.projectileDisplacement.cpy().rotate(rotation - 90));
        return projectileGeometry;
    }

    private boolean reloading(float deltaTime, Targeting targeting) {
        if (targeting.timeSinceLastAttack < targeting.attackspeed) {
            return true;
        }
        targeting.timeSinceLastAttack = 0;
        return false;
    }
}
