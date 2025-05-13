public class LetterObj extends SuperObject{

    public LetterObj() {
        obj = SuperObject.OBJ.LETTER;
        name = obj.toString();
        getImage(obj);
        setCollision(obj);
        setPosition(32,36, WorldMap.MapType.CAVE);
    }
}
