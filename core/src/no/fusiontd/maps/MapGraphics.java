package no.fusiontd.maps;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Random;

class MapGraphics {
    private final int TILEROWS, TILECOLS;
    private final TextureAtlas tilesAtlas = new TextureAtlas("tiles.atlas");
    private int[][] padMap;
    private TextureAtlas.AtlasRegion[][] tileTextures;
    private ObjectMap<String, Array<TextureAtlas.AtlasRegion>> textures = new ObjectMap<String, Array<TextureAtlas.AtlasRegion>>();
    private Random random = new Random();

    MapGraphics(int[][] map) {
        TILEROWS = map.length;
        TILECOLS = map[0].length;
        this.padMap = padMap(map);
        initTextures();
        tileTextures = new TextureAtlas.AtlasRegion[TILEROWS][TILECOLS];
        updateGraphics();
    }

    private void updateGraphics() {
        for (int row = 0; row < TILEROWS; row++) {
            tileTextures[row] = new TextureAtlas.AtlasRegion[TILECOLS];
            for (int col = 0; col < TILECOLS; col++) {
                setTileGraphic(col + 1, row + 1);
            }
        }
    }

    private int[][] padMap(int[][] map) {
        int[][] paddedMap = new int[TILEROWS + 2][TILECOLS + 2];
        int y_max = TILEROWS + 1;
        for (int i = 0; i < TILEROWS + 2; i++) {
            for (int j = 0; j < TILECOLS + 2; j++) {
                if (i == 0 || j == 0 || i == TILEROWS + 1 || j == TILECOLS + 1) { //  || map[y_max - i - 1][j-1] == 2 || map[y_max - i- 1][j-1] == 3
                    paddedMap[y_max - i][j] = 0;
                } else {
                    paddedMap[y_max - i][j] = Math.min(map[y_max - i - 1][j - 1], 1);
                }
            }
        }
        return paddedMap;
    }

    private void initTextures() {

        //OBS!: Unfortunately inverted graphics from arrey, i.e. RoadWest gives GroundEast tile etc.
        // The corners are seriously messed up. BEWARE!!!

        textures.put("road", tilesAtlas.findRegions("t_p"));
        textures.put("tree", tilesAtlas.findRegions("tree"));

        textures.put("00000000", tilesAtlas.findRegions("t_g"));
        textures.put("10000011", tilesAtlas.findRegions("t_g_N"));
        textures.put("10000001", tilesAtlas.findRegions("t_g_N"));
        textures.put("00000011", tilesAtlas.findRegions("t_g_N"));
        textures.put("00001110", tilesAtlas.findRegions("t_g_W"));
        textures.put("00000110", tilesAtlas.findRegions("t_g_W"));
        textures.put("00001100", tilesAtlas.findRegions("t_g_W"));
        textures.put("00111000", tilesAtlas.findRegions("t_g_S"));
        textures.put("00011000", tilesAtlas.findRegions("t_g_S"));
        textures.put("00110000", tilesAtlas.findRegions("t_g_S"));
        textures.put("11100000", tilesAtlas.findRegions("t_g_E"));
        textures.put("01100000", tilesAtlas.findRegions("t_g_E"));
        textures.put("11000000", tilesAtlas.findRegions("t_g_E"));
        textures.put("10000000", tilesAtlas.findRegions("t_g_NE"));
        textures.put("00000010", tilesAtlas.findRegions("t_g_NW"));
        textures.put("00100000", tilesAtlas.findRegions("t_g_SE"));
        textures.put("00001000", tilesAtlas.findRegions("t_g_SW"));
        textures.put("11100011", tilesAtlas.findRegions("t_p_SW"));
        textures.put("11000011", tilesAtlas.findRegions("t_p_SW"));
        textures.put("11100001", tilesAtlas.findRegions("t_p_SW"));
        textures.put("11000001", tilesAtlas.findRegions("t_p_SW"));
        textures.put("10001111", tilesAtlas.findRegions("t_p_SE"));
        textures.put("00001111", tilesAtlas.findRegions("t_p_SE"));
        textures.put("10000111", tilesAtlas.findRegions("t_p_SE"));
        textures.put("00000111", tilesAtlas.findRegions("t_p_SE"));
        textures.put("11111000", tilesAtlas.findRegions("t_p_NW"));
        textures.put("01111000", tilesAtlas.findRegions("t_p_NW"));
        textures.put("11110000", tilesAtlas.findRegions("t_p_NW"));
        textures.put("01110000", tilesAtlas.findRegions("t_p_NW"));
        textures.put("00111110", tilesAtlas.findRegions("t_p_NE"));
        textures.put("00111100", tilesAtlas.findRegions("t_p_NE"));
        textures.put("00011110", tilesAtlas.findRegions("t_p_NE"));
        textures.put("00011100", tilesAtlas.findRegions("t_p_NE"));
    }

    private void setTileGraphic(int x, int y) {
        Array<TextureAtlas.AtlasRegion> variations;
        if (padMap[y][x] == 1 || padMap[y][x] == 2 || padMap[y][x] == 3) {
            variations = textures.get("road");
        } else {
            variations = textures.get(checkNeighbours(padMap, x, y));
            if (variations == null) {
                variations = textures.get("00000000");
            }
        }
        tileTextures[y - 1][x - 1] = variations.get(getRandomIndex(variations.size));
    }

    private int getRandomIndex(int size) {
        int candidate = random.nextInt(size + 1);
        if (candidate >= size) {
            candidate = 0;
        }
        return candidate;
    }

    TextureAtlas.AtlasRegion getTileGraphic(int x, int y) {
        return tileTextures[y][x];
    }

    private String checkNeighbours(int[][] map, int x, int y) {

        // (0,0) is in upper right corner (northeast) and going clockwise around the point

        String neighbours = "";

        neighbours += isGrass(x - 1, y - 1);
        neighbours += isGrass(x - 1, y);
        neighbours += isGrass(x - 1, y + 1);
        neighbours += isGrass(x, y + 1);
        neighbours += isGrass(x + 1, y + 1);
        neighbours += isGrass(x + 1, y);
        neighbours += isGrass(x + 1, y - 1);
        neighbours += isGrass(x, y - 1);

        return neighbours;
    }

    private char isGrass(int x, int y) {
        return padMap[y][x] == 0 ? '0' : '1';
    }

    void setType(int row, int col, int tileType) {
        padMap[row + 1][col + 1] = tileType;
        updateGraphics();
    }
}