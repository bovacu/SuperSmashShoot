package maps;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.SuperSmashShoot;
import tiles.RegularTile;
import tiles.Tile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapParser {

    private static final String PATH = "maps/";

    public static List<Tile> getTilesFromFile(String fileName){
        List<Tile> tiles = new ArrayList<>();
        File file = new File(PATH + fileName);
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            int width = Integer.valueOf(br.readLine().split(":")[1]) / Tile.TILE_WH;
            int height = Integer.valueOf(br.readLine().split(":")[1]) / Tile.TILE_WH;

            float centerX = (SuperSmashShoot.SCREEN_WIDTH - width * Tile.TILE_WH) / 2f;
            float centerY = (SuperSmashShoot.SCREEN_HEIGHT - height * Tile.TILE_WH) / 2f;

            for(int y = height - 1; y >= 0; y--){
                String vectorElements[] = br.readLine().split("\\s+");
                for(int x = 0; x < width; x++){
                    if(vectorElements[x].contains("r")){
                        Tile rt = new RegularTile(Integer.valueOf(vectorElements[x].split(":")[1]
                                .replaceAll("]", "").replaceAll("\\[", "")),
                                new Vector2(Tile.TILE_WH * x + centerX, Tile.TILE_WH * y + centerY),
                                new Vector2(Tile.TILE_WH, Tile.TILE_WH));
                        tiles.add(rt);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }

        return tiles;
    }
}
