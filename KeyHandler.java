import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean up, down, left, right, shift, space, enter, pause = false;
    private GameState gameState;
    private SaveManager saveManager;
    static GamePanel gp;
    static Ui gameUi;
    
    KeyHandler() {
        gameState = GameState.getInstance();
        saveManager = SaveManager.getInstance();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        // Save and Load functionality
        if (code == KeyEvent.VK_F5) {
            saveManager.saveGame();
            return;
        }
        if (code == KeyEvent.VK_F9) {
            saveManager.loadGame();
            return;
        }

        
        if(gameState != null && gameState.getCurrentState() == GameState.State.DIALOGUE) {
            if(code == KeyEvent.VK_ENTER) {
                if(GamePanel.dialogManager.hasMoreDialogue()) {
                    GamePanel.dialogManager.decreaseCounter();
                    GamePanel.dialogManager.getDanielDialog();
                } else {
                    gameState.setCurrentState(GameState.State.PLAY);
                }
            }
        }    
        else if(gameState.getCurrentState() == GameState.State.PLAY || gameState.getCurrentState() == GameState.State.PAUSE) {
            switch (code) {
                case KeyEvent.VK_W : up = true; break;
                case KeyEvent.VK_S: down = true; break;
                case KeyEvent.VK_A: left = true; break;
                case KeyEvent.VK_D: right = true; break;
                case KeyEvent.VK_SHIFT: shift = true; break;
                case  KeyEvent.VK_ENTER: enter = true; break;
                case KeyEvent.VK_ESCAPE:
                if (GamePanel.gameState.getCurrentState() == GameState.State.PLAY) {
                    GamePanel.gameState.setCurrentState(GameState.State.PAUSE);
                } else {
                    GamePanel.gameState.setCurrentState(GameState.State.PLAY);
                } break;

            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W: up = false; break;
            case KeyEvent.VK_S: down = false; break;
            case KeyEvent.VK_A: left = false; break;
            case KeyEvent.VK_D: right = false; break;
            case KeyEvent.VK_SHIFT: shift = false; break;
            case KeyEvent.VK_ENTER: enter = false; break;

        }
    }
}
