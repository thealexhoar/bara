package bara.game.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.ObjectMap

class KeyboardController(
    var config: Config = Config()
) : InputController {
    private val currentState = ObjectMap<InputBinding, Boolean>()
    private val oldState = ObjectMap<InputBinding, Boolean>()

    init {
        for (binding in InputBinding.values()) {
            currentState.put(binding, false)
            oldState.put(binding, false)
        }
    }

    data class Config(
        val up: Int = Input.Keys.W,
        val down: Int = Input.Keys.S,
        val left: Int = Input.Keys.A,
        val right: Int = Input.Keys.D,
        val jump: Int = Input.Keys.SPACE
    )

    override fun update() {
        currentState.forEach { entry ->
            oldState.put(entry.key, entry.value)
            currentState.put(
                entry.key,
                Gdx.input.isKeyPressed(inputToKey(entry.key))
            )
        }
    }

    override fun getXAxis(): Float {
        var xAxis = 0f
        if (inputDown(InputBinding.LEFT)) {
            xAxis -= 1
        }
        if (inputDown(InputBinding.RIGHT)) {
            xAxis += 1
        }
        return xAxis
    }

    override fun getYAxis(): Float {
        var yAxis = 0f
        if (inputDown(InputBinding.DOWN)) {
            yAxis -= 1
        }
        if (inputDown(InputBinding.UP)) {
            yAxis += 1
        }
        return yAxis
    }

    override fun inputDown(binding: InputBinding): Boolean {
        return currentState[binding]
    }

    override fun inputUp(binding: InputBinding): Boolean {
        return !currentState[binding]
    }

    override fun inputPressed(binding: InputBinding): Boolean {
        return currentState[binding] && !oldState[binding]
    }

    override fun inputReleased(binding: InputBinding): Boolean {
        return !currentState[binding] && oldState[binding]
    }

    private fun inputToKey(input: InputBinding) : Int {
        return when (input) {
            InputBinding.UP -> config.up
            InputBinding.DOWN -> config.down
            InputBinding.LEFT -> config.left
            InputBinding.RIGHT -> config.right
            InputBinding.JUMP -> config.down
        }
    }

}