package bara.game.components

import bara.game.ashley.GameComponent
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import bara.game.ashley.EngineEntity


class CollisionComponent : GameComponent() {
    var beginContacts = ObjectMap<String, Array<Contact>>()
    var currentContacts = ObjectMap<String, Array<Fixture>>()
    var endContacts = ObjectMap<String, Array<Contact>>()

    override fun reset() {
        beginContacts.clear()
        currentContacts.clear()
        endContacts.clear()
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        engineEntity.with<CollisionComponent>()
    }

}
