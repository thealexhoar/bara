package bara.game.systems

import bara.game.ashley.PooledResourceEngine
import bara.game.components.PhysicsComponent
import bara.game.util.ComponentLookup
import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.physics.box2d.*
import ktx.collections.GdxArray

class PhysicsSystem (
    iterations: Int = 60,
    private val velocitySteps: Int = 15,
    private val positionSteps: Int = 8
) :
    EntitySystem(),
    EntityListener
{
    private val timeStep = 1f / iterations
    private var timeAccumulator = 0f
    private lateinit var world: World
    private lateinit var entities: ImmutableArray<Entity>

    override fun addedToEngine(engine: Engine) {
        val family =Family.all(PhysicsComponent::class.java).get()
        if (engine is PooledResourceEngine) {
            entities = engine.getEntitiesFor(family)
            world = engine.getResource<World>()!!
        }
        engine.addEntityListener(family, this)
    }

    override fun update(deltaTime: Float) {
        val dt = Math.min(deltaTime, 0.25f)
        timeAccumulator += dt
        while (timeAccumulator >= timeStep) {
            world.step(timeStep, velocitySteps, positionSteps)
            timeAccumulator -= timeStep
        }
    }

    override fun entityAdded(entity: Entity) {
        val physicsComponent = ComponentLookup.physics(entity)!!
        val collisionComponent = ComponentLookup.collision(entity)

        physicsComponent.body = world.createBody(physicsComponent.bodyDef)
        physicsComponent.body?.userData = entity

        if (collisionComponent != null) {
            for (key in physicsComponent.fixtureDefs.keys()) {
                val fixtureDef = physicsComponent.fixtureDefs[key]
                val fixture = physicsComponent.body!!.createFixture(fixtureDef)
                fixture.userData = key
                physicsComponent.fixtures.put(key, fixture)

                collisionComponent.beginContacts.put(key, GdxArray())
                collisionComponent.currentContacts.put(key, GdxArray())
                collisionComponent.endContacts.put(key, GdxArray())
            }
        }
        else {
            for (key in physicsComponent.fixtureDefs.keys()) {
                val fixtureDef = physicsComponent.fixtureDefs[key]
                val fixture = physicsComponent.body!!.createFixture(fixtureDef)
                fixture.userData = key
                physicsComponent.fixtures.put(key, fixture)
            }
        }
    }

    override fun entityRemoved(entity: Entity) {
        val physicsComponent = ComponentLookup.physics(entity)!!
        world.destroyBody(physicsComponent.body)
    }

    companion object {
        val METER = 32f
    }
}