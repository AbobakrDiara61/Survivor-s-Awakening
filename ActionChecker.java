public class ActionChecker {
    private WorldMap.MapType currentMapType;
    private Player player;
    public ActionChecker(/* WorldMap.MapType currentMapType */) {
        // this.currentMapType = currentMapType;
        this.player = GamePanel.player;
    }
    public boolean caveFound() {
        if(WorldMap.currentMap == WorldMap.MapType.MAP1){
            if(player.y <= 12 * GamePanel.tileSize) {
                if(player.x < 38 * GamePanel.tileSize && player.x > 15 * GamePanel.tileSize) {
                    return true;
                }
                
            }
        }
        return false;
    }
    public boolean isMapOne() {
        return currentMapType == WorldMap.MapType.MAP1;
    }

    public boolean isMapTwo() {
        return currentMapType == WorldMap.MapType.MAP2;
    }

    public boolean isCave() {
        return currentMapType == WorldMap.MapType.CAVE;
    }

/*     public boolean isFirstChoiceSelected() {
        // Assume GameState tracks player choice (true for first, false for second)
        return GameState.getInstance().getPlayerChoice() == 1;
    } */

    /* public boolean caveLetterFound(); */
}