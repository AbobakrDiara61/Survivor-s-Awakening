import java.awt.Graphics2D;
import java.util.ArrayList;

public class ObjectManager {
    private static ObjectManager instance = null;

    private ArrayList<SuperObject> objects;

    private ObjectManager() {
        objects = new ArrayList<>();
        // Initialize with a sample object (e.g., LetterObj)
        addObject(new LetterObj());
        addObject(new BoatObj());
        // addObject(new());
    }

    // Thread-safe Singleton getInstance method
    public static ObjectManager getInstance() {
        if (instance == null) {
            instance = new ObjectManager();
        }
        return instance;
    }

    // Method to add a SuperObject to the ArrayLst
    public void addObject(SuperObject obj) {
        objects.add(obj);
    }

    // Method to draw all objects
    public void draw(Graphics2D g2, Player player) {
        for (SuperObject obj : objects) {
            if(obj != null) {
                obj.draw(g2,player);
            }
        }
    }
    public ArrayList<SuperObject> getObjList() {
        return objects;
    }
}