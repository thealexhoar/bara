package bara.game.ashley

import bara.game.util.ComponentLookup
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import ktx.ashley.EngineEntity
import ktx.ashley.entity
import ktx.collections.GdxArray


// Extension functions on entity
fun Entity.createEntity(
    engine: PooledEngine,
    configure: (Entity) -> Unit = {}
): Entity {
    val entity = engine.entity {
        // Special cases for parent/children components
        components.forEach { component ->
            val gameComponent = (component as GameComponent)
            gameComponent.buildOnto(this)
            when (gameComponent) {
                is ChildrenComponent ->
                    handleChildrenComponent(this, gameComponent)
                is ProtoChildrenComponent ->
                    handleProtoChildrenComponent(this, gameComponent)
            }
        }
    }
    configure(entity)
    return entity
}

class ProtoEntity {
    val components = GdxArray<GameComponent>()

    fun with(component: GameComponent) : ProtoEntity {
        components.add(component)
        return this
    }

    fun createEntity(
        engine: PooledEngine,
        configure: (Entity) -> Unit = {}
    ): Entity {
        val entity = engine.entity {
            components.forEach { gameComponent ->
                gameComponent.buildOnto(this)
                when (gameComponent) {
                    is ChildrenComponent ->
                        handleChildrenComponent(this, gameComponent)
                    is ProtoChildrenComponent ->
                        handleProtoChildrenComponent(this, gameComponent)
                }
            }
        }
        configure(entity)
        return entity
    }
}

fun handleChildrenComponent(
    engineEntity: EngineEntity,
    component: ChildrenComponent
) {
    // clone all the children and add to new entity
    val newComponent = ComponentLookup.children(engineEntity.entity)
    for (entity in component.children) {
        val newChild = entity.createEntity(engineEntity.engine as PooledEngine)
        newComponent.children.add(newChild)
        ComponentLookup.parent(newChild).parent = engineEntity.entity
    }
}

fun handleProtoChildrenComponent(
    engineEntity: EngineEntity,
    component: ProtoChildrenComponent
) {
    // clone all the children and add to new entity
    val newComponent = ComponentLookup.children(engineEntity.entity)
    for (protoEntity in component.children) {
        val newChild =
            protoEntity.createEntity(engineEntity.engine as PooledEngine)
        newComponent.children.add(newChild)
        ComponentLookup.parent(newChild).parent = engineEntity.entity
    }
}