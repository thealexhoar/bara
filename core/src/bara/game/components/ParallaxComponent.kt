package bara.game.components

import bara.game.ashley.GameComponent
import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import bara.game.ashley.EngineEntity

class ParallaxComponent : GameComponent() {
    var parallaxFactor = 0f

    override fun reset() {
        parallaxFactor = 0f
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        val tempComponent = this
        engineEntity.with<ParallaxComponent> {
            parallaxFactor = tempComponent.parallaxFactor
        }
    }
}
