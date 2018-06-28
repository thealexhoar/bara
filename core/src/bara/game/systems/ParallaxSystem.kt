package bara.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import bara.game.components.ParallaxComponent

class ParallaxSystem :
    IteratingSystem(Family.all(ParallaxComponent::class.java).get())
{
    //TODO: implement
    override fun processEntity(entity: Entity, deltaTime: Float) {

    }
}
