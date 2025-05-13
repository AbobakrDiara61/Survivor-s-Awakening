import java.util.ArrayList;
import java.util.HashSet;

public class Pathfinding {
    private final static int MAX_STEPS = 500;
    ArrayList<Node> openList;
    ArrayList<Node> closedList;
    ArrayList<Node> checkedList;
    ArrayList<Node> pathList;
    Node[][] nodes;
    private static final int ROW = 50;
    private static final int COL = 50;
    public Node start;
    public Node currentNode;
    public Node dest;
    boolean goalReached;
    public HashSet<Integer> collisionSet;
    public String[] map;
    
    Pathfinding() {
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        checkedList = new ArrayList<>();
        pathList = new ArrayList<>();
        collisionSet = WorldMap.collisionSet;
        nodes = new Node[ROW][COL];
        map = WorldMap.map;
        instantiateNodes();
        
    }
    private void instantiateNodes() {
        for(int r = 0 ; r < ROW ; r++) {
            for(int c = 0 ; c < COL ; c++) {
                Node newNode = new Node(r,c);
                nodes[r][c] = newNode;
            }
        }
    }
    private boolean isCollision(int tileNumber) {
        if(collisionSet.contains(tileNumber)) {
            return true;
        }
        return false;
    }
    private void getCost(Node node) {
        int dx = Math.abs(start.col - node.col);
        int dy = Math.abs(start.row - node.row);
        node.gCost = dx + dy;
        // estimated
        dx = Math.abs(dest.col - node.col);
        dy = Math.abs(dest.row - node.row);
        node.hCost = dx + dy;
        
        node.fCost = node.hCost + node.gCost;
    }
    private void resetNodes() {
        for(int r = 0 ; r < ROW ; r++) {
            for(int c = 0 ; c < COL ; c++) {
                nodes[r][c].checked = false;
                nodes[r][c].open = false;       
                /* String row = map[r];     
                String[] columns = row.split(" ");
                int tileNumber = Integer.parseInt(columns[c]);   
                nodes[r][c].solid = isCollision(tileNumber); */
                // getCost(nodes[r][c]);
            }
        }
        /* openList.clear();
        pathList.clear();
        goalReached = false; */
    }
    public void setNodes(int startRow, int goalRow, int startCol, int goalCol) {
        resetNodes();
        dest = nodes[goalRow][goalCol];
        currentNode = start = nodes[startRow][startCol];
        openList.add(currentNode);
        for(int r = 0 ; r < ROW ; r++) {
            for(int c = 0 ; c < COL ; c++) {
                String row = map[r];     
                String[] columns = row.split(" ");
                int tileNumber = Integer.parseInt(columns[c]);   
                nodes[r][c].solid = isCollision(tileNumber);
                getCost(nodes[r][c]);
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
    }
    public boolean search() {
        int step = 0;
        while (goalReached == false && step < MAX_STEPS) {
            int row = currentNode.row;
            int col = currentNode.col;
            currentNode.checked = true;
            openList.remove(currentNode);
            
            if(isValid(row,col-1)) {
                openNode( nodes[row][col-1] );
            }
            if(isValid(row,col+1)) {
                openNode( nodes[row][col+1] );
            }
            if(isValid(row-1,col)) {
                openNode( nodes[row-1][col] );
            }
            if(isValid(row+1,col)) {
                openNode( nodes[row+1][col] );
            }
            if(openList.size() == 0) {
                break;
            }
            // see open nodes whice one is the best
            int bestNodeIndex = 0;
            int bestFCost = Integer.MAX_VALUE;
            for(int i=0 ; i < openList.size() ; i++) {
                if(openList.get(i).fCost < bestFCost) {
                    bestFCost = openList.get(i).fCost;
                    bestNodeIndex = i;
                }
                if(openList.get(i).fCost == bestFCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // move to next step whice is the best node
            currentNode = openList.get(bestNodeIndex);
            if(dest == currentNode) {
                goalReached = true;
                trackPath();
            }
            if (step % 50 == 0) {
                System.out.println("Step " + step + ": openList size = " + openList.size());
            }
            step++;
        }
        return goalReached;
    }
    public void trackPath() {
        Node currentNode = dest;
        while (currentNode != start) {
            pathList.add(0,currentNode.parent);
            currentNode = currentNode.parent;
        }

    }
    private void openNode(Node node) {
        if(node.checked == false && node.open == false && node.solid == false) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    } 
    public void setDest(int row, int col) {
        dest.row = row;
        dest.col = col;
    }
    public void setStart(int row, int col) {
        start.row = row;
        start.col = col;
    }
    private static boolean isValid(int row, int col)
    {
        return (row >= 0) && (row < ROW) && (col >= 0)
            && (col < COL);
    }
    private boolean isDestination(int row, int col)
    {
        return row == dest.row && col == dest.col;
    }


}
