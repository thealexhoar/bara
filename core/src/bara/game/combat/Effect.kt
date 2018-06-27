package bara.game.combat

import com.badlogic.ashley.core.Entity

interface Effect {
    fun applyTo(entity: Entity): Boolean
}
