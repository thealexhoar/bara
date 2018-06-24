package bara.game.construction

import bara.game.components.PlayerComponent
import bara.game.components.PositionComponent
import bara.game.components.SpriteComponent
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import ktx.ashley.entity


val PLAYER_SIZE = 3.0f

val PLAYER_COLLISION_FIXTURE = "collision"


fun constructPlayer(engine: PooledEngine, x: Float, y: Float, addToEngine: Boolean): Entity {
    val player = engine.entity {
        with<PlayerComponent>()
        with<PositionComponent>()
        with<SpriteComponent>()
    }


    if (addToEngine) {
        engine.addEntity(player)
    }

    return player
}