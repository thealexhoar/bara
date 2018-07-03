package bara.game.ashley

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import bara.game.ashley.EngineEntity

abstract class GameComponent : Component, Pool.Poolable {
    abstract fun buildOnto(engineEntity: EngineEntity)
}