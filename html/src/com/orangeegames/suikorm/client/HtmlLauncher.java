package com.orangeegames.suikorm.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.Gdx;
import com.orangeegames.suikorm.SuikodenRM;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                //return new GwtApplicationConfiguration(true);
                // Fixed size application:
                return new GwtApplicationConfiguration(1280, 720);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new SuikodenRM();
        }
}
