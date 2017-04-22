package no.fusiontd.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AtlasCreator {

    private static final String ASSET_DIR = "android/assets";

    public static void main(String[] args) {
        TexturePacker.process(ASSET_DIR + "/ui", ASSET_DIR, "ui");
        TexturePacker.process(ASSET_DIR + "/tiles", ASSET_DIR, "tiles");
        TexturePacker.process(ASSET_DIR + "/t", ASSET_DIR, "tiles_new");
        TexturePacker.process(ASSET_DIR + "/sprites", ASSET_DIR, "sprites");
    }
}
