package bara.game.components

import bara.game.ashley.GameComponent
import bara.game.ashley.EngineEntity

class AnimationComponent : GameComponent() {
    //TODO: implement data

    override fun reset() {

    }

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<AnimationComponent>()
    }
}


