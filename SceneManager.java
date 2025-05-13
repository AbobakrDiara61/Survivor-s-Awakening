public class SceneManager {
    public enum Scene {
        SHORE, FOREST, FOREST_EXIT, CAVE_ENTRANCE, CAVE_LETTER, 
        CAVE_EXIT, FIND_KALRA, GET_INJURED, ESCAPE
    }
    private Scene currentScene;
    private DialogManager dialogManager;
    private GameState gameState;
    public SceneManager() {
        currentScene = Scene.SHORE;
        dialogManager = GamePanel.dialogManager;
        gameState = GameState.getInstance();
    }
    private boolean sceneInitialized = false;
    private ActionChecker actionChecker = new ActionChecker();
    public void update() {
        switch (currentScene) {
            case SHORE:   
            if (!sceneInitialized) {
                gameState.setCurrentState(GameState.State.DIALOGUE);
                dialogManager.setDialogueCounter(2); // lines you want
                dialogManager.getDanielDialog();
                sceneInitialized = true;
            }
            GameState.State lastState = gameState.getCurrentState();
            gameState.setCurrentState(GameState.State.DIALOGUE);
            if( !dialogManager.hasMoreDialogue() ) {
                gameState.setCurrentState(lastState);
                currentScene = Scene.FOREST;
            }
                break;
            case FOREST:
            System.out.println(actionChecker.caveFound());
            if(actionChecker.caveFound()) {
                gameState.setCurrentState(GameState.State.DIALOGUE);
                dialogManager.getDanielDialog();
                currentScene = Scene.CAVE_ENTRANCE;
            }
                break;
            case FOREST_EXIT:
            break;
            case CAVE_ENTRANCE:
                break;
            case CAVE_LETTER:
                break;
            case CAVE_EXIT:
                break;
            case FIND_KALRA:
                break;
            case GET_INJURED:
                break;
            case ESCAPE:
                break;
            default:
                break;
        }
    }
    public Scene getCurrentScene() {
        return currentScene;
    }
    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }
}
