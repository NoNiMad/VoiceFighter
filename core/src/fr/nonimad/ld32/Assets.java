package fr.nonimad.ld32;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.VisUI;

public class Assets {
    public static Texture t_ldLogo;
    public static Texture t_no_texture;
    public static Texture t_background;

    public static Array<AtlasRegion> ta_scorpion_stay;
    public static Array<AtlasRegion> ta_scorpion_fire;
    public static Array<AtlasRegion> ta_zombie;
    public static Array<AtlasRegion> ta_tadmerde;
    public static Array<AtlasRegion> ta_robot;
    
    public static Array<AtlasRegion> ta_bullets;
    public static Array<AtlasRegion> ta_bullet_electric;
    
    public static BitmapFont f_text;
    public static BitmapFont f_titles;
    public static BitmapFont f_mainTitle;

    public static void loadAssets()
    {
        VisUI.load();
        
        t_ldLogo = newTexture("LDLogo2015.png");
        t_no_texture = newTexture("no_texture.jpg");
        t_background = newTexture("background.jpg");
        
        ta_scorpion_stay = new TextureAtlas(Gdx.files.internal("chars/scorpion_stay.pack")).getRegions();
        ta_scorpion_fire = new TextureAtlas(Gdx.files.internal("chars/scorpion_fire.pack")).getRegions();
        ta_zombie = new TextureAtlas(Gdx.files.internal("chars/zombie.pack")).getRegions();
        ta_tadmerde = new TextureAtlas(Gdx.files.internal("chars/tadmerde.pack")).getRegions();
        ta_robot = new TextureAtlas(Gdx.files.internal("chars/robot.pack")).getRegions();
        
        ta_bullets = new TextureAtlas(Gdx.files.internal("bullets/bullets.pack")).getRegions();
        ta_bullet_electric = new TextureAtlas(Gdx.files.internal("bullets/bullet_electric.pack")).getRegions();
        
        f_text = newFont("Music_Warrior_black_18");
        f_titles = newFont("Music_Warrior_black_48");
        f_mainTitle = newFont("Music_Warrior_blackTOgray_96");

    }
    
    private static Texture newTexture(String path)
    {
        return new Texture(Gdx.files.internal(path));
    }
    
    private static BitmapFont newFont(String name)
    {
        return new BitmapFont(Gdx.files.internal("fonts/" + name + ".fnt"), Gdx.files.internal("fonts/" + name + ".png"), false);
    }

    public static void dispose() {
        for(Field field : Assets.class.getFields()) {
            if(doesClassImplements(field.getType(), Disposable.class)) {
                Method m;
                try {
                    m = field.getType().getDeclaredMethod("dispose");
                    m.invoke(field.get(null));
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static boolean doesClassImplements(Class<?> c, Class<?> i) {
        if(Arrays.asList(c.getInterfaces()).contains(i)) {
            return true;
        } else {
            if(c.getSuperclass() != Object.class)
                return doesClassImplements(c.getSuperclass(), i);
        }
        return false;
    }
}
