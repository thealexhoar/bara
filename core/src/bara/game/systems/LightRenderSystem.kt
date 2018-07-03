package bara.game.systems

import bara.game.ashley.PooledResourceEngine
import com.badlogic.ashley.core.EntitySystem
import bara.game.graphics.RenderStage
import box2dLight.RayHandler
import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class LightRenderSystem(
    private var width: Int,
    private var height: Int
) : EntitySystem(), RenderStage {

    private lateinit var rayHandler: RayHandler
    private lateinit var gameCamera: OrthographicCamera


    override fun addedToEngine(engine: Engine) {
        if (engine is PooledResourceEngine) {
            rayHandler = engine.getResource<RayHandler>()!!
            rayHandler.setLightMapRendering(false)
            RayHandler.useDiffuseLight(true)       // enable or disable diffused lighting
            rayHandler.setBlur(true)          // enabled or disable blur
            rayHandler.setBlurNum(4)          // set number of gaussian blur passes
            rayHandler.setShadows(true)       // enable or disable shadow
            rayHandler.setCulling(true)       // enable or disable culling
            rayHandler.setAmbientLight(0.9f)  // set default ambient light
            gameCamera = engine.getResource<OrthographicCamera>()!!
        }
    }

    override fun update(deltaTime: Float) {
        rayHandler.setCombinedMatrix(gameCamera)
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        rayHandler.updateAndRender()
        rayHandler.update()
    }

    override fun render(batch: SpriteBatch) {
        batch.draw(
            rayHandler.lightMapTexture,
            0f, 1f,
            1f, -1f
        )
    }

    override fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height
        rayHandler.resizeFBO(width, height)
    }

    override fun dispose() {
    }
}
