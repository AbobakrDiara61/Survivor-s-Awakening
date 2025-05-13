import java.awt.Graphics2D;

public class BoatObj extends SuperObject {
    public BoatObj() {
        obj = SuperObject.OBJ.BOAT;
        name = obj.toString();
        getImage(obj);
        setCollision(obj);
        setPosition(30, 26, WorldMap.MapType.MAP2);
    }

    @Override
    public void draw(Graphics2D g2, Player player) {
        mapType = WorldMap.currentMap;
        if(positionSetted == true && mapType == getMapType(obj)) {
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
            
            g2.drawImage(image, horizontal, vertical, GamePanel.tileSize * 3, GamePanel.tileSize * 3, null);
        }
    }
}
