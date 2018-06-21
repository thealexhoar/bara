package bara.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import bara.game.components.CameraComponent;
import bara.game.components.PositionComponent;
import bara.game.util.ComponentLookup;

public class CameraSystem extends EntitySystem {
    private ImmutableArray<Entity> _entities;
    private OrthographicCamera _camera;

    public CameraSystem(OrthographicCamera camera) {
        _camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        _entities = engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if (_entities.size() > 0) {
            Entity cameraEntity = _entities.first();
            CameraComponent cameraComponent = ComponentLookup.camera(cameraEntity);
            PositionComponent positionComponent = ComponentLookup.position(cameraEntity);

            _camera.position.set(positionComponent.x, positionComponent.y, _camera.position.z);
            _camera.update();

            //TODO 11/7/17: add lerping to new position
            //TODO 11/7/17: add effects like screenshake
        }
    }
}
