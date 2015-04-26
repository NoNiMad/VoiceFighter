package fr.nonimad.ld32;

import com.badlogic.gdx.Game;

public class LudumDare32 extends Game {
    public static final String TITLE = "Voice Fighter"; 
    public static final int WIDTH = 1024, HEIGHT = 768;
    public static boolean debugMode = false;
    
    @Override
    public void create() {
        Assets.loadAssets();
        setScreen(new ScreenMainMenu());
    }
    
    @Override
    public void dispose() {
        Assets.dispose();
    }
}
