package bara.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Pool

class SpriteComponent : Component, Pool.Poolable {
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
}