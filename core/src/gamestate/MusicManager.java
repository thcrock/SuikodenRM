package gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class MusicManager {

    Music music = null;
    String musicTrackName = null;

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
        }
    }
}
