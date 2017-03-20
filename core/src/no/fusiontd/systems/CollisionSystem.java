package no.fusiontd.systems;


import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;

import java.util.ArrayList;

import no.fusiontd.components.Collision;
import no.fusiontd.components.Position;
import no.fusiontd.components.Type;

public class CollisionSystem extends EntitySystem {

    ComponentMapper<Collision> mCol;
    ComponentMapper<Position>  mPos;
    ComponentMapper<Type> mType;
    ArrayList<Entity> projList = new ArrayList<Entity>();
    ArrayList<Entity> creepList = new ArrayList<Entity>();
    float dx, dy, distance;



    public CollisionSystem(Aspect.Builder aspect) {
        super(Aspect.all(Collision.class, Position.class, Type.class));
    }


    protected void process(Entity e) {
        Type typ = mType.get(e);
        Collision col = mCol.get(e);
        Position pos = mPos.get(e);

        for (int i = 0; i < creepList.size() ; i++) {
            dx =  pos.vec.x - creepList.get(i).getComponent(Position.class).vec.x;
            dy = pos.vec.y - creepList.get(i).getComponent(Position.class).vec.y;
            distance = (float)Math.sqrt(dx*dx + dy*dy);
            if(distance < col.radius + creepList.get(i).getComponent(Collision.class).radius) {
                System.out.println("Creep er truffet");
                break;
            }

        }




    }

    @Override
    protected void processSystem() {
        Bag<Entity> entities = getEntities();
        Type typ;
        for (Entity e: entities){
            typ = mType.get(e);
            if (typ.type ==  "Projectiles") {
                projList.add(e);
            } else if (typ.type == "Creep") {
                creepList.add(e);
            }
        }
        for (Entity e: projList) {
         process(e);
        }

    }
}
