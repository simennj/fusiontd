package no.fusiontd.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

import no.fusiontd.components.Position;
import no.fusiontd.components.Velocity;


public class VelocitySystem extends IteratingSystem {

    float delta = 1/60;
    public VelocitySystem() {
        super(Aspect.all(Position.class, Velocity.class));
    }

    @Override
    protected void process(int entityId) {
        Position position = Position.mapper.get(entityId);
        position.vec.add(Velocity.mapper.get(entityId).vec.scl(delta));
    }
}
