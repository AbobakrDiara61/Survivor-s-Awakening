public class GameState {
    public enum State {
        PLAY, PAUSE, DIALOGUE, GAME_OVER
    }
    private State currentState;
    private static GameState instance;
    private GameState() {
        currentState = State.PLAY;
    }
    public static GameState getInstance() {
        if(instance == null) {
            instance = new GameState();
        }
        return instance;
    }
    public State getCurrentState() {
        return currentState;
    }
    public void setCurrentState(State state) {
        this.currentState = state;
    }
    public String getStateName() {
        return currentState.toString();
    }

}   
