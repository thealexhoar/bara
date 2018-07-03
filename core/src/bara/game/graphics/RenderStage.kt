package bara.game.graphics

import com.badlogic.gdx.graphics.g2d.SpriteBatch

interface RenderStage {
    fun render(batch: SpriteBatch)
    fun resize(width: Int, height: Int)
    fun dispose()
}
