package utilities;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyPacker {
        public static void main (String[] args) throws Exception {
                TexturePacker.process("/mnt/teradactyl/src/SuikodenRM/core/sprites", "/mnt/teradactyl/src/SuikodenRM/core/sprites/atlas", "atlas");
        }
}
