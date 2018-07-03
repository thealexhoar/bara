package bara.game.components

import bara.game.util.ComponentLookup
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem

class PhysicsTransformSystem() : IteratingSystem(
    Family.all(
        PositionComponent::class.java,
        PhysicsComponent::class.java
    ).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val positionComponent = ComponentLookup.position(entity)!!
        val physicsComponent = ComponentLookup.physics(entity)!!

        positionComponent.x = physicsComponent.body!!.position.x
        positionComponent.y = physicsComponent.body!!.position.y
        positionComponent.theta = physicsComponent.body!!.angle
    }
}