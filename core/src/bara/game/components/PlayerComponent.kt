package bara.game.components

import bara.game.ashley.GameComponent
import ktx.ashley.EngineEntity

class PlayerComponent : GameComponent() {
    //TODO: implement?
    override fun reset() {}

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<PlayerComponent>()
    }

}
