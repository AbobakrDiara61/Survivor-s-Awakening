import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AudioLoader { 
    private enum FilePath {
        VOICE_OVER_DANIEL, VOICE_OVER_KALRA, AUDIO
    }
    public HashMap<AudioID, String> getAudio() {
        return loadFiles(FilePath.AUDIO);
    }
    public ArrayList<String> getDanielVoiceOvers() {
        return loadVoiceOverFiles(FilePath.VOICE_OVER_DANIEL);
    }
    public ArrayList<String> getKalraVoiceOvers() {
        return loadVoiceOverFiles(FilePath.VOICE_OVER_KALRA);
    }

    private String getFilePath(FilePath path) {
        String mainPath = "assets/sound/text_files/";
        switch (path) {
            case VOICE_OVER_DANIEL:
                return mainPath + "voice_over_daniel.txt";
            case VOICE_OVER_KALRA:
                return mainPath + "voice_over_kalra.txt";
            case AUDIO:
                return mainPath + "audio.txt";
            default:
                return null;
        }
    }
    private HashMap<AudioID, String> loadFiles(FilePath path) {
        HashMap<AudioID, String> clipMap = new HashMap<>();
        Scanner scanner = null;
        try{
            scanner = new Scanner(new File( getFilePath(path) ) );
            while ( scanner.hasNextLine() ) {
                String line = scanner.nextLine();
                StringTokenizer stringTokenizer = new StringTokenizer( line );
                if(stringTokenizer.countTokens() != 2) { 
                    System.err.println("Error in Line: " + line + " in file Path " + getFilePath(path));
                    return null;
                }
                String soundPath = stringTokenizer.nextToken();
                String audioName = stringTokenizer.nextToken();
                try {
                    AudioID audioID = AudioID.valueOf(audioName);
                    clipMap.put(audioID, soundPath);
                } catch(IllegalArgumentException e) {
                    System.err.println("Error Name of AudioID in line " + line + " File Path " + getFilePath(path));
                } 
            }
        } catch(IOException e) {
            System.err.println("Error loading file: " + getFilePath(path));
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }
        return clipMap;
    }
    
    private ArrayList<String> loadVoiceOverFiles(FilePath path) {
        ArrayList<String> voiceOverList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(getFilePath(path)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                voiceOverList.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error loading file: " + getFilePath(path));
            e.printStackTrace();
        }
        return voiceOverList;
    }
}
