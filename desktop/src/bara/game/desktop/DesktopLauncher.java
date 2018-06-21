package bara.game.desktop;

import bara.game.MainGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = 800;
        config.height = 600;
        config.backgroundFPS = 30;
        config.foregroundFPS = 60;
        config.vSyncEnabled = true;
        config.useGL30 = true;
        //config.resizable = false;
        config.samples = 8;

        new LwjglApplication(new MainGame(800, 600), config);
    }
}
