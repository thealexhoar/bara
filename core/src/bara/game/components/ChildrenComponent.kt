package bara.game.components

import bara.game.ashley.GameComponent
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.ObjectMap
import ktx.ashley.EngineEntity


class ChildrenComponent : GameComponent() {
    //NOTE 8/13/17: use enums/ints for keys instead?
    var children = ObjectMap<String, Entity>()

    override fun reset() {
        children.clear()
    }

    override fun buildOnto(engineEntity: EngineEntity) {

    }

}
