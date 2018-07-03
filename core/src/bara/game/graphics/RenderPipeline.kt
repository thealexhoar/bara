package bara.game.graphics

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.collections.GdxArray

class RenderPipeline (
    private var screenWidth: Int,
    private var screenHeight: Int
) {
    private val stages = GdxArray<RenderStage>()
    private val nullCamera = OrthographicCamera(1f, 1f)

    init {
        nullCamera.position.set(0.5f, 0.5f, 0f)
        nullCamera.update()
    }

    fun append(stage: RenderStage) {
        stages.add(stage)
    }

    fun insert(index: Int, stage: RenderStage) {
        stages.insert(index, stage)
    }

    fun render(spriteBatch: SpriteBatch) {
        spriteBatch.projectionMatrix = nullCamera.combined
        spriteBatch.begin()
        for (i in 0 until stages.size) {
            stages[i].render(spriteBatch)
        }
        spriteBatch.end()
    }

    fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
        for (i in 0 until stages.size) {
            stages.get(i).resize(width, height)
        }
    }

    fun dispose() {
        stages.forEach { stage ->
            stage.dispose()
        }
    }

    fun getEntitySystems() : List<EntitySystem> {
        return stages
            .filter { stage -> stage is EntitySystem }
            .map { stage -> stage as EntitySystem }
    }
}
