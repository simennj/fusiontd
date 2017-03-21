package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import no.fusiontd.components.Durability;
import no.fusiontd.components.Position;
import no.fusiontd.components.Timer;
import no.fusiontd.components.Type;
import no.fusiontd.components.Attack;
import no.fusiontd.components.Attackable;

public class CollisionSystem extends IteratingSystem {

    private final ImmutableArray<Entity> creeps;
    ComponentMapper<Attack> mAttack;
    ComponentMapper<Attackable> mAttab;
    ComponentMapper<Position>  mPos;
    ComponentMapper<Durability> mDur;
    ComponentMapper<Timer> mTime;
    float dx, dy, distance;
    int creepLife, projLife;

    public CollisionSystem() {
        super(Family.all(Attack.class, Position.class, Type.class).get());
        creeps = getEngine().getEntitiesFor(Family.all(Attackable.class, Position.class, Type.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position projPos = mPos.get(entity);
        Attack projAtt = mAttack.get(entity);
        for (Entity creep : creeps) {

            dx = projPos.x - mPos.get(creep).x;
            dy = projPos.y - mPos.get(creep).y;
            distance = (float)Math.sqrt(dx*dx + dy*dy);
            if(distance < projAtt.projradius + mAttab.get(creep).creepradius) {
                CollisionHandler(entity, creep);
            }
            
        }
    }

    void CollisionHandler(Entity proj, Entity creep) {
            System.out.println("Kollisjon");
            creepLife = mDur.get(creep).life;
            projLife = mDur.get(proj).life;

            mDur.get(creep).life -= projLife;
            mDur.get(proj).life -= creepLife;

            if (mDur.get(creep).life <= 0) {
                creep.removeAll();
            }
            if (mDur.get(proj).life <= 0) {
                proj.removeAll();
            }




    }
}
