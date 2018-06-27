package bara.game.components

import bara.game.ashley.GameComponent
import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.EngineEntity

class CameraComponent : GameComponent() {

    override fun reset() {}

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<CameraComponent>()
    }

}
