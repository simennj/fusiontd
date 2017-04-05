package no.fusiontd.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;
import no.fusiontd.components.*;

public class TargetingSystem extends IteratingSystem {
    private ComponentMapper<Placement> mPos = ComponentMapper.getFor(Placement.class);
    private ComponentMapper<Targeting> mTar = ComponentMapper.getFor(Targeting.class);
    private ComponentMapper<PathFollow> mPat = ComponentMapper.getFor(PathFollow.class);
    private ImmutableArray<Entity> creeps;

    public TargetingSystem() {
        super(Family.all(Placement.class, Targeting.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        creeps = engine.getEntitiesFor(Family.all(Attackable.class, Placement.class, PathFollow.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Targeting targeting = mTar.get(entity);
        Placement entityPlacement = mPos.get(entity);
        targeting.timeSinceLastAttack += deltaTime;
        if (targeting.timeSinceLastAttack < targeting.attackspeed) {
            return;
        }
        float range2 = targeting.range * targeting.range;
        Vector2 diffFirstInRange = new Vector2();
        Entity firstCreep = null;
        float timeFirstInRange = 0;
        for (Entity creep : creeps) {
            Placement creepPlacement = mPos.get(creep);
            Vector2 diffVector = new Vector2(
                    creepPlacement.x - entityPlacement.x,
                    creepPlacement.y - entityPlacement.y
            );
            if (diffVector.len2() < range2 && mPat.get(creep).time > timeFirstInRange) {
                diffFirstInRange = diffVector;
                timeFirstInRange = mPat.get(creep).time;
                firstCreep = creep;

            }
        }
        if (firstCreep != null) {

            targeting.timeSinceLastAttack = 0;
            PathFollow path = mPat.get(firstCreep);
            Vector2 position = mPos.get(firstCreep);
            float time = 10 / diffFirstInRange.len();
            System.out.println(time);
            path.path.valueAt(position, mPat.get(firstCreep).time + time / 160);
            Vector2 firstCreepdiffVector = new Vector2(
                    position.x - entityPlacement.x,
                    position.y - entityPlacement.y
            );
            float rotation = firstCreepdiffVector.angle();
            entityPlacement.rotation = rotation - 90;
            Entity missile = new Entity()
                    .add(new Placement(entityPlacement))
                    .add(new Velocity(firstCreepdiffVector.nor().scl(10)));
            for (CloneableComponent component : targeting.attack) {
                missile.add(component.cloneComponent());
            }
            getEngine().addEntity(missile);
        }
    }
}
