package no.fusiontd.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.PrintWriter;

import no.fusiontd.game.Map;

public class MapWriter extends MapReader {

    Map originalMap = new Map();

    public void saveMap(int[][] map){
        //Takes a map as input, and saves the map into a file.
        //It will save the map in one line, and go along the rows (starting top-left)
        String mapString = "";
        mapString = mapToString(map);
        FileHandle file = Gdx.files.local("maps/mapSaved.txt"); //Give maps a unique filename?
        // If so it would probably require some kind of input from user, which could be given as input into the function.
        file.writeString(mapString, false); //"False"-value overwrites current content.
    }

    public String mapToString(int[][] map){
        //Turns the map into a String.

        String mapString = "";
        for (int i = 0; i<originalMap.TILEROWS; i++){
            for (int j = 0; j<originalMap.TILECOLS; j++){
                mapString = mapString + map[i][j];
            }
        }
        return mapString;
    }
}
