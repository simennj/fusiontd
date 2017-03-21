package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import no.fusiontd.components.Collision;
import no.fusiontd.components.Position;
import no.fusiontd.components.Type;
import no.fusiontd.components.Attack;
import no.fusiontd.components.Attackable;

public class CollisionSystem extends IteratingSystem {

    private final ImmutableArray<Entity> creeps;
    ComponentMapper<Attack> mAttack;
    ComponentMapper<Attackable> mAttab;
    ComponentMapper<Position>  mPos;
    float dx, dy, distance;

    public CollisionSystem() {
        super(Family.all(Attack.class, Position.class, Type.class).get());
        creeps = getEngine().getEntitiesFor(Family.all(Attackable.class, Position.class, Type.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position projPos = mPos.get(entity);
        Attack projAtt = mAttack.get(entity);
        for (Entity creep : creeps) {

            dx = projPos.x - creep.getComponent(Position.class).x;
            dy = projPos.y - creep.getComponent(Position.class).y;
            distance = (float)Math.sqrt(dx*dx + dy*dy);
            if(distance < projAtt.projradius + creep.getComponent(Attackable.class).creepradius) {
                CollisionHandler(entity, creep);
            }
            
        }
    }

    void CollisionHandler(Entity proj, Entity creep) {

    }
}
