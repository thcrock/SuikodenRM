package gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class MusicManager {

    Music music = null;
    String musicTrackName = null;
    String nextTrackName = null;
    float FACTOR = 0.5f;
    float volume = 1;
    boolean isPlaying = true;

    public void playTrack(String musicTrack) {
        if(musicTrack == null) {
            if(this.music != null) {
                this.music.stop();
            }
            return;
        }

        if(!musicTrack.equals(this.musicTrackName)) {
            if(this.music != null) {
                System.out.println("Stopping");
                this.music.stop();
            }
            this.musicTrackName = musicTrack;
            this.music = Gdx.audio.newMusic(Gdx.files.internal("music/" + musicTrack));
            this.music.setLooping(true);
            this.music.play();
            this.volume = 0;
            this.music.setVolume(volume);
            this.isPlaying = true;
        }
    }

    public void update(float delta) {
        if (this.isPlaying) {
            if (volume > 1) {
                return; 
            }
            volume += delta * FACTOR;

            this.music.setVolume(volume);
        }
    }
}
