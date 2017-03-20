package no.fusiontd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import no.fusiontd.components.Position;
import no.fusiontd.components.Velocity;


public class VelocitySystem extends IteratingSystem {
    ComponentMapper<Position> mPos;
    ComponentMapper<Velocity> mVel;

    public VelocitySystem() {
        super(Family.all(Position.class, Velocity.class).get());
    }

    @Override
    protected void processEntity(Entity e, float deltatime) {
        Velocity vel = mVel.get(e);
        Position pos = mPos.get(e);
        pos.add(vel.scl(deltatime));
    }

}
