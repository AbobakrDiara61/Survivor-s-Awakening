public class CollisionDetector {
    private static CollisionDetector instance;
    String tileColOne;
    String tileColTwo;
    public CollisionDetector() {
        
    }
    public static CollisionDetector getInstance() {
        if(instance == null) {
            instance = new CollisionDetector();
        }
        return instance;
    }
    public void detect(Entity character) {
        int charWorldX = character.x;
        int charWorldY = character.y;
        String direction = character.direction;

        int leftSideX = charWorldX + character.solidArea.x ;
        int rightSideX = charWorldX + character.solidArea.x + character.solidArea.width;
        int topY = charWorldY + character.solidArea.y;
        int bottomY = charWorldY + character.solidArea.y + character.solidArea.height;

        int leftRectCol = leftSideX / GamePanel.tileSize;
        int rightRectCol = rightSideX / GamePanel.tileSize;
        int toptRectRow = topY / GamePanel.tileSize;
        int bottRectRow = bottomY / GamePanel.tileSize;

        switch (direction) {
            case "up":
                toptRectRow = (topY - character.speed)/GamePanel.tileSize;  
                tileColOne = getTileAt(toptRectRow, leftRectCol);
                tileColTwo = getTileAt(toptRectRow, rightRectCol);

                break;
                case "down":

                bottRectRow = (bottomY + character.speed) / GamePanel.tileSize;
                tileColOne = getTileAt(bottRectRow, leftRectCol);
                tileColTwo = getTileAt(bottRectRow, rightRectCol);

                case "right":             
                rightRectCol = (rightSideX + character.speed) / GamePanel.tileSize;
                tileColOne = getTileAt(toptRectRow, rightRectCol);
                tileColTwo = getTileAt(bottRectRow, rightRectCol);         
                break;

                case "left":
                leftRectCol = (leftSideX - character.speed) / GamePanel.tileSize;   
                tileColOne = getTileAt(toptRectRow, leftRectCol);
                tileColTwo = getTileAt(bottRectRow, leftRectCol);
                break; 
                default:
                break;
        }
        // Check if either tile is in the collision set
        if (WorldMap.collisionSet.contains(Integer.parseInt(tileColOne)) || WorldMap.collisionSet.contains(Integer.parseInt(tileColTwo))) {
            character.collisionOn = true;
        }
    }
    private String getTileAt(int row, int col) {
        boolean check = (row >= 0 && row < WorldMap.map.length && col >= 0 && col < WorldMap.map[row].length());
        if(check) {
            String line = WorldMap.map[row];
            String Columns[] = line.split(" ");
            return Columns[col];
        }
        return " "; // Return non-collision space if out of bounds
    }
/*     public boolean entitiesDetection(Entity npc) {    
        int leftSideX = npc.x + npc.solidArea.x ;
        int rightSideX = npc.x + npc.solidArea.x + npc.solidArea.width;
        int topY = npc.y + npc.solidArea.y;
        int bottomY = npc.y + npc.solidArea.y + npc.solidArea.height;

        Player playeerrr = GamePanel.player;
        switch (npc.direction) {
            case "up":
                if(topY == playeerrr.y + playeerrr.solidArea.y + playeerrr.solidArea.height) {
                    return true;
                }
                break;
            case "down":
                if(bottomY == playeerrr.y + playeerrr.solidArea.y) {
                    return true;
                }
                break;
            case "right":
                if(rightSideX == playeerrr.x + playeerrr.solidArea.x) {
                    return true;
                }
                break;
            case "left":
                if(leftSideX == playeerrr.x + playeerrr.solidArea.x + playeerrr.solidArea.width) {
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    } */
}
