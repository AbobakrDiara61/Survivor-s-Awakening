import java.util.ArrayList;

public class Dialog {
    private ArrayList<String> dialog;
    private ArrayList<String> voice;
    private int index; 
    private int voiceIndex;
    public Dialog() {
        dialog = new ArrayList<>();
        voice = new ArrayList<>();
        index = 0;
        voiceIndex = 0;
    }
    public ArrayList<String> getDialogueArray() {
        return dialog;
    }
    public void setVoice(ArrayList<String> voice) {
        this.voice = voice;
    }
    public String getVoice() {
        if(voiceIndex < dialog.size()) {
            return voice.get(voiceIndex++);
        }
        return "";
    } 
    public String getDialogue() {
        if(index < dialog.size()) {
            return dialog.get(index++);
        }
        return "";
    }
    public int getIndex() {
        return index;
    }
    public int getVoiceIndex() {
        return voiceIndex;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public void setVoiceIndex(int voiceIndex) {
        this.voiceIndex = voiceIndex;
    }
}
