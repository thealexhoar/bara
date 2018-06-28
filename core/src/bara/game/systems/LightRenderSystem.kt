package bara.game.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import bara.game.graphics.RenderStage
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class LightRenderSystem(
    private var width: Int,
    private var height: Int
) : EntitySystem(), RenderStage {
    private var frameBuffer = FrameBuffer(
        Pixmap.Format.RGB888,
        width,
        height,
        false
    )

    override fun update(deltaTime: Float) {
        //TODO: implement
        //TODO: use dithering to fix color banding issue
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(
            frameBuffer.colorBufferTexture,
            0f, 1f,
            1f, -1f
        )
    }

    override fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height
        frameBuffer = FrameBuffer(Pixmap.Format.RGB888, width, height, false)
    }
}
