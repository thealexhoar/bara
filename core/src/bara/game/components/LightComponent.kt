package bara.game.components

import bara.game.ashley.GameComponent
import ktx.ashley.EngineEntity

class LightComponent : GameComponent() {
    //TODO: implement
    override fun reset() {}

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<LightComponent>()
    }

}
