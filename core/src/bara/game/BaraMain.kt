package bara.game

import bara.game.ashley.PooledResourceEngine
import bara.game.ashley.ProtoEntity
import bara.game.components.*
import bara.game.graphics.RenderPipeline
import bara.game.input.InputManager
import bara.game.light.CpyDirectionalLight
import bara.game.systems.*
import bara.game.util.AssetHandler
import bara.game.util.ComponentLookup
import box2dLight.RayHandler
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import ktx.app.KtxApplicationAdapter
import ktx.box2d.createWorld
import ktx.box2d.earthGravity

class BaraMain(
    var screenWidth: Int,
    var screenHeight: Int
) :
    KtxApplicationAdapter //TODO: refactor into KtxGame
{

    var engine = PooledResourceEngine()
    lateinit var renderPipeline: RenderPipeline
    lateinit var spriteBatch: SpriteBatch
    val FRAME_60 = 1 / 60f

    override fun create() {
        renderPipeline = RenderPipeline(screenWidth, screenHeight)
        spriteBatch = SpriteBatch()


        /////////////////
        //  RESOURCES  //
        /////////////////

        //assets
        val assetHandler = AssetHandler()
        assetHandler.loadAsset<BitmapFont>("bitpotion", "BitPotionExt.fnt")

        val params = TextureLoader.TextureParameter()
        params.genMipMaps = true
        params.minFilter = Texture.TextureFilter.MipMapLinearNearest
        params.magFilter = Texture.TextureFilter.MipMapLinearNearest
        assetHandler.loadAsset<Texture>(
            "badlogic",
            "badlogic.jpg",
            params
        )
        //TODO: offload loading to a loading screen
        while (!assetHandler.update()) {}

        engine.addResource(assetHandler)

        //input
        engine.addResource(InputManager())

        //physics
        engine.addResource(createWorld(
            gravity = earthGravity,
            allowSleep = true
        ))

        //spriteBatch
        engine.addResource(spriteBatch)

        //lighting
        engine.addResource(
            RayHandler(
                engine.getResource<World>(),
                screenWidth,
                screenHeight
            )
        )

        //game camera
        val gameCamera = OrthographicCamera(
            screenWidth.toFloat() / PhysicsSystem.METER,
            screenHeight.toFloat() / PhysicsSystem.METER
        )
        gameCamera.position.set(1f, 0f, 0f)
        gameCamera.update()
        engine.addResource(gameCamera)


        /////////////////
        //   SYSTEMS   //
        /////////////////

        //TODO: add systems to engine
        renderPipeline.append(SpriteRenderSystem(screenWidth, screenHeight))
        renderPipeline.append(DebugRenderSystem(screenWidth, screenHeight))

        for (system in renderPipeline.getEntitySystems()) {
            engine.addSystem(system)
        }

        engine.addSystem(PhysicsSystem(300, 3, 1))
        engine.addSystem(PhysicsTransformSystem())
        engine.addSystem(SpriteTransformSystem())

        //////////////////
        //   ENTITIES   //
        //////////////////
        val protoFloor = ProtoEntity()
            .with(PositionComponent())
            .with(PhysicsComponent()) { component ->
                val pc = component as PhysicsComponent
                val bodyDef = BodyDef()
                bodyDef.type = BodyDef.BodyType.StaticBody
                bodyDef.position.set(0f, 0f)
                bodyDef.angle = 0.1f

                val fixtureDef = FixtureDef()
                val shape = PolygonShape()
                 shape.setAsBox(25f, 1f)
                fixtureDef.shape = shape

                pc.bodyDef = bodyDef
                pc.fixtureDefs.put("rect", fixtureDef)
            }
            .with(SpriteComponent()) { component ->
                val sc = component as SpriteComponent
                val texture = assetHandler.getAsset<Texture>("badlogic")
                val sprite = Sprite(texture)
                sprite.setSize(50f, 2f)
                sprite.setOriginCenter()
                sprite.color = Color.ROYAL

                sc.sprite = sprite
                sc.offsetX = -25f
                sc.offsetY = -1f
            }

        val protoBox = ProtoEntity()
            .with(PositionComponent())
            .with(PhysicsComponent()) { component ->
                val pc = component as PhysicsComponent
                val bodyDef = BodyDef()
                bodyDef.type = BodyDef.BodyType.DynamicBody
                bodyDef.position.set(-10f, 10f)

                val fixtureDef = FixtureDef()
                val shape = PolygonShape()
                shape.setAsBox(0.5f, 0.8f)
                fixtureDef.shape = shape
                fixtureDef.density = 1f

                pc.bodyDef = bodyDef
                pc.fixtureDefs.put("rect", fixtureDef)
            }
            .with(SpriteComponent()) { component ->
                val sc = component as SpriteComponent
                val texture = assetHandler.getAsset<Texture>("badlogic")
                val sprite = Sprite(texture)
                sprite.setSize(1f, 1.6f)
                sprite.setOriginCenter()

                sc.sprite = sprite
                sc.offsetX = -0.5f
                sc.offsetY = -0.8f
            }


        protoFloor.createEntity(engine)
        for (i in 0..150) {
            protoBox.createEntity(engine) { boxEntity ->
                val pc = ComponentLookup.physics(boxEntity)!!
                pc.bodyDef!!.position.set(0f + 1f * (i % 20 - 10), 10f + 2f * i)
                pc.bodyDef!!.angle = 0.15f * i
            }
        }
    }


    override fun render() {
        // TODO: use fixed timestep with real-time interpolation
        val deltaTime = Gdx.graphics.rawDeltaTime
        //val deltaTime = FRAME_60

        Gdx.gl.glClearColor(0.5f, 0.2f, 0.4f, 1f)
        //Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        engine.getResource<InputManager>()?.update()

        engine.update(deltaTime)
        renderPipeline.render(spriteBatch)

    }

    override fun resize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height

        val gameCamera = engine.getResource<OrthographicCamera>()!!
        val oldPos = gameCamera.position.cpy()
        gameCamera.setToOrtho(
            false,
            width.toFloat() / PhysicsSystem.METER,
            height.toFloat() / PhysicsSystem.METER
        )
        gameCamera.position.set(oldPos)
        gameCamera.update()

        val rayHandler = engine.getResource<RayHandler>()!!
        rayHandler.resizeFBO(width, height)

        renderPipeline.resize(width, height)
    }

    override fun dispose() {
        renderPipeline.dispose()
        spriteBatch.dispose()
    }

}