package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import no.fusiontd.components.*;

public class TargetingSystem extends IteratingSystem {
    float distance, dx, dy;
    ComponentMapper<Position> mPos;
    private ImmutableArray<Entity> creeps;

    public TargetingSystem() {
        super(Family.all(Position.class, Type.class, Targeting.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        creeps = engine.getEntitiesFor(Family.all(Attackable.class, Position.class, Type.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        for (Entity creep : creeps) {
            dx = entity.getComponent(Position.class).x - mPos.get(creep).x;
            dy = entity.getComponent(Position.class).x - mPos.get(creep).y;
            distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance > entity.getComponent(Targeting.class).range) {
                getEngine().addEntity(new Entity().add(new Position()).add(new Attack()).add(new Type("Projectile")));
            }
        }
    }

}
