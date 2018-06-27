package bara.game.ashley

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import ktx.ashley.entity
import ktx.collections.GdxArray


class ProtoEntity {
    val components = GdxArray<GameComponent>()

    fun with(component: GameComponent): ProtoEntity {
        components.add(component)
        return this
    }

    fun build(engine: PooledEngine): Entity {
        val entity = engine.entity {
            components.forEach { component ->
                component.buildOnto(this)
            }
        }
        return entity
    }

}