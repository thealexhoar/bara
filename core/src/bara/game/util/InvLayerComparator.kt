package bara.game.util

import com.badlogic.ashley.core.Entity

import java.util.Comparator

class InvLayerComparator : Comparator<Entity> {
    override fun compare(e1: Entity, e2: Entity): Int {
        val l1 = ComponentLookup.layer(e1)
        val l2 = ComponentLookup.layer(e2)

        val z1 = l1?.layer ?: 0
        val z2 = l2?.layer ?: 0

        return z2 - z1
    }
}
