package bara.game.components

import bara.game.ashley.GameComponent
import bara.game.behavior.Behavior
import bara.game.ashley.EngineEntity

class BehaviorComponent : GameComponent() {
    var behavior: Behavior? = null

    override fun reset() {
        behavior = null
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        val tempBehavior = behavior //temp to get around name shadowing
        engineEntity.with<BehaviorComponent> {
            behavior = tempBehavior
        }
    }

}
