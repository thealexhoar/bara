package bara.game.behavior

import com.badlogic.ashley.core.Entity


class PlayerBehavior : Behavior {
    private enum class MoveState {
        IDLE,
        MOVE,
        ACTION
    }

    private enum class ActionState {
        NONE,
        ATTACK
    }

    override fun run(deltaTime: Float, entity: Entity) {

    }

}
