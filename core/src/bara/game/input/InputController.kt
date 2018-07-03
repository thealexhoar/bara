package bara.game.input

interface InputController {
    fun update()
    fun getXAxis() : Float
    fun getYAxis() : Float
    fun inputDown(binding: InputBinding) : Boolean
    fun inputUp(binding: InputBinding) : Boolean
    fun inputPressed(binding: InputBinding) : Boolean
    fun inputReleased(binding: InputBinding) : Boolean
}