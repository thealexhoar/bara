package bara.game.behavior;

import com.badlogic.ashley.core.Entity;

public abstract class BehaviorNode {
    BehaviorResult run(float deltaTime, Entity entity) {
        return BehaviorResult.SUCCESS;
    }
}
