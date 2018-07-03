package bara.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import bara.game.components.BehaviorComponent
import bara.game.util.ComponentLookup

class BehaviorSystem :
    IteratingSystem(Family.all(BehaviorComponent::class.java).get())
{

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val behaviorComponent = ComponentLookup.behavior(entity)
        behaviorComponent?.behavior?.run(deltaTime, entity)
    }
}
