import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.GraphicsEnvironment;

public class FontLoader {
    public static Font loadFont(String fontPath, float size) {
        Font font = null;
        try {
            InputStream inputStream = FontLoader.class.getResourceAsStream(fontPath);
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = font.deriveFont(size); // set size
            // registers your custom font with the system-wide graphics environment
            // use it in components like JLabel, JButton
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        }
        catch(FontFormatException e) {
            e.printStackTrace();
        } 
        catch(IOException e) {
            System.err.println("Error Loading the font in path " + fontPath);
            e.printStackTrace();  
        }
        // If we get here, loading failed → return fallback font
        return new Font("Arial", Font.PLAIN, 20);
    }
}
