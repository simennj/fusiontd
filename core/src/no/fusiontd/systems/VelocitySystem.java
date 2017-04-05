package no.fusiontd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import no.fusiontd.components.Placement;
import no.fusiontd.components.Velocity;


public class VelocitySystem extends IteratingSystem {
    private ComponentMapper<Placement> mPos = ComponentMapper.getFor(Placement.class);
    private ComponentMapper<Velocity> mVel = ComponentMapper.getFor(Velocity.class);

    public VelocitySystem() {
        super(Family.all(Placement.class, Velocity.class).get());
    }

    @Override
    protected void processEntity(Entity e, float deltatime) {
        Velocity vel = mVel.get(e);
        Placement pos = mPos.get(e);
        pos.mulAdd(vel, deltatime);
    }

}
