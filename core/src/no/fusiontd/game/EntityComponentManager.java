package no.fusiontd.game;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

import no.fusiontd.CloneableComponent;
import no.fusiontd.Graphics;
import no.fusiontd.components.*;
import no.fusiontd.screens.PlayScreen;
import no.fusiontd.systems.*;

import java.util.Arrays;
import java.util.Collection;

public class EntityComponentManager extends Engine {

    private final ObjectMap<String, Collection<CloneableComponent>> blueprints = new ObjectMap<String, Collection<CloneableComponent>>();
    private ImmutableArray<Entity> towers;
    private ComponentMapper<Geometry> mPos = ComponentMapper.getFor(Geometry.class);
    private ImmutableArray<Entity> creeps;
    private Player localPlayer, mulPlayer;

    public EntityComponentManager(PlayScreen view, final Player localPlayer, Player mulPlayer) {
        super();
        this.localPlayer = localPlayer;
        this.mulPlayer = mulPlayer;

        addSystem(new VelocitySystem());
        addSystem(new RenderSystem(view.batch));
        addSystem(new PathSystem());
        addSystem(new TargetingSystem());
        addSystem(new TimerSystem());
        addSystem(new CollisionSystem());
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
                    localPlayer.addCash(1);
                } else if (pathFollowMapper.get(entity).time > 1) {
                    localPlayer.loseLives(1);
                }
            }
        });
        towers = getEntitiesFor(Family.all(Geometry.class, Render.class, Targeting.class).get());
        creeps = getEntitiesFor(Family.all(Geometry.class, Attackable.class, Durability.class).get());

        blueprints.put("missileTower", Arrays.<CloneableComponent>asList(
                new Render("missileTower"),
                new Buyable(1),
                new Targeting(5, .5f, true,
                        new Render(Graphics.getRegion("missile")),
                        new Timer(1),
                        new Attack(.5f, 12),
                        new Durability(12),
                        new Velocity(new Vector2(10, 0)),
                        new AddOnRemove(new Vector2(0, .5f),
                                new Render("explosion"),
                                new Timer(2)
                        )
                ),
                new Upgradeable(10,
                        new Targeting(5, .5f, true,
                                new Render(Graphics.getRegion("missile")),
                                new Timer(1),
                                new Attack(.6f, 2),
                                new Durability(12),
                                new Velocity(new Vector2(11, 0)),
                                new AddOnRemove(new Vector2(0, .5f),
                                        new Render("explosion"),
                                        new Timer(2)
                                )
                        ),
                        new Upgradeable(15, new Targeting(5, .5f, true,
                                new Render(Graphics.getRegion("missile")),
                                new Timer(1),
                                new Durability(12),
                                new Attack(.7f, 5),
                                new Velocity(new Vector2(12, 0))),
                                new Upgradeable(20,
                                        new Timer(1f),
                                        new Attack(.8f, 10),
                                        new Velocity(new Vector2(15, 0))
                                )
                        )
                )

        ));

        blueprints.put("cannonTower", Arrays.<CloneableComponent>asList(
                new Render("t_hybrida0"),
                new Buyable(2),
                new Targeting(3f, 2f, true,
                        new Render(Graphics.getRegion("missile0")),
                        new Timer(1),
                        new Attack(.5f, 1),
                        new Durability(1),
                        new Velocity(new Vector2(10, 0)),
                        new AddOnRemove(new Vector2(0, .5f),
                                new Render("explosion"),
                                new Timer(2),
                                new Attack(.5f, 1),
                                new Durability(10000000)
                        )),
                new Upgradeable(10, new Targeting(3.5f, 1f, true,
                        new Render(Graphics.getRegion("flame")),
                        new Timer(1),
                        new Attack(.5f, 1),
                        new Durability(1),
                        new Velocity(new Vector2(10, 0)),
                        new AddOnRemove(new Vector2(0, .5f),
                                new Render("explosion"),
                                new Timer(2),
                                new Attack(.6f, 2),
                                new Durability(1000000)),
                        new Upgradeable(15, new Targeting(4f, 1.6f, true,
                                new Render(Graphics.getRegion("missile")),
                                new Timer(1),
                                new Attack(.7f, 5),
                                new Velocity(new Vector2(12, 0)),
                                new AddOnRemove(new Vector2(0, .5f),
                                        new Render("explosion"),
                                        new Timer(2),
                                        new Attack(.7f, 3),
                                        new Durability(1000000))),
                                new Upgradeable(20, new Targeting(4.5f, 1.4f, true,
                                        new Timer(1f),
                                        new Attack(.8f, 10),
                                        new Velocity(new Vector2(15, 0)),
                                        new AddOnRemove(new Vector2(0, .5f),
                                                new Attack(.8f, 5),
                                                new Timer(2),
                                                new Render("explosion"),
                                                new Durability(10000000))))))

                )));


        blueprints.put("flameTower", Arrays.<CloneableComponent>asList(
                new Render("t_emil0"),
                new Buyable(5),
                new Targeting(1, .05f, new Vector2(0, .5f), true,
                        new Render(Graphics.getRegion("flame")),
                        new Timer(1),
                        new Attack(.05f, 30),
                        new Durability(30)
                ),
                new Upgradeable(10, new Targeting(1.5f, .05f, new Vector2(0, .5f), true,
                        new Render(Graphics.getRegion("flame")),
                        new Attack(.05f, 40),
                        new Durability(40)),
                        new Upgradeable(15, new Targeting(1, .05f, new Vector2(0, .5f), true,
                                new Attack(.05f, 60),
                                new Timer(1),
                                new Render(Graphics.getRegion("flame")),
                                new Durability(60))))
        ));
        blueprints.put("sniperTower", Arrays.<CloneableComponent>asList(
                new Render("t_volvox0"),
                new Buyable(3),
                new Targeting(5, 1.5f, false,
                        new Render(Graphics.getRegion("LF")),
                        new Timer(1),
                        new Attack(.1f, 2000),
                        new Durability(1),
                        new Velocity(new Vector2(10, 0))),
                new Upgradeable(10, new Targeting(10, 1f, false,
                        new Render(Graphics.getRegion("LF")),
                        new Attack(.1f, 2500),
                        new Timer(1),
                        new Durability(1),
                        new Velocity(new Vector2(10, 0)))
                )
        ));


    }

    public void upgradeEntity(Entity e) {
        ComponentMapper<Upgradeable> mUpgr = ComponentMapper.getFor(Upgradeable.class);
        Upgradeable upgrade = mUpgr.get(e);
        for (CloneableComponent component : upgrade.upgrades) {
            e.add(component.cloneComponent());
        }
    }


    public Entity spawn(CloneableComponent... components) {
        return spawn(Arrays.asList(components));
    }

    public Entity spawn(Iterable<CloneableComponent> components) {
        Entity entity = new Entity();
        for (CloneableComponent component : components) {
            entity.add(component.cloneComponent());
        }
        addEntity(entity);
        return entity;
    }

    public void spawnTower(String name, Geometry geometry) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(geometry) < geometry.radius) return;
        }
        Entity tower = spawn(blueprints.get(name));
        tower.add(geometry);
    }

    public boolean checkTower(Geometry geometry) {
        for (Entity tower : towers) {
            if (mPos.get(tower).dst(geometry) < geometry.radius) return true;
        }
        return false;
    }

    public Entity getTowerAt(float x, float y) {
        towers = getEntitiesFor(Family.all(Geometry.class, Render.class, Targeting.class).get());

        for (Entity e : towers) {
            if (mPos.get(e).dst(e.getComponent(Geometry.class)) < e.getComponent(Geometry.class).radius) {
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

    public int getCost(String tower){
        return spawn(blueprints.get(tower)).getComponent(Buyable.class).cost;
    }

}
