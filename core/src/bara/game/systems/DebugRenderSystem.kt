package bara.game.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import bara.game.graphics.CustomBox2DRenderer
import bara.game.graphics.RenderStage
import bara.game.util.AssetManager
import ktx.collections.GdxArray


class DebugRenderSystem(
    width: Int,
    height: Int,
    private val spriteBatch: SpriteBatch,
    private val world: World,
    private val camera: OrthographicCamera
) : EntitySystem(), RenderStage {

    private var frameBuffer: FrameBuffer? = null
    private var width: Int = 0
    private var height: Int = 0
    private val nullCamera = OrthographicCamera(width.toFloat(), height.toFloat())
    private val debugRenderer = Box2DDebugRenderer(
        DRAW_BODIES,
        DRAW_JOINTS,
        DRAW_AABBS,
        DRAW_INACTIVE_BODIES,
        DRAW_VELOCITIES,
        DRAW_CONTACTS
    )
    private val customDebugRenderer = CustomBox2DRenderer(
        DRAW_BODIES,
        DRAW_JOINTS,
        DRAW_AABBS,
        DRAW_INACTIVE_BODIES,
        DRAW_VELOCITIES,
        DRAW_CONTACTS,
        DRAW_WIDTH
    )
    private val debugFont = AssetManager.getDefaultFont(16)
    private val frameTimeHistory = GdxArray<Float>(FPS_HISTORY_COUNT)
    private var historyIndex = 0

    init {
        resize(width, height)
        nullCamera.position.set((width / 2).toFloat(), (height / 2).toFloat(), 0f)
        nullCamera.update()
    }

    override fun update(deltaTime: Float) {
        if (frameTimeHistory.size < FPS_HISTORY_COUNT) {
            frameTimeHistory.add(Gdx.graphics.deltaTime)
        } else {
            frameTimeHistory.set(historyIndex, Gdx.graphics.deltaTime)
        }
        historyIndex++
        historyIndex %= FPS_HISTORY_COUNT


        var frameSum = 0f
        for (i in 0 until frameTimeHistory.size) {
            frameSum += frameTimeHistory.get(i)
        }
        val frameAverage = frameSum / FPS_HISTORY_COUNT

        frameBuffer!!.begin()
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        //debugRenderer.render(world, camera.combined);
        //customDebugRenderer.render(world, camera.combined);
        spriteBatch.projectionMatrix = nullCamera.combined
        spriteBatch.begin()
        val fpsString = Integer.toString(Math.round(1f / frameAverage))
        debugFont.draw(spriteBatch, fpsString, 10f, 20f)
        spriteBatch.end()
        frameBuffer!!.end()
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(
            frameBuffer!!.colorBufferTexture,
            0f, 1f,
            1f, -1f
        )

    }

    override fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height
        frameBuffer = FrameBuffer(Pixmap.Format.RGBA8888, this.width, this.height, false)
    }

    companion object {
        val DRAW_BODIES = true
        val DRAW_JOINTS = true
        val DRAW_AABBS = false
        val DRAW_INACTIVE_BODIES = true
        val DRAW_VELOCITIES = false
        val DRAW_CONTACTS = true
        val DRAW_WIDTH = 1f / 16

        private val FPS_HISTORY_COUNT = 15
    }

}
