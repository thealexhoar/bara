package bara.game.light

import box2dLight.ConeLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color

class CpyConeLight (
    rayHandler: RayHandler,
    rays: Int,
    color: Color,
    distance: Float,
    x: Float,
    y: Float,
    directionDegree: Float,
    coneDegree: Float
) :
    ConeLight(
        rayHandler,
        rays,
        color,
        distance,
        x,
        y,
        directionDegree,
        coneDegree
    ),
    Cloneable
{
    public override fun clone() : CpyConeLight {
        return CpyConeLight(
            rayHandler,
            rayNum,
            color,
            distance,
            x,
            y,
            direction,
            coneDegree
        )
    }
}