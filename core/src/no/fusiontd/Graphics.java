package no.fusiontd;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;
import java.util.Random;

import no.fusiontd.screens.MapEditorScreen;

import static no.fusiontd.screens.MapEditorScreen.tileType.PATH;

public class Graphics {
    private static final ObjectMap<String, TextureAtlas.AtlasRegion> regions;
    private static TextureAtlas tileAtlas = new TextureAtlas("tiles.atlas");
    private static TextureAtlas uiAtlas = new TextureAtlas("ui.atlas");
    private static TextureAtlas tileAtlasNew = new TextureAtlas("tiles_new.atlas");
    private static ArrayList<TextureAtlas.AtlasRegion> tiles;

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

        regions.put("g0", tileAtlasNew.findRegion("t_g0"));
        regions.put("g1", tileAtlasNew.findRegion("t_g1"));
        regions.put("g2", tileAtlasNew.findRegion("t_g2"));
        regions.put("g3", tileAtlasNew.findRegion("t_g3"));
        regions.put("g4", tileAtlasNew.findRegion("t_g4"));
        regions.put("g5", tileAtlasNew.findRegion("t_g5"));
        regions.put("g6", tileAtlasNew.findRegion("t_g6"));
        regions.put("g7", tileAtlasNew.findRegion("t_g7"));

        regions.put("g_E0", tileAtlasNew.findRegion("t_g_E0"));
        regions.put("g_E1", tileAtlasNew.findRegion("t_g_E1"));
        regions.put("g_E2", tileAtlasNew.findRegion("t_g_E2"));
        regions.put("g_E3", tileAtlasNew.findRegion("t_g_E3"));
        regions.put("g_E4", tileAtlasNew.findRegion("t_g_E4"));
        regions.put("g_E5", tileAtlasNew.findRegion("t_g_E5"));

        regions.put("g_N0", tileAtlasNew.findRegion("t_g_N0"));
        regions.put("g_N1", tileAtlasNew.findRegion("t_g_N1"));
        regions.put("g_N2", tileAtlasNew.findRegion("t_g_N2"));
        regions.put("g_N3", tileAtlasNew.findRegion("t_g_N3"));
        regions.put("g_N4", tileAtlasNew.findRegion("t_g_N4"));
        regions.put("g_N5", tileAtlasNew.findRegion("t_g_N5"));


        regions.put("g_S0", tileAtlasNew.findRegion("t_g_S0"));
        regions.put("g_S1", tileAtlasNew.findRegion("t_g_S1"));
        regions.put("g_S2", tileAtlasNew.findRegion("t_g_S2"));
        regions.put("g_S3", tileAtlasNew.findRegion("t_g_S3"));
        regions.put("g_S4", tileAtlasNew.findRegion("t_g_S4"));
        regions.put("g_S5", tileAtlasNew.findRegion("t_g_S5"));


        regions.put("g_W0", tileAtlasNew.findRegion("t_g_W0"));
        regions.put("g_W1", tileAtlasNew.findRegion("t_g_W1"));
        regions.put("g_W2", tileAtlasNew.findRegion("t_g_W2"));
        regions.put("g_W3", tileAtlasNew.findRegion("t_g_W3"));
        regions.put("g_W4", tileAtlasNew.findRegion("t_g_W4"));
        regions.put("g_W5", tileAtlasNew.findRegion("t_g_W5"));

        regions.put("p0", tileAtlasNew.findRegion("t_p0"));
        regions.put("p1", tileAtlasNew.findRegion("t_p1"));
        regions.put("p2", tileAtlasNew.findRegion("t_p2"));
        regions.put("p3", tileAtlasNew.findRegion("t_p3"));

        regions.put("p_NE0", tileAtlasNew.findRegion("t_p_NE0"));
        regions.put("p_NE1", tileAtlasNew.findRegion("t_p_NE1"));
        regions.put("p_NW0", tileAtlasNew.findRegion("t_p_NW0"));
        regions.put("p_NW1", tileAtlasNew.findRegion("t_p_NW1"));
        regions.put("p_SE0", tileAtlasNew.findRegion("t_p_SE0"));
        regions.put("p_SE1", tileAtlasNew.findRegion("t_p_SE1"));
        regions.put("p_SW0", tileAtlasNew.findRegion("t_p_SW0"));
        regions.put("p_SW1", tileAtlasNew.findRegion("t_p_SW1"));

        regions.put("g_SE0", tileAtlasNew.findRegion("t_g_SE0"));
        regions.put("g_SE1", tileAtlasNew.findRegion("t_g_SE1"));
        regions.put("g_SW0", tileAtlasNew.findRegion("t_g_SW0"));
        regions.put("g_SW1", tileAtlasNew.findRegion("t_g_SW1"));

        regions.put("g_NE0", tileAtlasNew.findRegion("t_g_NE0"));
        regions.put("g_NE1", tileAtlasNew.findRegion("t_g_NE1"));
        regions.put("g_NW0", tileAtlasNew.findRegion("t_g_NW0"));
        regions.put("g_NW1", tileAtlasNew.findRegion("t_g_NW1"));

        regions.put("tree0", tileAtlasNew.findRegion("tree0"));
        regions.put("tree1", tileAtlasNew.findRegion("tree1"));
        regions.put("tree2", tileAtlasNew.findRegion("tree2"));








    }

    public static TextureAtlas.AtlasRegion getRegion(String name) {
        return regions.get(name);
    }

    public static TextureAtlas.AtlasRegion getTile(MapEditorScreen.tileType tileType) {
        tiles.clear();
        switch (tileType) {
            case PATH:
                tiles.add(regions.get("p0"));
                tiles.add(regions.get("p1"));
                tiles.add(regions.get("p2"));
                tiles.add(regions.get("p3"));
                break;
            case GRASS:
                tiles.add(regions.get("g0"));
                tiles.add(regions.get("g1"));
                tiles.add(regions.get("g2"));
                tiles.add(regions.get("g3"));
                tiles.add(regions.get("g4"));
                tiles.add(regions.get("g5"));
                tiles.add(regions.get("g6"));
                tiles.add(regions.get("g7"));
                break;
            case EAST:
                tiles.add(regions.get("g_E0"));
                tiles.add(regions.get("g_E1"));
                tiles.add(regions.get("g_E2"));
                tiles.add(regions.get("g_E3"));
                tiles.add(regions.get("g_E4"));
                tiles.add(regions.get("g_E5"));
                break;
            case WEST:
                tiles.add(regions.get("g_W0"));
                tiles.add(regions.get("g_W1"));
                tiles.add(regions.get("g_W2"));
                tiles.add(regions.get("g_W3"));
                tiles.add(regions.get("g_W4"));
                tiles.add(regions.get("g_W5"));
                break;
            case NORTH:
                tiles.add(regions.get("g_N0"));
                tiles.add(regions.get("g_N1"));
                tiles.add(regions.get("g_N2"));
                tiles.add(regions.get("g_N3"));
                tiles.add(regions.get("g_N4"));
                tiles.add(regions.get("g_N5"));
                break;
            case SOUTH:
                tiles.add(regions.get("g_S0"));
                tiles.add(regions.get("g_S1"));
                tiles.add(regions.get("g_S2"));
                tiles.add(regions.get("g_S3"));
                tiles.add(regions.get("g_S4"));
                tiles.add(regions.get("g_S5"));
                break;



            }
            int rnd = new Random().nextInt(tiles.size());
        return tiles.get(rnd);
    }

}
