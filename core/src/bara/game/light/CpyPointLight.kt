package bara.game.light

import box2dLight.PointLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color

class CpyPointLight(
    rayHandler: RayHandler,
    rays: Int,
    color: Color,
    distance: Float,
    x: Float,
    y: Float
) :
    PointLight(
        rayHandler,
        rays,
        color,
        distance,
        x,
        y
    ),
    Cloneable
{
    public override fun clone() : CpyPointLight {
        return CpyPointLight(
            rayHandler,
            rayNum,
            color,
            distance,
            x,
            y
        )
    }
}