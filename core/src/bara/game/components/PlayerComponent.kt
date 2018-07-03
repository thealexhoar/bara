package bara.game.components

import bara.game.ashley.GameComponent
import bara.game.ashley.EngineEntity

class PlayerComponent : GameComponent() {
    //TODO: implement?
    override fun reset() {}

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<PlayerComponent>()
    }

}
