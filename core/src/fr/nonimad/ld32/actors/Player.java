package fr.nonimad.ld32.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.nonimad.ld32.Assets;
import fr.nonimad.ld32.LudumDare32;
import fr.nonimad.ld32.ScreenGameOver;

public class Player extends Actor {
    private int life;
    private int firePower;
    private int fireTempo;
    
    private Animation anim_stay;
    private float anim_stay_time;
    
    private boolean anim_fire_enabled;
    private Animation anim_fire;
    private float anim_fire_time;
    
    public Player() {
        life = 100;
        firePower = 1000;
        fireTempo = 0;
        
        anim_stay = new Animation(1/2f, Assets.ta_scorpion_stay);
        anim_stay_time = 0;
        
        anim_fire_enabled = false;
        anim_fire = new Animation(1/14f, Assets.ta_scorpion_fire);
        anim_fire_time = 0;
        
        this.setPosition(50, LudumDare32.HEIGHT / 2 - 64);
    }
    
    @Override
    public void act(float delta) {
        if(this.life <= 0) {
            ((LudumDare32)Gdx.app.getApplicationListener()).setScreen(new ScreenGameOver());
        }
        
        if(anim_fire_enabled) {
            anim_fire_time += Gdx.graphics.getDeltaTime();
            
            if(anim_fire_time > 1) {
                anim_fire_enabled = false;
                anim_fire_time = 0;
            }
        } else {
            anim_stay_time += Gdx.graphics.getDeltaTime();
            if(anim_stay_time > 1) {
                anim_stay_time = 0;
            }
        }
        
        if(fireTempo > 0)
            fireTempo--;
        
        if(firePower < 1000)
            firePower += delta * 200;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(anim_fire_enabled) {
            batch.draw(anim_fire.getKeyFrame(anim_fire_time), 50, this.getY(), 128, 128);
        } else {
            batch.draw(anim_stay.getKeyFrame(anim_stay_time), 50, this.getY(), 128, 128);
        }
    }

    public void hurt(int damage) {
        this.life -= damage;
    }
    
    public void fire(Bullet b) {
        if(!anim_fire_enabled) {
            anim_fire_enabled = true;
            anim_fire_time = 0;
            anim_stay_time = 0;
        }
        
        firePower -= b.bulletType.damage;
        if(firePower < 0)
            firePower = 0;
        fireTempo = 30;
    }

    public boolean canFire(Bullet b) {
        if(firePower > b.bulletType.damage && fireTempo == 0)
            return true;
        return false;
    }
    
    public int getLife() {
        return this.life;
    }
    
    public float getFirePower() {
        return this.firePower;
    }
}
