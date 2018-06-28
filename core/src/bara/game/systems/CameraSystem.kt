package bara.game.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.OrthographicCamera
import bara.game.components.CameraComponent
import bara.game.components.PositionComponent
import bara.game.util.ComponentLookup

class CameraSystem(private val camera: OrthographicCamera) : EntitySystem() {
    //TODO: refactor camera to be part of the Engine's resources
    private lateinit var entities: ImmutableArray<Entity>

    override fun addedToEngine(engine: Engine?) {
        entities = engine!!.getEntitiesFor(
            Family.all(
                PositionComponent::class.java,
                CameraComponent::class.java
            ).get()
        )
    }

    override fun update(deltaTime: Float) {
        if (entities.size() > 0) {
            val cameraEntity = entities.first()
            val cameraComponent = ComponentLookup.camera(cameraEntity)
            val positionComponent = ComponentLookup.position(cameraEntity)

            camera.position.set(
                positionComponent.x,
                positionComponent.y,
                camera.position.z
            )
            camera.update()

            //TODO 11/7/17: add lerping to new position
            //TODO 11/7/17: add effects like screenshake
        }
    }
}
