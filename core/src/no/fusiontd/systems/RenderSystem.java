package no.fusiontd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.fusiontd.components.Position;
import no.fusiontd.components.Render;
import no.fusiontd.components.Rotation;

public class RenderSystem extends IteratingSystem {
    private final SpriteBatch batch;

    ComponentMapper<Position> mPos = ComponentMapper.getFor(Position.class);
    ComponentMapper<Render> mRend = ComponentMapper.getFor(Render.class);
    ComponentMapper<Rotation> mRot = ComponentMapper.getFor(Rotation.class);

    public RenderSystem(SpriteBatch batch) {
        super(Family.all(Position.class, Rotation.class, Render.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Position pos = mPos.get(entity);
        Render rend = mRend.get(entity);
        Rotation rot = mRot.get(entity);
        rend.setPosition(pos.x, pos.y);
        rend.setRotation(rot.rotation);
        rend.draw(batch);
    }
}
