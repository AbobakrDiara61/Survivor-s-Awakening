
import javax.swing.*;
import java.awt.*;

public class Ui{
    GamePanel gp;
    KeyHandler kh;
    String[] pauseOptions = {"Resume", "Save Game", "Load Game", "Exit"};
    private long lastInputTime = 0;  // Tracks last input time
    private final long inputCooldown = 200;  // Cooldown time in milliseconds (adjust as needed)
    public Ui(GamePanel gp, KeyHandler kh)
    {
        this.gp = gp;
        this.kh = kh;

    }
    protected Font arial_20;
    protected Font arial_30;
    protected Font arial_40;
    protected Font abril_20;
    protected Font tagess_20;
    protected Font fjalla_20;
    protected Graphics2D g2;
    protected int counter = 0;
    protected boolean visible = true;
    protected String msg;
    protected Color dialogBackColor;
    protected int selectedOption = 0;
    protected int totalOptions = 4;
    private String fontPath;
    public Ui() {
        fontPath = "assets/fonts/";
        abril_20 = FontLoader.loadFont(fontPath+"AbrilFatface-Regular.ttf", 20);
        fjalla_20 = FontLoader.loadFont(fontPath+"FjallaOne-Regular.ttf", 20);
        tagess_20 = FontLoader.loadFont(fontPath+"Tagesschrift-Regular.ttf", 20);
        arial_20 = new Font("Arial", Font.BOLD, 20);
        arial_40 = new Font("Arial", Font.BOLD, 40);
        msg = "";
    }
    public Ui(String msg) {
        this();
        this.msg = msg;
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        // Other UI elements if needed in future
    }

    public void drawPauseScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 60));
        String text = "PAUSED";
        int y = gp.getHeight() / 2 - 110;
        int x = getXforCenteredText(text, g2);
        g2.drawString(text, x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String subtext = "Press ESC to resume";
        int subX = getXforCenteredText(subtext, g2);
        g2.drawString(subtext, subX, y + 25);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        for (int i = 0; i < pauseOptions.length; i++) {
            int X = gp.windWidth / 2 - 50;
            int Y = gp.windHeight / 2 + i * 40;
            if (i == selectedOption) {
                g2.setColor(Color.YELLOW);
                g2.drawString("→ " + pauseOptions[i], X, Y);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString("   " + pauseOptions[i], X, Y);
            }
        }
    }
    public void handlePauseInput() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastInputTime >= inputCooldown) {
            if (kh.up) {
                selectedOption--;
                if (selectedOption < 0) selectedOption = pauseOptions.length - 1;
                lastInputTime = currentTime;  // Update the last input time
            } else if (kh.down) {
                selectedOption++;
                if (selectedOption >= pauseOptions.length) selectedOption = 0;
                lastInputTime = currentTime;  // Update the last input time
            } else if (kh.enter) {
                if (pauseOptions[selectedOption].equals("Resume")) {
                    GamePanel.gameState.setCurrentState(GameState.State.PLAY);
                } else if (pauseOptions[selectedOption].equals("Exit")) {
                    System.exit(0);  // Or change to a main menu state
                }
                else if (pauseOptions[selectedOption].equals("Save Game")) {
                    SaveManager.getInstance().saveGame();
                }
                else if (pauseOptions[selectedOption].equals("Load Game")) {
                    SaveManager.getInstance().loadGame();
                    GamePanel.gameState.setCurrentState(GameState.State.PLAY);
                }
                lastInputTime = currentTime;  // Update the last input time
            }
            
        }
    }

    protected void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(dialogBackColor);
        g2.fillRoundRect(x, y, width, height, 35 , 35);
        // g2.setStroke(new BasicStroke(5));
    }
    public int getXforCenteredText(String text,Graphics2D g2)
    {
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.getWidth() / 2 - textWidth / 2;
        return x;
    }

}
