package bara.game.components

import bara.game.ashley.EngineEntity
import bara.game.ashley.GameComponent
import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Pool

class SpriteComponent : GameComponent() {

    var sprite = Sprite()
    var offsetX = 0f
    var offsetY = 0f
    var angleRad = 0f

    override fun reset() {
        sprite = Sprite()
        offsetX = 0f
        offsetY = 0f
        angleRad = 0f
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        val temp = this
        engineEntity.with<SpriteComponent> {
            sprite.set(temp.sprite)
            offsetX = temp.offsetX
            offsetY = temp.offsetY
            angleRad = temp.angleRad
        }
    }
}