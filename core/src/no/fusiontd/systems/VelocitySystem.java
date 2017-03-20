package no.fusiontd.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.Bag;

import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Velocity;


public class VelocitySystem extends EntityProcessingSystem {
    ComponentMapper<Position> mPos;
    ComponentMapper<Velocity> mVel;
    public VelocitySystem() {
        super(Aspect.all(Position.class, Velocity.class));
    }

    @Override
    protected void process(Entity e) {

        Velocity vel = mVel.get(e);
        Position pos = mPos.get(e);
        pos.vec.add(vel.vec.scl(world.getDelta()));

    }


}
