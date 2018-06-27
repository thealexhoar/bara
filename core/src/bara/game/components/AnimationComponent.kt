package bara.game.components

import bara.game.ashley.GameComponent
import ktx.ashley.EngineEntity

class AnimationComponent : GameComponent() {
    //TODO: implement data

    override fun reset() {

    }

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<AnimationComponent>()
    }
}
