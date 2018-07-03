package bara.game.components

import bara.game.ashley.GameComponent
import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import bara.game.ashley.EngineEntity

class PositionComponent : GameComponent() {
    var x = 0f
    var y = 0f
    var theta = 0f

    override fun reset() {
        x = 0f
        y = 0f
        theta = 0f
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        val tempComponent = this
        engineEntity.with<PositionComponent> {
            x = tempComponent.x
            y = tempComponent.y
            theta = tempComponent.theta
        }
    }

}
