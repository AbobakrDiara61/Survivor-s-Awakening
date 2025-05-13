import javax.sound.sampled.Clip;

public class AudioPlaybackManager {
    private static AudioPlaybackManager instance;
    private final AudioManager audioManager;
    private Clip currentClip;

    private AudioPlaybackManager() {
        audioManager = AudioManager.getInstance();
    }

    public static AudioPlaybackManager getInstance() {
        if (instance == null) {
            instance = new AudioPlaybackManager();
        }
        return instance;
    }

    public void play(AudioID audioID, boolean loop) {
        stop();
        currentClip = audioManager.loadClip(audioID);
        if (currentClip == null) {
            System.err.println("Failed to load clip for AudioID: " + audioID);
            return;
        }
        if (loop) {
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            currentClip.start();
        }
    }

    public void play(String AudioPath, boolean loop) {
        stop();
        currentClip = audioManager.loadClipFromPath(AudioPath);
        if (currentClip == null) {
            System.err.println("Failed to load clip for path: " + AudioPath);
            return;
        }
        if (loop) {
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            currentClip.start();
        }
    }

    public void stop() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.flush();
            currentClip.setFramePosition(0);
        }
    }

    public void pause() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

    public void resume() {
        if (currentClip != null && !currentClip.isRunning()) {
            currentClip.start();
        }
    }
}