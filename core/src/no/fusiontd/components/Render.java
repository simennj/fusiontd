package no.fusiontd.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Render extends Component {

    Sprite Sprite;

    public Render(TextureRegion rect) {
        Sprite = new Sprite(rect);
    }

    public Render(TextureRegion rect, float w, float h) {
        this(rect);
        Sprite.setSize(w,h);
    }

    public void setRegion(TextureRegion rect) {
        Sprite.setRegion(rect);
    }
    public void setOrigin(float xCoord, float yCoord) {
        Sprite.setOrigin(xCoord,yCoord);
    }

    public void setSize(float w, float h) {
        Sprite.setSize(w,h);
    }

    public void setPosition(float x, float y) {
        Sprite.setPosition(x - Sprite.getOriginX(), y - Sprite.getOriginY());
    }

    public void setRotation(float degrees) {
        Sprite.setRotation(degrees);
    }

    public void setScale(float x, float y) {
        Sprite.setScale(x, y);
    }

    public void setColor(Color col) {
        Sprite.setColor(col);
    }

    public void draw(SpriteBatch batch) {
        Sprite.draw(batch);
    }

}
