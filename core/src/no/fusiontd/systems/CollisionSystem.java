package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import no.fusiontd.components.*;

public class CollisionSystem extends IteratingSystem {

    private ComponentMapper<Timer> mTim = ComponentMapper.getFor(Timer.class);
    private ComponentMapper<Attack> mAttack = ComponentMapper.getFor(Attack.class);
    private ComponentMapper<Attackable> mAttab = ComponentMapper.getFor(Attackable.class);
    private ComponentMapper<Geometry> mPos = ComponentMapper.getFor(Geometry.class);
    private ComponentMapper<Durability> mDur = ComponentMapper.getFor(Durability.class);
    private ImmutableArray<Entity> creeps;

    public CollisionSystem() {
        super(Family.all(Attack.class, Geometry.class, Durability.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        creeps = getEngine().getEntitiesFor(Family.all(Attackable.class, Geometry.class, Durability.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Geometry projPos = mPos.get(entity);
        Attack projAtt = mAttack.get(entity);
        for (Entity creep : creeps) {
            float dx = projPos.x - mPos.get(creep).x;
            float dy = projPos.y - mPos.get(creep).y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if(distance < projAtt.projradius + mAttab.get(creep).creepradius) {
                CollisionHandler(entity, creep);
            }
            
        }
    }

    private void CollisionHandler(Entity proj, Entity creep) {
        float creepLife = mDur.get(creep).life;
        float projDmg = mAttack.get(proj).damage;

            mDur.get(creep).life -= projDmg;
            mDur.get(proj).life -= creepLife;

            if (mDur.get(creep).life <= 0) {
                getEngine().removeEntity(creep);
            }
            if (mDur.get(proj).life <= 0) {
                getEngine().removeEntity(proj);
            }
    }
}
