package bara.game.systems

import bara.game.components.SpriteComponent
import bara.game.graphics.RenderStage
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import bara.game.components.PositionComponent
import bara.game.util.LayerComparator
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class WorldRenderSystem(
    private var width: Int,
    private var height: Int,
    private val _camera: OrthographicCamera
) :
    SortedIteratingSystem(
        Family.all(
            SpriteComponent::class.java,
            PositionComponent::class.java
        ).get(),
        LayerComparator()
    ),
    RenderStage
{

    private val deltaTime = 0f
    init {
        resize(width, height)
    }

    //TODO: implement framebuffer

    override fun update(deltaTime: Float) {
        super.update(this.deltaTime)
    }


    override fun processEntity(entity: Entity, deltaTime: Float) {
        //TODO: implement
    }

    override fun render(batch: SpriteBatch) {
        //TODO: implement
    }

    override fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }
}
