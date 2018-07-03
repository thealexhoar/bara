package bara.game.util

import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxArray


class AssetHandler(val logResults: Boolean = true) {
    private val ERR = "ERROR"
    private val NULL_PIXEL = "NULL_PIXEL"
    private val TEXTURE_ROOT = "core/assets/textures/"
    private val SHADER_ROOT = "core/assets/shaders/"
    private val FONT_ROOT = "core/assets/fonts/"
    private val LEVEL_ROOT = "core/assets/levels/"

    private data class AssetDef(
        val jclass: Class<*>,
        val name: String,
        val location: String,
        val configure: (Any) -> Unit = {}
    )

    private val assetManager = AssetManager()
    private val fileMappings = ObjectMap<Class<*>, ObjectMap<String, String>>()
    private val pendingAssets = GdxArray<AssetDef>()

    init {
        assetManager
    }

    inline fun <reified T> loadAsset(
        name: String,
        location: String,
        params: AssetLoaderParameters<T>? = null,
        noinline configure: (Any) -> Unit = {}
    ) {
        loadAsset(T::class.java, name, location, params, configure)
    }

    fun <T> loadAsset(
        jclass: Class<T>,
        name: String,
        location: String,
        params: AssetLoaderParameters<T>? = null,
        configure: (Any) -> Unit = {}
    ) {
        val fullLocation = assetPath(jclass)!! + location
        pendingAssets.add(AssetDef(jclass, name, fullLocation, configure))
        if (params != null) {
            assetManager.load<T>(fullLocation, jclass, params)
        }
        else {
            assetManager.load<T>(fullLocation, jclass)
        }
        if (logResults) {
            println("Attempting to load [$jclass] $name from $fullLocation")
        }
    }

    fun update() : Boolean {
        val complete = assetManager.update()
        if (complete) {
            registerAssets()
        }
        return complete
    }

    fun getProgress() : Float {
        return assetManager.progress
    }

    inline fun <reified T> getAsset(name: String) : T? {
        return getAsset<T>(T::class.java, name)
    }

    fun <T> getAsset(jclass: Class<T>, name: String) : T? {
        val location: String? = fileMappings.get(jclass)?.get(name)
        return try {
            assetManager.get(location)
        } catch (e: Exception) {
            null
        }
    }

    private fun registerAssets() {
        pendingAssets.forEach { assetDef ->
            if (assetManager.isLoaded(assetDef.location, assetDef.jclass)) {
                assetDef.configure(assetManager.get(assetDef.location))
                if (!fileMappings.containsKey(assetDef.jclass)) {
                    fileMappings.put(assetDef.jclass, ObjectMap())
                }
                fileMappings[assetDef.jclass]
                    .put(assetDef.name, assetDef.location)
                println("Successfully loaded [${assetDef.jclass}] ${assetDef.name} from ${assetDef.location}")
            }
        }
    }

    private fun <T> assetPath(jclass: Class<T>) : String? {
        return when (jclass) {
            Texture::class.java -> TEXTURE_ROOT
            ShaderProgram::class.java -> SHADER_ROOT
            BitmapFont::class.java -> FONT_ROOT
            else -> null
        }
    }


}
