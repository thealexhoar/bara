package bara.game.systems

import bara.game.ashley.PooledResourceEngine
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
import bara.game.util.AssetHandler
import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.BitmapFont
import ktx.collections.GdxArray


class DebugRenderSystem (
    width: Int,
    height: Int
) : EntitySystem(), RenderStage {

    private var frameBuffer: FrameBuffer? = null
    private var width: Int = 0
    private var height: Int = 0
    private val debugRenderer = CustomBox2DRenderer(
        DRAW_BODIES,
        DRAW_JOINTS,
        DRAW_AABBS,
        DRAW_INACTIVE_BODIES,
        DRAW_VELOCITIES,
        DRAW_CONTACTS,
        DRAW_WIDTH
    )
    private val frameTimeHistory = GdxArray<Float>(FPS_HISTORY_COUNT)
    private var historyIndex = 0
    private val nullCamera = OrthographicCamera(800f, 600f)
    init {
        nullCamera.position.set(400f, 300f, 0f)
        nullCamera.update()
    }

    private lateinit var debugFont: BitmapFont
    private lateinit var world: World
    private lateinit var camera: OrthographicCamera
    private lateinit var spriteBatch: SpriteBatch


    override fun addedToEngine(engine: Engine?) {
        if (engine is PooledResourceEngine) {
            debugFont = engine
                .getResource<AssetHandler>()!!
                .getAsset<BitmapFont>("bitpotion")!!

            debugFont.data.scaleX = 2f
            debugFont.data.scaleY = 2f

            world = engine.getResource<World>()!!
            camera = engine.getResource<OrthographicCamera>()!!
            spriteBatch = engine.getResource<SpriteBatch>()!!
        }
    }

    override fun update(deltaTime: Float) {
        //fps counting
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

        //debug rendering
        frameBuffer!!.begin()
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        debugRenderer.render(world, camera.combined);
        spriteBatch.projectionMatrix = nullCamera.combined
        spriteBatch.begin()
        val fpsString = Integer.toString(Math.round(1f / frameAverage))
        debugFont.draw(spriteBatch, fpsString, 10f, 28f)
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

    override fun dispose() {
        frameBuffer?.dispose()
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
