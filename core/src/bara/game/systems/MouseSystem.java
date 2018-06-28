package bara.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import bara.game.components.MouseComponent;
import bara.game.components.PositionComponent;
import bara.game.util.Blackboard;
import bara.game.util.ComponentLookup;
import bara.game.util.InputManager;

public class MouseSystem extends EntitySystem {
    private ImmutableArray<Entity> _entities;

    public MouseSystem() {

    }

    @Override
    public void addedToEngine(Engine engine) {
        _entities = engine.getEntitiesFor(Family.all(PositionComponent.class, MouseComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        if(_entities.size() > 0) {
            Entity mouseEntity = _entities.first();
            PositionComponent positionComponent = ComponentLookup.INSTANCE.position(mouseEntity);
            PositionComponent playerPosition = ComponentLookup.INSTANCE.position(Blackboard.player);

            Vector2 delta = InputManager.getWorldMouseDelta();

            Vector2 diff = new Vector2(
                positionComponent.getX() - playerPosition.getX(),
                positionComponent.getY() - playerPosition.getY()
            );

            diff.add(delta);
            if(diff.len() > Blackboard.aimRadius) {
                diff.setLength(Blackboard.aimRadius);
            }

            positionComponent.setX(playerPosition.getX() + diff.x);
            positionComponent.setY(playerPosition.getY() + diff.y);

            InputManager.setWorldMousePos(new Vector2(positionComponent.getX(), positionComponent.getY()));
        }
    }
}
