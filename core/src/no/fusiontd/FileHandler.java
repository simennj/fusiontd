package no.fusiontd;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileHandler {
    public static FileHandle[] createFilehandle() {
        String locRoot = Gdx.files.getLocalStoragePath();
        FileHandle[] assetMaps = Gdx.files.internal("maps/").list();
        FileHandle[] createdMaps = Gdx.files.absolute(locRoot + "maps/").list();

        boolean equalArrays = true;
        if (assetMaps.length != createdMaps.length) {
            equalArrays = false;
        } else {
            for (int i = 0; i < assetMaps.length; i++) {
                if (!assetMaps[i].nameWithoutExtension().equals(createdMaps[i].nameWithoutExtension())) {
                    equalArrays = false;
                    break;
                }

            }
        }
        FileHandle[] files;
        if (equalArrays) {
            files = assetMaps;
        } else {
            files = new FileHandle[createdMaps.length + assetMaps.length];
            int count = 0;
            for (int i = 0; i < assetMaps.length; i++) {
                files[i] = assetMaps[i];
                count++;
            }
            for (int j = 0; j < createdMaps.length; j++) {
                files[count++] = createdMaps[j];
            }
        }

        return files;
    }
}