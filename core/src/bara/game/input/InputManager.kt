package bara.game.input

class InputManager(
    val inputController: InputController = KeyboardController()
){
    fun getXAxis(): Float {
        return inputController.getXAxis()
    }

    fun getYAxis(): Float {
        return inputController.getYAxis()
    }

    fun inputDown(binding: InputBinding): Boolean {
        return inputController.inputDown(binding)
    }

    fun inputUp(binding: InputBinding): Boolean {
        return inputController.inputUp(binding)
    }

    fun inputPressed(binding: InputBinding): Boolean {
        return inputController.inputPressed(binding)
    }

    fun inputReleased(binding: InputBinding): Boolean {
        return inputController.inputReleased(binding)
    }

    fun update() {
        inputController.update()
    }


    //TODO: add mouse input handling
}