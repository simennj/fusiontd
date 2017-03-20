package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import no.fusiontd.components.Collision;
import no.fusiontd.components.Position;
import no.fusiontd.components.Type;

public class CollisionSystem extends IteratingSystem {

    ComponentMapper<Collision> mCol;
    ComponentMapper<Position>  mPos;


    public CollisionSystem() {
        super(Family.all(Collision.class, Position.class, Type.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
