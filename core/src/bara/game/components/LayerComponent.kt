package bara.game.components

import bara.game.ashley.GameComponent
import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.EngineEntity

class LayerComponent : GameComponent() {
    var layer = 0

    override fun reset() {
        layer = 0
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        val tempComponent = this
        engineEntity.with<LayerComponent> {
            layer = tempComponent.layer
        }
    }
}
