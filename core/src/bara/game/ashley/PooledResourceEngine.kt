package bara.game.ashley

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxArray
import kotlin.reflect.KClass



class PooledResourceEngine : PooledEngine() {
    private val resources = ObjectMap<KClass<*>, GdxArray<*>>()

    inline fun <reified T> registerResource(value: T) {
        registerResource(T::class, value as Any)
    }

    fun <T> registerResource(kclass: KClass<*>, value: T) {
        resources.put(kclass, GdxArray<T>())
    }

    inline fun <reified T> getResource(index: Int = 0) : T? {
        return getResource(T::class)
    }

    fun <T> getResource(kclass: KClass<*>, index: Int = 0) : T {
        return resources[kclass][index] as T
    }
}