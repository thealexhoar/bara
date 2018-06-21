package bara.game.behavior;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;


public class PlayerBehavior implements Behavior {
    private enum MoveState {
        IDLE,
        MOVE,
        ACTION
    }

    private enum ActionState {
        NONE,
        ATTACK
        //TODO 10/22/17: add more as time goes on
    }


    private static final float SPEED_MOVE_MAX = 16.0f;
    private static final float ACCEL_MOVE = 96.0f;

    private static final float ACCEL_FACTOR = 0.04f;
    private static final float SPEED_FACTOR = 1f - ACCEL_FACTOR;

    private static final float SPEED_ROTATOR_MIN =  0.3f * 2 * (float)Math.PI;
    private static final float SPEED_ROTATOR_MAX =  1.5f * 2 * (float)Math.PI;


    private static final float SLOWDOWN_FACTOR = 0.8f;
    private static final float SLOWDOWN_CUTOFF = 0.5f;



    private MoveState _moveState = MoveState.IDLE;
    private ActionState _actionState = ActionState.NONE;
    private float _timeInState = 0f;

    private float _animatorTheta1 = 0;

    private int[] _ammoMarkers = {1, 3, 5, 7, 9};
    private int _ammoMax = 0;
    private int _ammoCount = 0;

    public PlayerBehavior() {}

    @Override
    public void run(float deltaTime, Entity entity) {

    }

    private void changeMoveState(MoveState moveState, Vector2 input, Entity entity) {
        //clean up old state here
        switch(_moveState) {
            case IDLE:
                break;
            case MOVE:
                break;
        }

        System.out.print("Switching from ");
        System.out.print(_moveState);
        System.out.print(" to ");
        System.out.println(moveState);
        _moveState = moveState;
        _timeInState = 0;

        //initialize new state here
        switch(_moveState) {
            case IDLE:
                break;
            case MOVE:
                break;
        }
    }
}
