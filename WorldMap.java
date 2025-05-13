import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;

public class WorldMap {
    public static String[] map = {};
    private static WorldMap instance;
    private final File map1 = new File("assets/maps/map1.txt");
    private final File map2 = new File("assets/maps/map2.txt");
    private final File cave = new File("assets/maps/cave.txt");
    private final File collFile = new File("assets/maps/coll.txt");
    private final File caveColl = new File("assets/maps/collCave.txt");
    private final File tilesInfo = new File("assets/maps/tiles.txt");
    private final File caveInfo = new File("assets/maps/caveTiles.txt");

    private HashMap<Integer, BufferedImage> tileImages;
    public static HashSet<Integer> collisionSet;

    public enum MapType {
        MAP1, MAP2, CAVE
    }
    public static MapType currentMap;
    
    WorldMap() {
        tileImages = new HashMap<>();
        collisionSet = new HashSet<>();
        currentMap = MapType.MAP1; 
        getMap1();
        
    }
private BufferedImage getImage(String imageName) {
    String path = "/assets/photos/tiles/" + imageName;
    try {
        return ImageIO.read(getClass().getResource(path));
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
    public static WorldMap getInstance() {
        if(instance == null) {
            instance = new WorldMap();
        }
        return instance;
    }
    public void loadMap(File info) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(info);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                if (stringTokenizer.countTokens() != 2) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }
                String imageName = stringTokenizer.nextToken();
                int tileNumber = Integer.parseInt(stringTokenizer.nextToken());
                tileImages.put(tileNumber, getImage(imageName));
            }
        } catch (IOException e) {
            System.err.println("Error reading tiles: " );
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    // New method to load map from a file
    public void loadMapFromFile(File mapFile) {
        ArrayList<String> mapLines = new ArrayList<>();
        try (Scanner scanner = new Scanner(mapFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) { // Skip empty lines if any
                    mapLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading map file: " + mapFile.getPath());
            e.printStackTrace();
            return;
        }
        // Assign to map array
        map = mapLines.toArray(new String[0]);
    }
    public void mapDraw(Graphics2D g2){
        for(int r=0 ; r<GamePanel.worldscreenRowCount ; r++) {
            for(int c=0 ; c<GamePanel.worldscreenColCount ; c++) {
                String row = WorldMap.map[r];     
                String[] columns = row.split(" ");
                int tileNumber = Integer.parseInt(columns[c]);
                int worldX = c * GamePanel.tileSize; // x world
                int worldY = r * GamePanel.tileSize; // y world
                int screenX = worldX - GamePanel.player.x + GamePanel.player.screenX;
                int screenY = worldY - GamePanel.player.y + GamePanel.player.screenY;

                if(GamePanel.player.screenX > GamePanel.player.x) {
                    screenX = worldX;
                }
                if(GamePanel.player.screenY > GamePanel.player.y) {
                    screenY = worldY;
                }
                int rightLimit = GamePanel.windWidth -  GamePanel.player.screenX;
                if(rightLimit > GamePanel.worldWidth - GamePanel.player.x) {
                    screenX = GamePanel.windWidth - (GamePanel.worldWidth - worldX);
                }
                int bottomLimit = GamePanel.windHeight - GamePanel.player.screenY;
                if(bottomLimit > GamePanel.worldHeight - GamePanel.player.y) {
                    screenY = GamePanel.windHeight - (GamePanel.worldHeight - worldY);
                }


                if(worldX + GamePanel.tileSize > GamePanel.player.x - GamePanel.player.screenX &&
                    worldX - GamePanel.tileSize < GamePanel.player.x + GamePanel.player.screenX &&
                    worldY + GamePanel.tileSize > GamePanel.player.y - GamePanel.player.screenY &&
                    worldY - GamePanel.tileSize < GamePanel.player.y + GamePanel.player.screenY) {
                        if(tileImages.containsKey(tileNumber)) {
                            g2.drawImage(tileImages.get(tileNumber),screenX,screenY,GamePanel.tileSize ,GamePanel.tileSize ,null);
                        }
                } 
                else if(GamePanel.player.screenX > GamePanel.player.x ||
                        GamePanel.player.screenY > GamePanel.player.y ||
                        rightLimit > GamePanel.worldWidth - GamePanel.player.x ||
                        bottomLimit > GamePanel.worldHeight - GamePanel.player.y) {
                        g2.drawImage(tileImages.get(tileNumber),screenX,screenY,GamePanel.tileSize ,GamePanel.tileSize ,null);
            }
        }
    }
}
    // method to load collision 
    public void loadCollisionSetFromFile(File collisionFile) {
        collisionSet.clear(); // Clear existing collision set
        try (Scanner scanner = new Scanner(collisionFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                // spaces and commas as delimiters
                StringTokenizer tokenizer = new StringTokenizer(line, " ,");
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    try {
                        int tileId = Integer.parseInt(token);
                        collisionSet.add(tileId);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid integer in collision file: " + token);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading collision file: " + collisionFile.getPath());
            e.printStackTrace();
        }
    }
    public void getCave() {
        loadMapFromFile(cave);
        loadCollisionSetFromFile(caveColl);
        loadMap(caveInfo);
        currentMap = MapType.CAVE;
    }
    public void getFinalMAp() {
        loadMapFromFile(map2);
        loadCollisionSetFromFile(collFile);
        loadMap(tilesInfo);
        currentMap = MapType.MAP2;
    }
    public void getMap1() {
        loadMapFromFile(map1);
        loadCollisionSetFromFile(collFile);
        loadMap(tilesInfo);
        currentMap = MapType.MAP1;
    }
    

    public boolean isMap1() {return currentMap == MapType.MAP1;}
    
    public boolean isMap2() {return currentMap == MapType.MAP2;}
    
    public boolean isCave() {return currentMap == MapType.CAVE;}
}
