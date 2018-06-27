package bara.game.behavior

import com.badlogic.ashley.core.Entity

abstract class BehaviorNode {
    fun run(deltaTime: Float, entity: Entity): BehaviorResult {
        return BehaviorResult.SUCCESS
    }
}
