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
        regions.put("sniperTower", tileAtlas.findRegion("301"));
        regions.put("LF", tileAtlas.findRegion("300"));
        regions.put("explosion", tileAtlas.findRegion("021"));
        regions.put("zeros", tileAtlas.findRegion("276"));
        regions.put("one", tileAtlas.findRegion("277"));
        regions.put("two", tileAtlas.findRegion("278"));
        regions.put("three", tileAtlas.findRegion("279"));
        regions.put("four", tileAtlas.findRegion("280"));
        regions.put("five", tileAtlas.findRegion("281"));
        regions.put("six", tileAtlas.findRegion("282"));
        regions.put("seven", tileAtlas.findRegion("283"));
        regions.put("eight", tileAtlas.findRegion("284"));
        regions.put("nine", tileAtlas.findRegion("285"));
    }

    public static TextureAtlas.AtlasRegion getRegion(String name) {
        return regions.get(name);
    }

}
