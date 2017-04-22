package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import no.fusiontd.CloneableComponent;
import no.fusiontd.components.*;
import no.fusiontd.screens.PlayScreen;
import no.fusiontd.systems.*;

public class EntityComponentManager extends Engine {

    private ImmutableArray<Entity> towers = getEntitiesFor(Family.all(Geometry.class, Targeting.class).get());
    private ImmutableArray<Entity> creeps = getEntitiesFor(Family.all(Geometry.class, Attackable.class, Durability.class).get());
    private ComponentMapper<Geometry> mPos = ComponentMapper.getFor(Geometry.class);
    private TowerBlueprints towerBlueprints = new TowerBlueprints();
    private Player localPlayer, mulPlayer;

    public EntityComponentManager(PlayScreen view, final Player localPlayer, Player mulPlayer) {
        super();
        this.localPlayer = localPlayer;
        this.mulPlayer = mulPlayer;
        addSystems(view);
        addListeners(localPlayer);
    }

    private void addListeners(final Player localPlayer) {
        addEntityListener(Family.all(AddOnRemove.class, Geometry.class).get(), new EntityListener() {
            ComponentMapper<AddOnRemove> removeActionMapper = ComponentMapper.getFor(AddOnRemove.class);
            ComponentMapper<Geometry> positionMapper = ComponentMapper.getFor(Geometry.class);

            @Override
            public void entityAdded(Entity entity) {

            }

            @Override
            public void entityRemoved(Entity entity) {
                AddOnRemove addOnRemove = removeActionMapper.get(entity);
                Entity newEntity = spawn(addOnRemove.newEntity);
                Geometry geometry = positionMapper.get(newEntity);
                Geometry entityGeometry = positionMapper.get(entity);
                if (geometry == null) {
                    newEntity.add(entityGeometry.cloneComponent());
                    geometry = positionMapper.get(newEntity);
                }
                geometry.add(addOnRemove.displacement.cpy().rotate(entityGeometry.rotation));
            }
        });
        addEntityListener(Family.all(Durability.class, PathFollow.class).get(), new EntityListener() {
            ComponentMapper<PathFollow> pathFollowMapper = ComponentMapper.getFor(PathFollow.class);
            ComponentMapper<Durability> durabilityMapper = ComponentMapper.getFor(Durability.class);

            @Override
            public void entityAdded(Entity entity) {

            }

            @Override
            public void entityRemoved(Entity entity) {
                if (durabilityMapper.get(entity).life <= 0) {
                    localPlayer.addCash(entity.getComponent(Value.class).cost);
                } else if (pathFollowMapper.get(entity).time > 1) {
                    localPlayer.loseLives(1);
                }
            }
        });
    }

    private void addSystems(PlayScreen view) {
        addSystem(new VelocitySystem());
        addSystem(new RenderSystem(view.batch));
        addSystem(new PathSystem());
        addSystem(new TargetingSystem());
        addSystem(new TimerSystem());
        addSystem(new CollisionSystem());

    }

    public boolean upgradeEntity(Entity e) {
        ComponentMapper<Upgradeable> mUpgr = ComponentMapper.getFor(Upgradeable.class);
        Upgradeable upgrade = mUpgr.get(e);
        if(upgrade == null) {
            return false;
        }
        e.remove(Upgradeable.class);
        for (CloneableComponent component : upgrade.upgrades) {
            e.add(component.cloneComponent());
        }
        return true;
    }


    private Entity spawn(CloneableComponent... components) {
        return spawn(new CloneableComponentList(components));
    }

    private Entity spawn(CloneableComponentList components) {
        Entity entity = components.toEntity();
        addEntity(entity);
        return entity;
    }

    public void spawnTower(String name, Geometry geometry) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(geometry) < geometry.radius) return;
        }
        Entity tower = spawn(towerBlueprints.get(name));
        tower.add(geometry);
    }

    public boolean checkTower(Geometry geometry) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(geometry) < geometry.radius) return true;
        }
        return false;
    }

    public Entity getTowerAt(float x, float y) {
        Vector2 vec = new Vector2(x, y);
        for (Entity e : towers) {
            if (mPos.get(e).dst(vec) < e.getComponent(Geometry.class).radius) {
                System.out.println(e.getComponent(Targeting.class).attackspeed);
                return e;
            }
        }
        return null;
    }

    public boolean checkCreep(Geometry geometry) {
        for (Entity creep : creeps) {
            if (mPos.get(creep).dst(geometry) < geometry.radius) return true;
        }
        return false;
    }

    int getCost(String tower) {
        return towerBlueprints.get(tower).toEntity().getComponent(Value.class).cost;
    }

}
