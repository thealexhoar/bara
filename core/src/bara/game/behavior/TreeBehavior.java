package bara.game.behavior;

import com.badlogic.ashley.core.Entity;

public class TreeBehavior implements Behavior {
    public BehaviorNode behaviorNode;

    public TreeBehavior(BehaviorNode node) {
        behaviorNode = node;
    }

    @Override
    public void run(float deltaTime, Entity entity) {
        this.behaviorNode.run(deltaTime, entity);
    }
}
