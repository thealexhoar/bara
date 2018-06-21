package bara.game.construction;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import bara.game.components.PositionComponent;

public class MouseConstruction {
    public static Entity construtMouse(PooledEngine engine, float x, float y, boolean addToEngine) {
        Entity mouse = engine.createEntity();

        //PositionComponent positionComponent =

        if(addToEngine) {
            engine.addEntity(mouse);
        }
        return mouse;
    }
}
