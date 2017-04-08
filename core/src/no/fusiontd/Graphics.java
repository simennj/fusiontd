package no.fusiontd;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;

public class Graphics {
    private static final ObjectMap<String, TextureAtlas.AtlasRegion> regions;
    private static TextureAtlas tileAtlas = new TextureAtlas("tiles.atlas");

    static {
        regions = new ObjectMap<String, TextureAtlas.AtlasRegion>();
        regions.put("groundTex", tileAtlas.findRegion("024"));
        regions.put("roadTex", tileAtlas.findRegion("050"));
        regions.put("bush", tileAtlas.findRegion("131"));
        regions.put("pathStartTex", tileAtlas.findRegion("091"));
        regions.put("pathEndTex", tileAtlas.findRegion("090"));
        regions.put("plane", tileAtlas.findRegion("271"));
        regions.put("missile", tileAtlas.findRegion("252"));
        regions.put("missileTower", tileAtlas.findRegion("206"));
        regions.put("flameTower", tileAtlas.findRegion("226"));
        regions.put("flame", tileAtlas.findRegion("298"));
        regions.put("explosion", tileAtlas.findRegion("021"));
    }

    public static TextureAtlas.AtlasRegion getRegion(String name) {
        return regions.get(name);
    }

}
