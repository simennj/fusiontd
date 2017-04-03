package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;

public class TargetingSystem extends IteratingSystem {
    ComponentMapper<Position> mPos = ComponentMapper.getFor(Position.class);
    ComponentMapper<Rotation> mRot = ComponentMapper.getFor(Rotation.class);
    ComponentMapper<Targeting> mTar = ComponentMapper.getFor(Targeting.class);
    ComponentMapper<PathFollow> mPat = ComponentMapper.getFor(PathFollow.class);
    private ImmutableArray<Entity> creeps;

    public TargetingSystem() {
        super(Family.all(Position.class, Targeting.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        creeps = engine.getEntitiesFor(Family.all(Attackable.class, Position.class, PathFollow.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Targeting targeting = mTar.get(entity);
        Rotation entityRotation = mRot.get(entity);
        Position entityPosition = mPos.get(entity);
        targeting.timeSinceLastAttack += deltaTime;
        if (targeting.timeSinceLastAttack < targeting.attackspeed) {
            return;
        }
        float range2 = targeting.range * targeting.range;
        Vector2 diffFirstInRange = new Vector2();
        float timeFirstInRange = 0;
        for (Entity creep : creeps) {
            Position creepPosition = mPos.get(creep);
            Vector2 diffVector = new Vector2(
                    creepPosition.x - entityPosition.x,
                    creepPosition.y - entityPosition.y
            );
            if (diffVector.len2() < range2 && mPat.get(creep).time > timeFirstInRange) {
                diffFirstInRange = diffVector;
                timeFirstInRange = mPat.get(creep).time;
            }
        }
        if (timeFirstInRange != 0) {
            targeting.timeSinceLastAttack = 0;
            float rotation = diffFirstInRange.angle();
            mRot.get(entity).rotation = rotation - 90;
            getEngine().addEntity(
                    new Entity()
                            .add(new Position(entityPosition.x, entityPosition.y))
                            .add(new Rotation(rotation - 90))
                            .add(new Render(Graphics.getRegion("missile")))
                            .add(new Velocity(diffFirstInRange.nor().scl(8)))
                            .add(new Timer(1))
            );
        }
    }
}
