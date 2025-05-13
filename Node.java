public class Node {
    public Node parent;
    public int col;
    public int row;
    public int hCost; // Distacne current and goal pos
    public int gCost; // Distacne current and start pos
    public int fCost; // sum
    public boolean checked; 
    public boolean solid; 
    public boolean open; 
    public Node () {
        
    }
    public Node (int row, int col) {
        this.row = row;
        this.col = col;
    }
}
