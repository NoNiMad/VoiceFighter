package fr.nonimad.ld32.actors;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.nonimad.ld32.Assets;
import fr.nonimad.ld32.LudumDare32;
import fr.nonimad.ld32.ScreenInGame;

public class Enemy extends Actor {
    private static Random enemyRand = new Random();
    private static ShapeRenderer sr = new ShapeRenderer();
    
    private int life;
    private int maxLife;
    
    private Animation anim;
    private float anim_time;
    
    public Rectangle aabb;
    
    private Player thePlayer;
    
    public Enemy(Player p) {
        this.thePlayer = p;
        
        switch (enemyRand.nextInt(3)) {
            case 0:
                anim = new Animation(1/2f, Assets.ta_zombie);
                maxLife = 100;
                break;
            case 1:
                anim = new Animation(1/2f, Assets.ta_tadmerde);
                maxLife = 200;
                break;
            case 2:
                anim = new Animation(1/2f, Assets.ta_robot);
                maxLife = 300;
            default:
                break;
        }
        
        life = maxLife;        
        anim_time = 0;
        
        this.setPosition(LudumDare32.WIDTH, enemyRand.nextFloat() * (LudumDare32.HEIGHT - 300 - 64) + 64);
    }
    
    @Override
    public void act(float delta) {
        if(this.isDead()) {
            ScreenInGame.enemies.remove(this);
            this.remove();
        }
        
        this.moveBy(-200*delta, 0);
        if(this.getX() < 50 + 128) {
            thePlayer.hurt(20);
            this.remove();
        }
        
        anim_time += delta;
        if(anim_time > 1)
            anim_time = 0;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(anim.getKeyFrame(anim_time), this.getX(), this.getY(), 64, 64);
        
        batch.end();
        sr.begin(ShapeType.Filled);
        sr.setColor(Color.RED);
        sr.rect(this.getX(), this.getY() - 10, 64 * this.life / maxLife, 5);
        sr.end();
        batch.begin();
    }
    
    @Override
    protected void positionChanged() {
        aabb = new Rectangle(this.getX(), this.getY(), 32, 32);
    }
    
    public void hurt(int damage) {
        life -= damage;
    }
    
    public boolean isDead() {
        if(life <= 0)
            return true;
        return false;
    }
}
