package bara.game.light

import box2dLight.DirectionalLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color

class CpyDirectionalLight (
    rayHandler: RayHandler,
    rays: Int,
    color: Color,
    directionDegree: Float
) :
    DirectionalLight (
        rayHandler,
        rays,
        color,
        directionDegree
    ),
    Cloneable
{
    public override fun clone() : CpyDirectionalLight {
        return CpyDirectionalLight(
            rayHandler,
            rayNum,
            color,
            direction
        )
    }
}