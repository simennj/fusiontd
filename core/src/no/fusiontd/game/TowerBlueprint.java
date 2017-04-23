package no.fusiontd.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import no.fusiontd.CloneableComponent;
import no.fusiontd.components.Render;
import no.fusiontd.components.Targeting;
import no.fusiontd.components.Upgradeable;
import no.fusiontd.components.Value;

class TowerBlueprint extends CloneableComponentList {
    Upgradeable lastUpgrade;
    int cost;
    TextureAtlas.AtlasRegion icon;
    Targeting targeting;

    TowerBlueprint(Array<TextureAtlas.AtlasRegion> textures, int cost, Targeting targeting) {
        this.cost = cost;
        this.icon = textures.first();
        this.targeting = targeting;
        put(Render.class, new Render(textures));
        put(Value.class, new Value(cost));
        put(Targeting.class, targeting);
    }

    TowerBlueprint addUppgrade(int cost, float range, float attackSpeed, CloneableComponent... targetingComponents) {
        Targeting targetingUpgrade;
        if (lastUpgrade == null) {
            targetingUpgrade = targeting.cloneComponent();
        } else {
            targetingUpgrade = (Targeting) lastUpgrade.upgrades.get(Targeting.class).cloneComponent();
        }
        targetingUpgrade.range = range;
        targetingUpgrade.attackspeed = attackSpeed;
        for (CloneableComponent targetingComponent : targetingComponents) {
            targetingUpgrade.attack.put(targetingComponent.getClass(), targetingComponent);
        }
        lastUpgrade = new Upgradeable(cost, targetingUpgrade);
        put(Upgradeable.class, lastUpgrade);
        return this;
    }

}