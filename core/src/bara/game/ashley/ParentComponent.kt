package bara.game.ashley

import com.badlogic.ashley.core.Entity
import bara.game.ashley.EngineEntity

// WARNING: do NOT implement parent/child cycles
// WARNING: ALWAYS add both PARENT and CHILD components to define relationship
class ParentComponent : GameComponent() {
    var parent: Entity? = null

    override fun reset() {
        parent = null
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<ParentComponent>()
    }
}

class ProtoParentComponent : GameComponent() {
    var parent: ProtoEntity? = null

    override fun reset() {
        parent = null
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<ParentComponent>()
    }
}