package bara.game.construction;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import bara.game.combat.BulletDef;

public class BulletConstruction {
    public static Entity constructBullet(PooledEngine engine, BulletDef bulletDef, boolean addToEngine) {
        Entity bullet = engine.createEntity();



        if(addToEngine) {
            engine.addEntity(bullet);
        }
        return bullet;
    }
}
