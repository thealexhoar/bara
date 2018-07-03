package bara.game.systems

import bara.game.ashley.PooledResourceEngine
import bara.game.components.CollisionComponent
import bara.game.components.PhysicsComponent
import bara.game.util.ComponentLookup
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.physics.box2d.*

class CollisionSystem() :
    IteratingSystem(
        Family.all(
            PhysicsComponent::class.java,
            CollisionComponent::class.java
        ).get()
    ),
    ContactListener,
    ContactFilter
{

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        if (engine is PooledResourceEngine) {
            val world = engine.getResource<World>()!!
            world.setContactListener(this)
            world.setContactFilter(this)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val collisionComponent = ComponentLookup.collision(entity)!!
        collisionComponent.beginContacts.forEach { entry ->
            entry.value.clear()
        }
        collisionComponent.endContacts.forEach { entry ->
            entry.value.clear()
        }
    }

    override fun beginContact(contact: Contact) {
        val fixtureA = contact.fixtureA
        val fixtureB = contact.fixtureB
        val entityA = fixtureA.body.userData as Entity
        val entityB = fixtureB.body.userData as Entity
        val collisionA = ComponentLookup.collision(entityA)
        val collisionB = ComponentLookup.collision(entityB)

        if (collisionA != null) {
            val fixtureName = fixtureA.userData as String
            collisionA.beginContacts.get(fixtureName).add(contact)
            collisionA.currentContacts.get(fixtureName).add(fixtureB)
        }
        if (collisionB != null) {
            val fixtureName = fixtureB.userData as String
            collisionB.beginContacts.get(fixtureName).add(contact)
            collisionB.currentContacts.get(fixtureName).add(fixtureA)
        }
    }

    override fun endContact(contact: Contact) {
        val fixtureA = contact.fixtureA
        val fixtureB = contact.fixtureB
        val entityA = fixtureA.body.userData as Entity
        val entityB = fixtureB.body.userData as Entity
        val collisionA = ComponentLookup.collision(entityA)
        val collisionB = ComponentLookup.collision(entityB)

        if (collisionA != null) {
            val fixtureName = fixtureA.userData as String
            collisionA.endContacts.get(fixtureName).add(contact)
            collisionA.currentContacts
                .get(fixtureName)
                .removeValue(fixtureA, true)
        }
        if (collisionB != null) {
            val fixtureName = fixtureB.userData as String
            collisionB.endContacts.get(fixtureName).add(contact)
            collisionB.currentContacts
                .get(fixtureName)
                .removeValue(fixtureA, true)
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {
        //TODO: implement
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {
        //TODO: implement
    }

    override fun shouldCollide(fixtureA: Fixture, fixtureB: Fixture): Boolean {
        //TODO: implement
        return true
    }

}