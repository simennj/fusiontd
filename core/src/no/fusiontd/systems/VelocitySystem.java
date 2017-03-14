package no.fusiontd.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.Bag;

import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Velocity;


public class VelocitySystem extends IteratingSystem {
    ComponentMapper<Position> mPos;
    ComponentMapper<Velocity> mVel;
    ComponentMapper<Render> mRend;
    float delta = 1/60;
    public VelocitySystem() {
        super(Aspect.all(Position.class, Velocity.class));
    }

    @Override
    protected void process(int entityID) {
        Velocity vel = mVel.get(entityID);
        Position pos = mPos.get(entityID);
        pos.vec.add(vel.vec.scl(delta)); // Må fikses til å være korrekt deltatime

    }


}
