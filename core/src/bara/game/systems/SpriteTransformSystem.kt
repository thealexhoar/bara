package bara.game.systems

import bara.game.components.PositionComponent
import bara.game.components.SpriteComponent
import bara.game.util.ComponentLookup
import bara.game.util.radiansToDegrees
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem

class SpriteTransformSystem() : IteratingSystem(
    Family.all(SpriteComponent::class.java, PositionComponent::class.java).get()
) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val positionComponent = ComponentLookup.position(entity)
        val spriteComponent = ComponentLookup.sprite(entity)

        spriteComponent.sprite.setPosition(
            positionComponent.x + spriteComponent.offsetX,
            positionComponent.y + spriteComponent.offsetY
        )
        spriteComponent.sprite.rotation =
            radiansToDegrees(positionComponent.theta +spriteComponent.angleRad)
    }
}