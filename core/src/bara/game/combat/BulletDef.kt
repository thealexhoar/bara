package bara.game.combat

import com.badlogic.gdx.math.Vector2
import bara.game.behavior.Behavior
import ktx.collections.GdxArray

class BulletDef(
    val velocity: Vector2 = Vector2(),
    val position: Vector2 = Vector2(),
    val behavior: Behavior? = null,
    val effects: GdxArray<Effect> = GdxArray()
) {

}
