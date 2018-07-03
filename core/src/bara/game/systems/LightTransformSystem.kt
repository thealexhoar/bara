package bara.game.systems

import bara.game.components.LightComponent
import bara.game.components.PositionComponent
import bara.game.util.ComponentLookup
import bara.game.util.radiansToDegrees
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem

class LightTransformSystem :
    IteratingSystem(
        Family.all(
            PositionComponent::class.java,
            LightComponent::class.java
        ).get()
    )
{
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val positionComponent = ComponentLookup.position(entity)!!
        val lightComponent = ComponentLookup.light(entity)!!

        lightComponent.light?.setPosition(
            positionComponent.x,
            positionComponent.y
        )
        lightComponent.light?.setDirection(
            radiansToDegrees(positionComponent.theta)
        )
    }
}