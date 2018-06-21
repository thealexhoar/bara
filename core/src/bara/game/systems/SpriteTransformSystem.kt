package bara.game.systems

import bara.game.components.PositionComponent
import bara.game.components.SpriteComponent
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem

class SpriteTransformSystem() : IteratingSystem(
    Family.all(SpriteComponent::class.java, PositionComponent::class.java).get()
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        
    }
}