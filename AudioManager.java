import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

public class AudioManager {
    private static AudioManager instance;
    private AudioLoader soundLoader;
    private Map<AudioID, String> audioMap;
    private ArrayList<String> voiceOverDanielList;
    private ArrayList<String> voiceOverKalraList;
    private Map<AudioID, Clip> clipCache;
// Stores file paths
    private AudioManager() {
        soundLoader = new AudioLoader();
        audioMap = soundLoader.getAudio();
        voiceOverDanielList = soundLoader.getDanielVoiceOvers();
        voiceOverKalraList = soundLoader.getKalraVoiceOvers();
        clipCache = new HashMap<>();
    }
    public static AudioManager getInstance() {
        if(instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    public Clip loadClip(AudioID audioID) {
        if (clipCache.containsKey(audioID)) {
            return clipCache.get(audioID);
        }

        String filePath = getFilePath(audioID);
        if (filePath == null) {
            System.err.println("No file path found for AudioID: " + audioID);
            return null;
        }
        Clip clip = loadClipFromPath(filePath);
        if (clip != null) {
            clipCache.put(audioID, clip);   
        }
        return clip;
        
    }
    public Clip loadClipFromPath(String filePath) {
        try {
            File file = new File(filePath).getAbsoluteFile();
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file format  at " + filePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading audio file at " + filePath);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable  at " + filePath);
            e.printStackTrace();
        }
        return null;
    }
    private String getFilePath(AudioID audioID) {
        if (audioMap != null && audioMap.containsKey(audioID)) {
            return audioMap.get(audioID);
        }
        return null;
    }
    public ArrayList<String> getDanielVoiceOvers() {
        return voiceOverDanielList;
    }

    public ArrayList<String> getKalraVoiceOvers() {
        return voiceOverKalraList;
    }
}
