import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;



class GameSaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int playerX;
    public int playerY;
    public String playerDirection;
    public String currentScene;
    public int dialogCounter;
    public int danielDialogIndex;
    public int karlaDialogIndex;

    
    public String currentMap;
} 



public class SaveManager {

    private static SaveManager instance;
    private GameSaveData currentSaveData;
    
    private SaveManager() {
        currentSaveData = new GameSaveData();
    }
    
    public static SaveManager getInstance() {
        if (instance == null) {
            instance = new SaveManager();
        }
        return instance;
    }
    
    
    public void saveGame() {
        try {
            
            SceneManager sceneManager = new SceneManager();
            DialogManager dialogManager = GamePanel.dialogManager;

            Player player = GamePanel.player;
            currentSaveData.playerX = player.x;
            currentSaveData.playerY = player.y;
            currentSaveData.playerDirection = player.direction;
            currentSaveData.currentMap = WorldMap.currentMap.toString();
            currentSaveData.currentScene = sceneManager.getCurrentScene().toString();
            currentSaveData.dialogCounter = dialogManager.getDialogCounter();
            currentSaveData.danielDialogIndex = dialogManager.getDanielDialogIndex();
            currentSaveData.karlaDialogIndex = dialogManager.getKarlaDialogIndex();
            
            GamePanel.savedPlayerState = currentSaveData;
            
            System.out.println("Game saved: Player at " + player.x + ", " + player.y + 
                             " in " + WorldMap.currentMap + " map");
            
            
            saveToFile();
        } catch (Exception e) {
            System.out.println("Error saving game: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
    
 
    public void loadGame() {
        try {
            if (GamePanel.savedPlayerState != null) {
                GameSaveData saveData = GamePanel.savedPlayerState;
                
                SceneManager sceneManager = new SceneManager();
                DialogManager dialogManager = GamePanel.dialogManager;
                WorldMap worldMap = WorldMap.getInstance();

                String mapType = saveData.currentMap;
                
                if ("MAP1".equals(mapType)) {
                    worldMap.getMap1();
                } else if ("MAP2".equals(mapType)) {
                    worldMap.getFinalMAp();
                } else if ("CAVE".equals(mapType)) {
                    worldMap.getCave();
                }
                
                
                Player player = GamePanel.player;
                player.x = saveData.playerX;
                player.y = saveData.playerY;
                player.direction = saveData.playerDirection;
                sceneManager.setCurrentScene(SceneManager.Scene.valueOf(saveData.currentScene));
                dialogManager.setDialogueCounter(saveData.dialogCounter);
                dialogManager.setDanielDialogIndex(saveData.danielDialogIndex);
                dialogManager.setKarlaDialogIndex(saveData.karlaDialogIndex);

                
                System.out.println("Game loaded: Player at " + player.x + ", " + player.y + 
                                " in " + WorldMap.currentMap + " map");
            } else {
                System.out.println("No save data found, trying to load from file");
                loadFromFile();
            }
        } catch (Exception e) {
            System.out.println("Error loading game: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    private void saveToFile() {
        try {

            
            FileOutputStream fileOut = new FileOutputStream("assets/saves/gamesave.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(currentSaveData);
            out.close();
            fileOut.close();
            System.out.println("Game saved to file");
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    

    public void loadFromFile() {
        try {
            File saveFile = new File("assets/saves/gamesave.txt");
            if (saveFile.exists()) {
                FileInputStream fileIn = new FileInputStream(saveFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                GameSaveData saveData = (GameSaveData) in.readObject();
                in.close();
                fileIn.close();

                GamePanel.savedPlayerState = saveData;
                  
                loadGame();
                
                System.out.println("Game loaded from file");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}

