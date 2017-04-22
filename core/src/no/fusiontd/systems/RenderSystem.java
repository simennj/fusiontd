package no.fusiontd.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.fusiontd.components.Geometry;
import no.fusiontd.components.Render;

public class RenderSystem extends IteratingSystem {
    private final SpriteBatch batch;

    private ComponentMapper<Geometry> mPos = ComponentMapper.getFor(Geometry.class);
    private ComponentMapper<Render> mRend = ComponentMapper.getFor(Render.class);

    public RenderSystem(SpriteBatch batch) {
        super(Family.all(Geometry.class, Render.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Geometry pos = mPos.get(entity);
        Render rend = mRend.get(entity);
        rend.animate(deltaTime);
        rend.setPosition(pos.x, pos.y);
        rend.setRotation(pos.rotation);
        rend.draw(batch);
    }
}
