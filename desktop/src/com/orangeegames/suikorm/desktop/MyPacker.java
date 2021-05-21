package com.orangeegames.suikorm.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import java.lang.Runtime;
import java.io.InputStream;

public class MyPacker {
        public static void main (String[] args) throws Exception {
                TexturePacker.process("/mnt/teradactyl/src/SuikodenRM/core/sprites", "/mnt/teradactyl/src/SuikodenRM/core/sprites/atlas", "atlas");
        }
}
