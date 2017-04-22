package no.fusiontd;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;

public class Graphics {
    private static final ObjectMap<String, TextureAtlas.AtlasRegion> regions;
    private static TextureAtlas tileAtlas = new TextureAtlas("tiles.atlas");
    private static TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");
    private static TextureAtlas uiNewAtlas = new TextureAtlas("ui_new.atlas");
    private static TextureAtlas spriteAtlas = new TextureAtlas("sprites.atlas");

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
        regions.put("red_button", uiAtlas.findRegion("red_button"));
        regions.put("c_0", spriteAtlas.findRegion("c"));
        regions.put("c_bat0",spriteAtlas.findRegion("c_bat0"));
        regions.put("c_manyeyed0",spriteAtlas.findRegion("c_manyeyed0"));
        regions.put("missile0",spriteAtlas.findRegion("missile0"));
        regions.put("t_0", spriteAtlas.findRegion("t"));
        regions.put("t_emil0", spriteAtlas.findRegion("t_emil"));
        regions.put("t_hybrida0", spriteAtlas.findRegion("t_hybrida"));
        regions.put("t_volvox0", spriteAtlas.findRegion("t_volvox"));
        regions.put("button0", uiNewAtlas.findRegion("button0"));
        regions.put("back0", uiNewAtlas.findRegion("back0"));
        regions.put("play0", uiNewAtlas.findRegion("play0"));
        regions.put("placeholder", uiNewAtlas.findRegion("placeholder"));
    }

    public static TextureAtlas.AtlasRegion getRegion(String name) {
        return regions.get(name);
    }

}
