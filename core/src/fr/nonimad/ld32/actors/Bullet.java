package fr.nonimad.ld32.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import fr.nonimad.ld32.Assets;
import fr.nonimad.ld32.LudumDare32;
import fr.nonimad.ld32.ScreenInGame;

public class Bullet extends Actor {
    public BulletType bulletType;
    public int colorId;
    public TextureRegion texture;
    
    public Rectangle aabb;
    
    public Bullet() {
        this(BulletType.ROUND, 0);
    }
    
    public Bullet(BulletType bt, int cid) {
        bulletType = bt;
        colorId = cid;
        texture = Assets.ta_bullets.get(bt.textureId * 3 + colorId);
    }
    
    @Override
    public void act(float delta) {
        this.moveBy(500*delta, 0);
        if(this.getX() > LudumDare32.WIDTH) {
            this.remove();
        }
        
        for(Enemy e : ScreenInGame.enemies) {
            if(e.aabb.overlaps(this.aabb)) {
                e.hurt(this.bulletType.damage);
                this.remove();
                break;
            }
        }
    }
    
    @Override
    protected void positionChanged() {
        aabb = new Rectangle(this.getX(), this.getY(), texture.getRegionWidth() * 2, texture.getRegionHeight() * 2);
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY(), texture.getRegionWidth() * 2, texture.getRegionHeight() * 2);
    }
}
