package bara.game.ashley

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxArray
import kotlin.reflect.KClass



class PooledResourceEngine : PooledEngine() {
    private val resources = ObjectMap<KClass<*>, GdxArray<*>>()

    inline fun <reified T> addResource(value: T, index: Int = 0) {
        addResource(T::class, value as Any)
    }

    fun <T> addResource(kclass: KClass<*>, value: T, index: Int = 0) {
        if (!resources.containsKey(kclass)) {
            resources.put(kclass, GdxArray<T>())
        }
        if (resources[kclass].size <= index) {
            resources[kclass].size = index + 1
        }
        (resources[kclass] as GdxArray<T>).set(index, value)
    }

    inline fun <reified T> getResource(index: Int = 0) : T? {
        return getResource(T::class)
    }

    fun <T> getResource(kclass: KClass<*>, index: Int = 0) : T {
        return resources[kclass][index] as T
    }
}