package bara.game.ashley

import com.badlogic.ashley.core.Entity
import ktx.ashley.EngineEntity
import ktx.collections.GdxArray

// WARNING: do NOT implement parent/child cycles
// WARNING: ALWAYS add both PARENT and CHILD components to define relationship
class ChildrenComponent : GameComponent() {
    var children = GdxArray<Entity>()

    override fun reset() {
        children.clear()
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<ChildrenComponent>()
    }
}

class ProtoChildrenComponent : GameComponent() {
    var children = GdxArray<ProtoEntity>()

    override fun reset() {
        children.clear()
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<ChildrenComponent>()
    }
}
