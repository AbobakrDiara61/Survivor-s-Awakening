import java.awt.*;
import java.io.File;

public class Player extends Entity {
    KeyHandler kh;
    public final int screenX = GamePanel.windWidth / 2 - GamePanel.tileSize/2;
    public final int screenY = GamePanel.windHeight / 2 - GamePanel.tileSize/2; 
    private final File playerFile = new File("assets/sprites/player_sprites.txt");
    // private final File playerFile = new File("assets/sprites/kalra_sprites.txt");
    public Player(KeyHandler keyHandler) {
        this.kh = keyHandler;
        setDefault(20,40,4,"down");
        // overwrite on the collusion area of entity
        solidArea = new Rectangle(8, 16, 32, 32);
        getImages(playerFile);
    }
    // @Override
    public void update() {
        if(kh.up==true||kh.down==true||kh.right==true||kh.left==true)
        {  
            if(kh.up==true){ direction="up";}
            else if(kh.left==true){ direction="left";}
            else if(kh.right==true){ direction="right";}
            else if(kh.down==true){ direction="down";}
            collisionOn = false;
            GamePanel.cosDec.detect(this);
            if(!collisionOn) {
                if(direction.equals("up") ){ y-=speed;}
                if(direction.equals("left") ){ x-=speed;}
                if(direction.equals("right") ){ x+=speed;}
                if(direction.equals("down") ){ y+=speed;}
            } 
            if(kh.shift==true){speed = 6;}
            else{speed = 4;} 
            
            updateSpriteAnimation();

            switchMap();
        }
    }

    public void switchMap() {
        try {
            WorldMap worldMap = WorldMap.getInstance();
            int playerTileX = x / GamePanel.tileSize;
            int playerTileY = y / GamePanel.tileSize;
            
            System.out.println("Player position: " + playerTileX + ", " + playerTileY);


            String[] row = WorldMap.map[playerTileY].split(" ");
            int currentTile = Integer.parseInt(row[playerTileX]);
            

            switch (currentTile) {
                case 35: 
                    if (worldMap.isMap1()) { 
                        System.out.println("Map 1 to Cave");
                        worldMap.getCave(); 
                        setDefault(22,47,4,"down");
                    } 
                    else if (worldMap.isMap2()) { 
                        System.out.println("Map 2 to Cave");
                        worldMap.getCave(); 
                        setDefault(20,9,4,"down");
                    }
                    break;
                    
                case 4: 
                    if (worldMap.isCave()) { 
                        System.out.println("Cave to Map 1");
                        worldMap.getMap1(); 
                        setDefault(24,7,4,"down");
                    }
                    break;
                    
                case 27: 
                    if (worldMap.isCave()) { 
                        System.out.println("Cave to Map 2");
                        worldMap.getFinalMAp(); 
                        setDefault(20,48,4,"down");
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Unexpected error in switchMap: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
