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
import bara.game.input.InputManager;

public class MouseSystem extends EntitySystem {
    private ImmutableArray<Entity> _entities;

    public MouseSystem() {

    }

    @Override
    public void addedToEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        //TODO: implement
    }
}
