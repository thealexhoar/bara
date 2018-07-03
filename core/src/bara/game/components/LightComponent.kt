package bara.game.components

import bara.game.ashley.GameComponent
import bara.game.ashley.EngineEntity
import bara.game.light.CpyChainLight
import bara.game.light.CpyConeLight
import bara.game.light.CpyDirectionalLight
import bara.game.light.CpyPointLight
import box2dLight.Light

class LightComponent : GameComponent() {
    var light: Light? = null

    override fun reset() {
        light?.dispose()
    }

    override fun buildOnto(engineEntity: EngineEntity) {
        val tempLight = this.light
        engineEntity.with<LightComponent> {
            light = when (tempLight) {
                is CpyChainLight -> tempLight.clone()
                is CpyConeLight -> tempLight.clone()
                is CpyDirectionalLight -> tempLight.clone()
                is CpyPointLight -> tempLight.clone()
                else -> null
            }
        }
    }

}
