package no.fusiontd.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;



public class MapWriter extends MapReader {


    public void saveMap(int[][] map, String mapName){
        //Takes a map as input, and saves the map into a file.
        //It will save the map in one line, and go along the rows (starting top-left)
        String mapString = "";
        mapString = mapToString(map);
        String mapSaveString = "maps/" + mapName + ".txt";
        FileHandle file = Gdx.files.local(mapSaveString); //Give maps a unique filename?
        // If so it would probably require some kind of input from user, which could be given as input into the function.
        file.writeString(mapString, false); //"False"-value overwrites current content.
    }

    public String mapToString(int[][] map){
        //Turns the map into a String.
        //The string will only contain the values of each cell, starting with the upper-left cell, and going along the rows.
        String mapString = "";
        for (int i = 0; i<map.length; i++){
            for (int j = 0; j<map[0].length; j++){
                mapString = mapString + map[i][j];
            }
        }
        return mapString;
    }
}
