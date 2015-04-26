package fr.nonimad.ld32.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

import fr.nonimad.ld32.Assets;
import fr.nonimad.ld32.LudumDare32;
import fr.nonimad.ld32.ScreenInGame;

public class BulletElectric extends Bullet {
    private Animation bulletAnimation;
    private float bulletTime;
    
    public BulletElectric() {
        super(BulletType.ELECTRIC, 0);
        bulletAnimation = new Animation(1/3f, Assets.ta_bullet_electric);
        bulletTime = 0;
    }
    
    @Override
    public void act(float delta) {
        this.moveBy(500*delta, 0);
        if(this.getX() > LudumDare32.WIDTH / 2) {
            for(Enemy e : ScreenInGame.enemies) {
                e.hurt(this.bulletType.damage);
            }
            
            this.remove();
        }

        bulletTime += delta;
        if(bulletTime > 1)
            bulletTime = 0;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(bulletAnimation.getKeyFrame(bulletTime), this.getX(), this.getY());
    }
}
