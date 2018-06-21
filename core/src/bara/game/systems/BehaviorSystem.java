package bara.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import bara.game.components.BehaviorComponent;
import bara.game.util.ComponentLookup;

public class BehaviorSystem extends IteratingSystem {

    public BehaviorSystem() {
        super(Family.all(BehaviorComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BehaviorComponent behaviorComponent = ComponentLookup.behavior(entity);
        behaviorComponent.behavior.run(deltaTime, entity);
    }
}
