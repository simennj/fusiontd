package no.fusiontd.maps;

import com.badlogic.gdx.Gdx;

import no.fusiontd.menu.ExitButton;

public class MapReader {

    public int[][] loadMap(String mapName, int rows, int cols) {

        int[][] map = new int[rows][cols];

        // \\R matches any newline char
        String[] level;
        try {
            level = Gdx.files.internal("maps/" + mapName).readString().split("\\R");
        }catch (Exception e){
            e.printStackTrace();
            String locRoot = Gdx.files.getLocalStoragePath();
            level = Gdx.files.absolute(locRoot + "maps/" + mapName).readString().split("\\R");
        }

        int y_max = rows - 1;
        for (int y = 0; y < rows; y++) {
            char[] row = level[y].toCharArray();
            for (int x = 0; x < cols; x++) {
                map[y_max - y][x] = Character.getNumericValue(row[x]);
            }
        }
        return map;
    }
}