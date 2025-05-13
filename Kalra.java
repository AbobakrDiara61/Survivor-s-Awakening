import java.awt.Graphics2D;
import java.io.File;

public class Kalra extends Entity{
    private final File npcFile = new File("assets/sprites/kalra_sprites.txt");
    private int escapeRow = 29;
    private int escapeCol = 26;
    private int startRow = 11;
    private int startCol = 25;
    private Pathfinding pathfinder;
    private boolean reached;
    private int tileSize;
    Kalra() {
        getImages(npcFile);
        setDefault(startRow, startCol,4,"down");
        pathfinder = new Pathfinding();
        reached = false;
        tileSize = 32;
        
    }
    public void move(int nextX, int nextY) {
        if(x < nextX) {
            x = x+1;
        }
        if(x > nextX) {
            x = x-1;
        }
        if(y < nextY) {
            y = y+1;
        }
        if(x > nextY) {
            y = y-1;
        }
    }
public void escape() {
    while (!reached) {
        pathfinder.setNodes(startRow, escapeRow, startCol, escapeCol);
        if (pathfinder.search()) {
            if (!pathfinder.pathList.isEmpty()) {
                int nextCol = pathfinder.pathList.get(0).col;
                int nextRow = pathfinder.pathList.get(0).row;
                int nextX = nextCol * tileSize;
                int nextY = nextRow * tileSize;
                move(nextX, nextY);
                if (nextCol == escapeCol && nextRow == escapeRow) {
                    reached = true;
                }
            } else {
                System.out.println("Path list is empty even though search() returned true!");
                break;
            }
        } else {
            System.out.println("Path not found!");
            break;
        }
    }
}

    @Override
    public void draw(Graphics2D g2) {
        WorldMap worldMap = WorldMap.getInstance();
        if (worldMap.isMap2()) {
            super.draw(g2);
        }
    }
    public void startEscaping() {
        escape();
    }

}
