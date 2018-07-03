package bara.game.components

import bara.game.ashley.GameComponent
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.utils.ObjectMap
import bara.game.ashley.EngineEntity

class PhysicsComponent : GameComponent() {
    var bodyDef: BodyDef? = null
    var fixtureDefs = ObjectMap<String, FixtureDef>()
    var body: Body? = null
    var fixtures = ObjectMap<String, Fixture>()

    override fun reset() {
        bodyDef = null
        fixtureDefs.clear()
        body = null
        fixtures.clear()
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        val tempComponent = this
        engineEntity.with<PhysicsComponent> {
            bodyDef = tempComponent.bodyDef
            tempComponent.fixtureDefs.forEach { entry ->
                fixtureDefs.put(entry.key, entry.value)
            }
        }
    }

}
