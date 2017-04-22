package no.fusiontd.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;



public class MapWriter extends MapReader {


    public void saveMap(int[][] map, String mapName){
        //Takes a map as input, and saves the map into a file.
        //It will save the map row after row, with newlines separating, with upper-left corner as a start-point.
        String mapString = "";
        String locRoot = Gdx.files.getLocalStoragePath();
        mapString = mapToString(map);
        String mapSaveString = locRoot + "maps/" + mapName + ".txt";
        //FileHandle file = Gdx.files.local(mapSaveString);
        FileHandle file = Gdx.files.absolute(mapSaveString);

        file.writeString(mapString, false); //"False"-value overwrites current content.
    }

    public String mapToString(int[][] map){
        //Turns the map into a String.
        String mapString = "";
        for (int i = 0; i<map.length; i++){
            for (int j = 0; j<map[0].length; j++){
                mapString = mapString + map[i][j];
            }
            mapString += "\n";
        }
        return mapString;
    }
}
