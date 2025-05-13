import java.awt.*;
// import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements /* ActionListener, */Runnable{
    static final public int tileSize = 48;

    public static int screenRowCound = 16;
    public static int screenColCount = 21;

    public final static  int windWidth = tileSize * screenColCount;
    public final static  int windHeight = tileSize * screenRowCound;

    public final static int worldscreenRowCount = 50;
    public final static int worldscreenColCount = 50;
    
    public final static int worldHeight = tileSize * worldscreenRowCount;
    public final static int worldWidth = tileSize * worldscreenColCount;
    
    WorldMap worldMap = WorldMap.getInstance();
    public static CollisionDetector cosDec = CollisionDetector.getInstance();
    public static KeyHandler keyHandler = new KeyHandler();
    Thread  gameThread;
    int FPS =60;
    public static Player player = new Player(keyHandler);
    // Ui
    public Ui gameUi = new Ui(this, keyHandler);
    public static DialogManager dialogManager = new DialogManager();
    public static Kalra kalra = new Kalra();
    public static GameState gameState = GameState.getInstance();
    public static GameSaveData savedPlayerState;
    public ObjectManager objectManager = ObjectManager.getInstance();

    GamePanel() {
        setPreferredSize(new Dimension(windWidth,windHeight));
        setBackground(Color.BLACK);
        gameState.setCurrentState(GameState.State.PLAY);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setDoubleBuffered(true); // all drawing will be done offscreen the screen
    }
    public void StartGameThread()
    {
        // starting a thread so we can make time
        gameThread = new Thread(this);
        gameThread.start();
    }
        //Runnable Interface
        @Override
    public void run() {
        double drawInterval =1000000000/FPS; // draw screen every 1/60 second 
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer=0;
        int frames = 0;
        // ass long gameThread is exist repeat block
        while(gameThread !=null){
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (deltaTime >= 1) {
                // update info charchters pos 
                update();
                // draw screen with unpdated info
                repaint();
                deltaTime--;
                frames++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: "+frames);
                // System.out.println();
                timer = 0;
                frames = 0;
            }
        }
        }
    public void update(){   
        if (gameState.getCurrentState() == GameState.State.PLAY) {
            player.update();
        } else if (gameState.getCurrentState() == GameState.State.PAUSE) {
            gameUi.handlePauseInput();

        }
    }
    SceneManager sceneManager = new SceneManager();
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        worldMap.mapDraw(g2);
        objectManager.draw(g2,player);
        player.draw(g2);
        kalra.draw(g2);
        dialogManager.g2 =g2;
        sceneManager.update();
        dialogManager.draw(g2);
        if (gameState.getCurrentState() == GameState.State.PAUSE) {
            gameUi.drawPauseScreen(g2);
        }

        // gameUi.draw(g2);
        g2.dispose();// release memory
    }

}
