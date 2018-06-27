package bara.game.behavior

import com.badlogic.ashley.core.Entity

class TreeBehavior(var behaviorNode: BehaviorNode) : Behavior {

    override fun run(deltaTime: Float, entity: Entity) {
        this.behaviorNode.run(deltaTime, entity)
    }
}
