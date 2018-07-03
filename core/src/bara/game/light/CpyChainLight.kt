package bara.game.light

import box2dLight.ChainLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color

class CpyChainLight (
    rayHandler: RayHandler,
    rays: Int,
    color: Color,
    distance: Float,
    direction: Int,
    chain: FloatArray
) :
    ChainLight(
        rayHandler,
        rays,
        color,
        distance,
        direction,
        chain
    ),
    Cloneable
{
    fun getRayDirection() : Int {
        return rayDirection
    }

    public override fun clone() : CpyChainLight {
        return CpyChainLight(
            rayHandler,
            rayNum,
            color,
            distance,
            rayDirection,
            chain.toArray()
        )
    }
}