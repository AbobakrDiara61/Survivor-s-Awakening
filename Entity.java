import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Graphics2D;

public class Entity {
    public int x, y, speed;
    public int height;
    public int width;
    protected BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;
    private int spriteCounter = 0;
    protected int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn;

    public Entity() {
        solidArea = new Rectangle(8, 16, 32, 32);
        width = GamePanel.tileSize;
        height = GamePanel.tileSize;
    }
/*     public void move() {

    }
    public void update() {
        move();
        
    } */
    public void setDefault(int x,int y, int speed, String direction ){
        this.x = x * GamePanel.tileSize;
        this.y = y * GamePanel.tileSize;
        this.speed = speed;
        this.direction = direction;
    }
    protected BufferedImage getCurrentImage() {
        switch (direction) {
            case "up": return spriteNum == 1 ? up1 : up2;
            case "down": return spriteNum == 1 ? down1 : down2;
            case "left": return spriteNum == 1 ? left1 : left2;
            case "right": return spriteNum == 1 ? right1 : right2;
            default: return null;
        }
    }
    public void updateSpriteAnimation() {
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }
    public void getImages(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "=");
                
                if (tokenizer.countTokens() != 2) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                String spriteName = tokenizer.nextToken();
                String imagePath = tokenizer.nextToken();

                // Load image and assign to the corresponding field
                try {
                    BufferedImage image = ImageIO.read(new File(imagePath));
                    switch (spriteName.toLowerCase()) {
                        case "up1":
                            up1 = image;
                            break;
                        case "up2":
                            up2 = image;
                            break;
                        case "down1":
                            down1 = image;
                            break;
                        case "down2":
                            down2 = image;
                            break;
                        case "right1":
                            right1 = image;
                            break;
                        case "right2":
                            right2 = image;
                            break;
                        case "left1":
                            left1 = image;
                            break;
                        case "left2":
                            left2 = image;
                            break;
                        default:
                            System.err.println("Unknown sprite name: " + spriteName);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to load image: " + imagePath);
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Sprite paths file not found: " + file.getAbsolutePath());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        } 
    }
    public void draw(Graphics2D g2){ 
        int screenX = x - GamePanel.player.x + GamePanel.player.screenX;
        int screenY = y - GamePanel.player.y + GamePanel.player.screenY;
        int horizontal = screenX;
        int vertical = screenY;
        if(screenX > x) {
            horizontal = x;
        }
        if(screenY > y) {
            vertical = y;
        }
        int rightLimit = GamePanel.windWidth -  GamePanel.player.screenX;
                    if(rightLimit > GamePanel.worldWidth - GamePanel.player.x) {
                        horizontal = GamePanel.windWidth - (GamePanel.worldWidth - x);
                    }
                    int bottomLimit = GamePanel.windHeight - GamePanel.player.screenY;
                    if(bottomLimit > GamePanel.worldHeight - GamePanel.player.y) {
                        vertical = GamePanel.windHeight - (GamePanel.worldHeight - y);
                    }
        g2.drawImage(getCurrentImage(),horizontal,vertical,width,height,null);
    }
}