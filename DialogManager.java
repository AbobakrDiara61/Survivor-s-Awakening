import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DialogManager extends Ui{
    private final File danielFile = new File("assets/gameTexts/daniel.txt");
    private final File karlaFile = new File("assets/gameTexts/karla.txt");
    private final File caveFile = new File("assets/gameTexts/cave_letter.txt");
    private Dialog danielDialog, karlaDialog;
    private String caveDialog;
    private Block subWindow;
    private GameState gameState;
    public AudioPlaybackManager audioPlaybackManager;
    private AudioManager audioManager;
    // number of current dialogues
    private int dialogCounter;
    public DialogManager() {
        audioManager = AudioManager.getInstance();
        audioPlaybackManager = AudioPlaybackManager.getInstance();
        gameState = GameState.getInstance();
        danielDialog = new Dialog();
        karlaDialog = new Dialog();
        danielDialog.setVoice(audioManager.getDanielVoiceOvers());
        karlaDialog.setVoice(audioManager.getKalraVoiceOvers());
        loadScenario();
        loadCaveLetter();
        // getDanielDialog();
        initSubWindow();
        dialogBackColor = new Color(0, 0, 0, 200);


    }    

    public void decreaseCounter() {
        this.dialogCounter--;
    }
    public void setDialogueCounter(int counter) {
        this.dialogCounter = counter;
    }
    public boolean hasMoreDialogue() {
        if(dialogCounter != 0) {
            return true;
        }
        return false;
    }
    private void initSubWindow() {
        int x = GamePanel.windWidth / 2 - GamePanel.tileSize * 15 / 2;
        int y = GamePanel.windHeight / 2 + GamePanel.tileSize * 2;
        int height = GamePanel.windHeight / 3;
        int width = 15 * GamePanel.tileSize;
        subWindow = new Block(x, y, width, height);
    }
    @Override
    public void draw(Graphics2D g2) {
        if(gameState.getCurrentState() == GameState.State.DIALOGUE) { 
            this.g2 = g2;
            g2.setFont(abril_20);
            int msgX = subWindow.x +50, msgY = subWindow.y + 50;
            if(visible) {
                drawSubWindow(subWindow.x, subWindow.y, subWindow.width, subWindow.height);
                g2.setColor(Color.WHITE);
                StringTokenizer tokenizer = new StringTokenizer(msg, "/");
                while (tokenizer.hasMoreTokens()) {
                    String line = tokenizer.nextToken();
                    g2.drawString(line, msgX, msgY);
                    msgY += 20;
                }
            }
/*             counter++;
            if(counter >120) {
                counter = 0;
                visible = false;
            } */
        }
    }
    public void getDanielDialog() {
        msg = danielDialog.getDialogue();
        audioPlaybackManager.play(danielDialog.getVoice(), false);
        // draw(g2);
    }
    public void getKarlaDialog() {
        msg = karlaDialog.getDialogue();
        // draw(g2);
    }
    public void getCaveLetter() {
        msg =  caveDialog;
        // draw(g2);
    }
    private void loadScenario() {
        Scanner input = null;
        String temp;
        try {
            input = new Scanner(danielFile);
            while (input.hasNextLine()) {
                temp = input.nextLine();
                danielDialog.getDialogueArray().add(temp);
            }
            input.close();
            input = new Scanner(karlaFile);
            while (input.hasNextLine()) {
                temp = input.nextLine();
                karlaDialog.getDialogueArray().add(temp);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            input.close();
        }
    }
    private void loadCaveLetter() {
        Scanner input = null;
        caveDialog = "";
        try {
            input = new Scanner(caveFile);
            while (input.hasNextLine()) {
                caveDialog += input.nextLine();
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            input.close();
        }
    }

    public int getDialogCounter() {
        return dialogCounter;
    }
    
    public int getDanielDialogIndex() {
        return danielDialog.getIndex();
    }
    public int getKarlaDialogIndex() {
        return karlaDialog.getIndex();
    }
    public void setDanielDialogIndex(int index) {
        danielDialog.setIndex(index);
    }
    public void setKarlaDialogIndex(int index) {
        karlaDialog.setIndex(index);
    }

    
}
