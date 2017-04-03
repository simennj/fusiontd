package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import no.fusiontd.components.Attack;
import no.fusiontd.components.Attackable;
import no.fusiontd.components.Position;
import no.fusiontd.components.Targeting;
import no.fusiontd.components.Type;

public class TargetingSystem extends IteratingSystem {
    private final ImmutableArray<Entity> creeps;
    float distance, dx, dy;
    ComponentMapper<Position> mPos;

    public TargetingSystem() {
        super(Family.all(Position.class, Type.class, Targeting.class).get());
        creeps = getEngine().getEntitiesFor(Family.all(Attackable.class, Position.class, Type.class).get());

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        for (Entity creep : creeps) {
            dx = entity.getComponent(Position.class).x - mPos.get(creep).x;
            dy = entity.getComponent(Position.class).x - mPos.get(creep).y;
            distance = (float)Math.sqrt(dx*dx + dy*dy);
            if (distance > entity.getComponent(Targeting.class).range) {
                getEngine().addEntity(new Entity().add(new Position()).add(new Attack()).add(new Type("Projectile")));
            }
        }
    }

}
