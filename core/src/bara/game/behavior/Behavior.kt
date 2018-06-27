package bara.game.behavior

import com.badlogic.ashley.core.Entity

interface Behavior {
    fun run(deltaTime: Float, entity: Entity)
}
