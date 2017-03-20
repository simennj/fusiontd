package no.fusiontd.systems;


import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import no.fusiontd.components.Collision;
import no.fusiontd.components.Position;
import no.fusiontd.components.Type;

public class CollisionSystem extends EntitySystem {

    ComponentMapper<Collision> mCol;
    ComponentMapper<Position>  mPos;


    public CollisionSystem(Aspect.Builder aspect) {
        super(Aspect.all(Collision.class, Position.class, Type.class));
    }


    protected void process(Entity e) {
        Bag<Entity> entities = getEntities();






    }

    @Override
    protected void processSystem() {

    }
}
