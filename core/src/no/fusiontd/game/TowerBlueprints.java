package no.fusiontd.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import no.fusiontd.components.*;

public class TowerBlueprints extends ObjectMap<String, TowerBlueprint> {
    private TextureAtlas spriteAtlas = new TextureAtlas("sprites.atlas");

    TowerBlueprints() {
        createBlueprints();
    }

    private void createBlueprints() {

        put("t_basic", new TowerBlueprint(spriteAtlas.findRegions("t"), 10,
                new Targeting(3, 1f, true,
                        new Render(spriteAtlas.findRegions("missile0")),
                        new Timer(1),
                        new Attack(.5f, 3),
                        new Durability(3),
                        new Velocity(new Vector2(10, 0))
                )
        ).addUppgrade(15, 4, .9f,
                new Attack(.5f, 10),
                new Durability(10)
        ).addUppgrade(20, 5, .8f,
                new Attack(.5f, 15),
                new Durability(15)
        ).addUppgrade(25, 5, .6f,
                new Attack(.8f, 25),
                new Durability(25)
        ));

        put("t_volvox", new TowerBlueprint(
                        spriteAtlas.findRegions("t_volvox"), 15,
                        new Targeting(3f, 2f, true,
                                new Render(spriteAtlas.findRegions("missile0")),
                                new Timer(1),
                                new Attack(.5f, 1),
                                new Durability(1),
                                new Velocity(new Vector2(10, 0)),
                                new AddOnRemove(new Vector2(0, .5f),
                                        new Render(spriteAtlas.findRegions("acid")),
                                        new Timer(.4f),
                                        new Attack(.5f, 5),
                                        new Durability(10000000)
                                )
                        )
                ).addUppgrade(20, 3.5f, 1.8f,
                new Attack(.5f, 1),
                new Velocity(new Vector2(10, 0)),
                new AddOnRemove(
                        new Vector2(0, .5f),
                        new Render(spriteAtlas.findRegions("acid")),
                        new Timer(.5f),
                        new Attack(.6f, 25),
                        new Durability(1000000)
                )
                ).addUppgrade(25, 4f, 1.6f,
                new Attack(.7f, 1),
                new Velocity(new Vector2(12, 0)),
                new AddOnRemove(
                        new Vector2(0, .5f),
                        new Render(spriteAtlas.findRegions("acid")),
                        new Timer(.7f),
                        new Attack(.7f, 30),
                        new Durability(1000000)
                )
                ).addUppgrade(30, 4.5f, 1.4f,
                new Attack(.8f, 1),
                new Velocity(new Vector2(15, 0)),
                new AddOnRemove(
                        new Vector2(0, .5f),
                        new Attack(.8f, 40),
                        new Timer(1f),
                        new Render(spriteAtlas.findRegions("acid")),
                        new Durability(10000000)
                )
                )
        );

        put("t_emil", new TowerBlueprint(
                spriteAtlas.findRegions("t_emil"), 15,
                new Targeting(1, .1f, new Vector2(0, .5f), true,
                        new Render(spriteAtlas.findRegions("flame")),
                        new Timer(.05f),
                        new Attack(.1f, 12),
                        new Durability(12)
                )
        ).addUppgrade(20, 1.5f, .8f,
                new Attack(.05f, 15),
                new Durability(30)
        ).addUppgrade(25, 1, .05f,
                new Attack(.05f, 30),
                new Durability(30)
        ).addUppgrade(30, 1.5f, .05f,
                new Attack(.05f, 45),
                new Durability(90)
        ));


        put("t_hybrida", new TowerBlueprint(spriteAtlas.findRegions("t_hybrida"), 15,
                new Targeting(8, 1.5f, false,
                        new Render(spriteAtlas.findRegions("missile0")),
                        new Timer(1),
                        new Attack(.4f, 150),
                        new Durability(1),
                        new Velocity(new Vector2(15, 0))
                )
        ).addUppgrade(20, 10, 1.25f,
                new Attack(.4f, 300)
        ).addUppgrade(25, 12, 1f,
                new Attack(.4f, 500)
        ).addUppgrade(35, 15, 1f,
                new Attack(.4f, 500)
        ));


    }

}
