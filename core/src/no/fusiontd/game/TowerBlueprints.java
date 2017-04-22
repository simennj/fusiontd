package no.fusiontd.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

import no.fusiontd.components.*;

public class TowerBlueprints extends ObjectMap<String, CloneableComponentList> {
    private TextureAtlas spriteAtlas = new TextureAtlas("sprites.atlas");

    TowerBlueprints() {
        createBlueprints();
    }

    private void createBlueprints() {

        put("t_basic", new CloneableComponentList(
                new Render(spriteAtlas.findRegions("t")),
                new Value(1),
                new Targeting(5, .5f, true,
                        new Render(spriteAtlas.findRegions("missile0")),
                        new Timer(1),
                        new Attack(.5f, 5),
                        new Durability(10),
                        new Velocity(new Vector2(10, 0))
                ),
                new Upgradeable(3,
                        new Targeting(5, .5f, true,
                                new Render(spriteAtlas.findRegions("missile0")),
                                new Timer(1),
                                new Attack(.5f, 10),
                                new Durability(20),
                                new Velocity(new Vector2(10, 0))
                        ),
                        new Upgradeable(6, new Targeting(5, .5f, true,
                                new Render(spriteAtlas.findRegions("missile0")),
                                new Timer(1),
                                new Durability(40),
                                new Attack(.5f, 20),
                                new Velocity(new Vector2(12, 0))),
                                new Upgradeable(10, new Targeting(5, .5f, true,
                                        new Render(spriteAtlas.findRegions("missile0")),
                                        new Timer(1f),
                                        new Attack(.8f, 30),
                                        new Durability(60),
                                        new Velocity(new Vector2(15, 0)))
                                )
                        )
                )
        ));
        put("t_volvox", new CloneableComponentList(
                new Render(spriteAtlas.findRegions("t_volvox")),
                new Value(3),
                new Targeting(3f, 2f, true,
                        new Render(spriteAtlas.findRegions("missile0")),
                        new Timer(1),
                        new Attack(.5f, 1),
                        new Durability(1),
                        new Velocity(new Vector2(10, 0)),
                        new AddOnRemove(new Vector2(0, .5f),
                                new Render(spriteAtlas.findRegions("explosion")),
                                new Timer(.5f),
                                new Attack(.5f, 10),
                                new Durability(10000000)
                        )),
                new Upgradeable(6, new Targeting(3.5f, 1f, true,
                        new Render(spriteAtlas.findRegions("missile0")),
                        new Timer(1),
                        new Attack(.5f, 1),
                        new Durability(1),
                        new Velocity(new Vector2(10, 0)),
                        new AddOnRemove(new Vector2(0, .5f),
                                new Render(spriteAtlas.findRegions("explosion")),
                                new Timer(.5f),
                                new Attack(.6f, 2),
                                new Durability(1000000)),
                        new Upgradeable(15, new Targeting(4f, 1.6f, true,
                                new Render(spriteAtlas.findRegions("missile0")),
                                new Timer(1),
                                new Attack(.7f, 5),
                                new Velocity(new Vector2(12, 0)),
                                new AddOnRemove(new Vector2(0, .5f),
                                        new Render(spriteAtlas.findRegions("explosion")),
                                        new Timer(2),
                                        new Attack(.7f, 3),
                                        new Durability(1000000))),
                                new Upgradeable(25, new Targeting(4.5f, 1.4f, true,
                                        new Timer(1f),
                                        new Attack(.8f, 10),
                                        new Velocity(new Vector2(15, 0)),
                                        new AddOnRemove(new Vector2(0, .5f),
                                                new Attack(.8f, 5),
                                                new Timer(2),
                                                new Render(spriteAtlas.findRegions("explosion")),
                                                new Durability(10000000))))))
                )));
        put("t_emil", new CloneableComponentList(
                new Render(spriteAtlas.findRegions("t_emil")),
                new Value(5),
                new Targeting(1, .05f, new Vector2(0, .5f), true,
                        new Render(spriteAtlas.findRegions("flame")),
                        new Timer(.05f),
                        new Attack(.1f, 5),
                        new Durability(10)
                ),
                new Upgradeable(10, new Targeting(1.5f, .05f, new Vector2(0, .5f), true,
                        new Render(spriteAtlas.findRegions("flame")),
                        new Timer(.05f),
                        new Attack(.05f, 15),
                        new Durability(30)),
                        new Upgradeable(15, new Targeting(1, .05f, new Vector2(0, .5f), true,
                                new Render(spriteAtlas.findRegions("flame")),
                                new Timer(.05f),
                                new Attack(.05f, 30),
                                new Durability(30)
                        ),
                                new Upgradeable(20, new Targeting(1.5f, .05f, new Vector2(0, .5f), true,
                                        new Render(spriteAtlas.findRegions("flame")),
                                        new Timer(.05f),
                                        new Attack(.05f, 45),
                                        new Durability(90)))))
        ));
        put("t_hybrida", new CloneableComponentList(
                new Render(spriteAtlas.findRegions("t_hybrida")),
                new Value(3),
                new Targeting(5, 1.5f, false,
                        new Render(spriteAtlas.findRegions("missile0")),
                        new Timer(1),
                        new Attack(.1f, 150),
                        new Durability(1),
                        new Velocity(new Vector2(15, 0))),
                new Upgradeable(10, new Targeting(10, 1.25f, false,
                        new Render(spriteAtlas.findRegions("missile0")),
                        new Attack(.1f, 300),
                        new Timer(1),
                        new Durability(1),
                        new Velocity(new Vector2(15, 0))),
                        new Upgradeable(20, new Targeting(10, 1f, false,
                                new Render(spriteAtlas.findRegions("missile0")),
                                new Attack(.1f, 500),
                                new Timer(1),
                                new Durability(1),
                                new Velocity(new Vector2(15, 0))),
                                new Upgradeable(25, new Targeting(10, 1f, false,
                                        new Render(spriteAtlas.findRegions("missile0")),
                                        new Attack(.1f, 500),
                                        new Timer(1),
                                        new Durability(85),
                                        new Velocity(new Vector2(15, 0))))))

        ));

    }

}
