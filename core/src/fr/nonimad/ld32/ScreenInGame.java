package fr.nonimad.ld32;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;

import fr.nonimad.ld32.actors.Bullet;
import fr.nonimad.ld32.actors.BulletElectric;
import fr.nonimad.ld32.actors.BulletType;
import fr.nonimad.ld32.actors.Enemy;
import fr.nonimad.ld32.actors.Player;

public class ScreenInGame extends ScreenAdapter {
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private static Random spawnRandom = new Random();
    
    private Stage stage = new Stage();
    private Player player = new Player();
    
    private SoundAnalyser soundAnalyser = new SoundAnalyser();
    private ShapeRenderer sr = new ShapeRenderer();
    private SpriteBatch sb = new SpriteBatch();
    
    BitmapFont debugFont = new BitmapFont();
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        
        stage.addActor(player);
        
        TextButton btn_back = new TextButton("Return", VisUI.getSkin());
        btn_back.setPosition(LudumDare32.WIDTH - 110, LudumDare32.HEIGHT - 40);
        btn_back.setSize(100, 30);
        btn_back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((LudumDare32)Gdx.app.getApplicationListener()).setScreen(new ScreenMainMenu());
            }
        });
        stage.addActor(btn_back);
    }
    
    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.getY() > 64) {
            player.moveBy(0, -300*delta);
        } else if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.getY() < LudumDare32.HEIGHT - 300) {
            player.moveBy(0, 300*delta);
        }
        
        if(spawnRandom.nextInt(80) == 0) {
            Enemy e = new Enemy(player);
            enemies.add(e);
            stage.addActor(e);
        }
        
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        sb.begin();
        sb.draw(Assets.t_background, 0, 0, LudumDare32.WIDTH, LudumDare32.HEIGHT);
        sb.end();
        
        Period p = soundAnalyser.getLastPeriodIfValid();
        if(p != null) {
            if(LudumDare32.debugMode) {
                sb.begin();
                debugFont.draw(sb, "Amplitude : " + p.amplitude, 10, 40);
                debugFont.draw(sb, "Duration : " + p.duration, 10, 20);
                sb.end();
            }
            
            if(p.amplitude >= 5 && p.duration >= 3) {
                Bullet b = newBullet(p);
                
                if(b != null && player.canFire(b)) {
                    if(LudumDare32.debugMode) {
                        System.out.println("Amplitude : " + p.amplitude);
                        System.out.println("Duration : " + p.duration);
                    }
                    
                    stage.addActor(b);
                    player.fire(b);
                }
            }
        }
        
        sr.begin(ShapeType.Filled);
        sr.setColor(Color.BLACK);
        sr.rect(129, LudumDare32.HEIGHT - 221, 202, 17);
        sr.rect(129, LudumDare32.HEIGHT - 241, 202, 17);
        sr.setColor(Color.RED);
        sr.rect(130, LudumDare32.HEIGHT - 220, (int)(200 * player.getLife() / 100), 15);
        sr.setColor(Color.BLUE);
        sr.rect(130, LudumDare32.HEIGHT - 240, (int)(200 * player.getFirePower() / 1000), 15);
        sr.end();
        
        sb.begin();
        Assets.f_text.draw(sb, "Health", 10, LudumDare32.HEIGHT - 200);
        Assets.f_text.draw(sb, "Fire Power", 10, LudumDare32.HEIGHT - 220);
        sb.end();
        
        stage.act();
        stage.draw();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    private Bullet newBullet(Period p) {
        if(p.amplitude > 70) {
            BulletElectric be = new BulletElectric();
            be.setPosition(50 + 128, player.getY() + 64);
            return be;
        }
        
        BulletType bt = null;
        int colorId = -1;
        
        if(isInInterval(p.amplitude, 5, 15)) {
            bt = BulletType.ROUND;
        } else if(isInInterval(p.amplitude, 16, 35)) {
            bt = BulletType.SEED;
        } else if(isInInterval(p.amplitude, 36, 50)) {
            bt = BulletType.COMMET;
        } else if(isInInterval(p.amplitude, 51, 70)) {
            bt = BulletType.ARROW;
        }
        
        if(isInInterval(p.duration, 3, 20)) {
            colorId = 0;
        } else if(isInInterval(p.duration, 21, 40)) {
            colorId = 1;
        } else if(isInInterval(p.duration, 41, 1000)) {
            colorId = 2;
        }
        
        if(bt == null || colorId < 0)
            return null;
        
        Bullet b = new Bullet(bt, colorId);
        b.setPosition(50 + 128, player.getY() + 64);
        return b;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        soundAnalyser.stopped = true;
        stage.dispose();
        sr.dispose();
        debugFont.dispose();
        sb.dispose();
    }
    
    private boolean isInInterval(int nb, int min, int max) {
        if(nb >= min && nb <= max)
            return true;
        
        return false;
    }
}
