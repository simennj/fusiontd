package no.fusiontd.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Rotation;

public class RenderSystem extends EntitySystem {
    private final SpriteBatch batch;

    ComponentMapper<Position> mPos;
    ComponentMapper<Render> mRend;
    ComponentMapper<Rotation> mRot;

    public RenderSystem(SpriteBatch batch) {
        super(Aspect.all(Position.class, Rotation.class, Render.class));
        this.batch = batch;
    }

    @Override
    protected void processSystem() {
        Bag<Entity> entities = getEntities();


        for(Entity e: entities) {
            process(e);
            
        }
    }

    protected void process (Entity e) {
        Position pos = mPos.get(e);
        Render rend = mRend.get(e);
        Rotation rot = mRot.get(e);
        rend.setPosition(pos.vec.x, pos.vec.y);
        rend.setRotation(rot.rotation);
        rend.draw(batch);
    }


}
