package no.fusiontd.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Render extends Component {

    Sprite sprite;

    public Render() {
        this(new TextureRegion(new Texture("tiles/128.png")));
    }

    public Render(TextureRegion rect) {
        this(rect, 1, 1);
    }

    public Render(TextureRegion rect, float w, float h) {
        sprite = new Sprite(rect);
        setScale(1, 1);
    }

    public void setRegion(TextureRegion rect) {
        sprite.setRegion(rect);
    }

    public void setOrigin(float xCoord, float yCoord) {
        sprite.setOrigin(xCoord, yCoord);
    }

    public void setSize(float w, float h) {
        sprite.setSize(w, h);
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x - sprite.getOriginX(), y - sprite.getOriginY());
    }

    public void setRotation(float degrees) {
        sprite.setRotation(degrees);
    }

    public void setScale(float x, float y) {
        sprite.setScale(x / 128, y / 128);
    }

    public void setColor(Color col) {
        sprite.setColor(col);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
