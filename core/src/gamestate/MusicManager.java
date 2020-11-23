package gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class MusicManager {

    Music music = null;
    String musicTrackName = null;

    public void playTrack(String musicTrack) {
        System.out.println(musicTrack);
        System.out.println(this.musicTrackName);
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
