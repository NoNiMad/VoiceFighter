package fr.nonimad.ld32.actors;

public enum BulletType {
    ARROW(300, 1),
    COMMET(200, 0),
    ROUND(50, 2),
    SEED(100, 3),
    ELECTRIC(150, 0);
    
    int damage;
    int textureId;
    
    private BulletType(int d, int tId) {
        damage = d;
        textureId = tId;
    }
}
