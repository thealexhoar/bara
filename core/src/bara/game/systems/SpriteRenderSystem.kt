package bara.game.systems

import bara.game.ashley.PooledResourceEngine
import bara.game.components.SpriteComponent
import bara.game.graphics.RenderStage
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import bara.game.util.ComponentLookup
import bara.game.util.LayerComparator
import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer

class SpriteRenderSystem (
    private var width: Int,
    private var height: Int
) :
    SortedIteratingSystem(
        Family.all(
            SpriteComponent::class.java
        ).get(),
        LayerComparator()
    ),
    RenderStage
{

    private val deltaTime = 0f
    private lateinit var frameBuffer: FrameBuffer
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var worldCamera: OrthographicCamera

    //TODO: implement framebuffer

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        if (engine is PooledResourceEngine) {
            worldCamera = engine.getResource<OrthographicCamera>()!!
            spriteBatch = engine.getResource<SpriteBatch>()!!
        }
    }

    override fun update(deltaTime: Float) {
        frameBuffer.begin()
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        spriteBatch.projectionMatrix = worldCamera.combined
        spriteBatch.begin()
        super.update(this.deltaTime)
        spriteBatch.end()
        frameBuffer.end()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val spriteComponent = ComponentLookup.sprite(entity)!!
        spriteComponent.sprite.draw(spriteBatch)
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
        frameBuffer = FrameBuffer(
            Pixmap.Format.RGBA8888,
            this.width,
            this.height,
            false
        )
    }

    override fun dispose() {
        frameBuffer.dispose()
    }
}
