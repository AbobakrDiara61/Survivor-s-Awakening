import java.awt.*;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SuperObject{
    public enum OBJ {
        LETTER, Bandage, SPELLS, BOAT, WOOD, DYNAMITE, SYMBOL 
    }
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    protected boolean positionSetted;
    protected WorldMap.MapType mapType;
    protected OBJ obj;
    public Rectangle solidArea ;

    SuperObject() {
        solidArea = new Rectangle(0,0,48,48);
        positionSetted = false;
    }
    public void draw(Graphics2D g2, Player player ){
        mapType = WorldMap.currentMap;
        if(positionSetted == true && mapType == getMapType(obj)) {
            // Calculate the screen coordinates based on player's world position and screen position
            int worldXPixel = worldX * GamePanel.tileSize;
            int worldYPixel = worldY * GamePanel.tileSize;
            int screenX = worldXPixel - player.x + player.screenX;
            int screenY = worldYPixel - player.y + player.screenY;
            int horizontal = screenX;
            int vertical = screenY;
            if(screenX > worldXPixel) {
                horizontal = worldXPixel;
            }
            if(screenY > worldYPixel) {
                vertical = worldYPixel;
            }
            int rightLimit = GamePanel.windWidth - player.screenX;
            if(rightLimit > GamePanel.worldWidth - player.x) {
                horizontal = GamePanel.windWidth - (GamePanel.worldWidth - worldXPixel);
            }
            int bottomLimit = GamePanel.windHeight - player.screenY;
            if(bottomLimit > GamePanel.worldHeight - player.y) {
                vertical = GamePanel.windHeight - (GamePanel.worldHeight - worldYPixel);
            }
            // Draw the image if it's within the viewable area
            g2.drawImage(image, horizontal, vertical, GamePanel.tileSize, GamePanel.tileSize, null);
        }
    }
        protected void getImage(OBJ obj) {
            String filePath = getFilePath(obj);
            try {
                image = ImageIO.read(getClass().getResourceAsStream(filePath));
        } catch(IOException e) {
            System.err.println("Error Loading Image of " + obj.toString());
            System.err.println("with the file path " + filePath );
            e.printStackTrace();
        }
    }
    private String getFilePath(OBJ obj) {
        String fullPath = "assets/photos/obj/";
        switch (obj) {
            case LETTER:
                fullPath += "letter_1.png";
                break;
            case BOAT:
                fullPath += "Boat1.png";
                break;
            case WOOD:
                fullPath += "wood.png";
                break;
            case DYNAMITE:
                fullPath += "dynamite.png";
                break;
            case SYMBOL:
                fullPath += "symbol.png";
                break;
            default:
            fullPath = " ";
                break;
        }
        return fullPath;
    }
    protected void setCollision(OBJ obj) {
        switch (obj) {
            case LETTER:
                collision = true;
                break;
            case WOOD:
                collision = true;
                break;
            case DYNAMITE:
                collision = true;
                break;
            case BOAT:
                collision = true;
                break;
            default:
            collision = false;
                break;
        }      
    }
    protected WorldMap.MapType getMapType(OBJ obj) {
        WorldMap.MapType temp;
        switch (obj) {
            case LETTER:
                temp = WorldMap.MapType.CAVE;
                break;
            case SYMBOL:
                temp = WorldMap.MapType.CAVE;
                break;
            case BOAT:
                temp = WorldMap.MapType.MAP2;
                break;
            case WOOD:
                temp = WorldMap.MapType.MAP1;
                break;
            case DYNAMITE:
                temp = WorldMap.MapType.MAP2;
                break;
            default:
                temp = WorldMap.MapType.MAP1;
                break;
        }      
        return temp;
    }
    protected void setPosition(int worldX, int worldY, WorldMap.MapType mapType) {
        positionSetted = true;
        this.mapType = mapType;
        this.worldX = worldX;
        this.worldY = worldY;
    }
}
