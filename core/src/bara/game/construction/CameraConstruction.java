package bara.game.construction;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import bara.game.components.CameraComponent;
import bara.game.components.PositionComponent;

public class CameraConstruction {

    public static Entity constructCamera(PooledEngine engine, float x, float y, boolean addToEngine) {
        Entity camera = engine.createEntity();

        PositionComponent positionComponent = engine.createComponent(PositionComponent.class);
        positionComponent.setX(x);
        positionComponent.setY(y);
        positionComponent.setTheta(0);
        camera.add(positionComponent);

        CameraComponent cameraComponent = engine.createComponent(CameraComponent.class);
        camera.add(cameraComponent);

        if(addToEngine) {
            engine.addEntity(camera);
        }

        return camera;
    }

}
