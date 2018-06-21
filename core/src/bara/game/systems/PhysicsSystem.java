package bara.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import bara.game.components.CollisionComponent;
import bara.game.components.PhysicsComponent;
import bara.game.components.PositionComponent;
import bara.game.util.ComponentLookup;


public class PhysicsSystem extends EntitySystem implements EntityListener, ContactListener, ContactFilter {
    public static final float METER = 32;

    private ImmutableArray<Entity> _entities;
    private World _world;
    private final float _timestep;
    private float _timeAccumulator = 0;

    public PhysicsSystem(World world, int iterations) {
        super();
        _world = world;
        //_world.setContactFilter(this);
        _world.setContactListener(this);
        _timestep = 1.0f / iterations;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Family family = Family.all(PhysicsComponent.class, PositionComponent.class).get();
        _entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, this);
    }

    @Override
    public void entityAdded(Entity entity) {
        PhysicsComponent physicsComponent = ComponentLookup.physics(entity);
        CollisionComponent collisionComponent = ComponentLookup.collision(entity);
        physicsComponent.body = _world.createBody(physicsComponent.bodyDef);
        //set body data to point at the entity
        //but set fixture data to hold its name
        physicsComponent.body.setUserData(entity);
        if (collisionComponent != null) {
            for (String key : physicsComponent.fixtureDefs.keySet()) {
                FixtureDef fixtureDef = physicsComponent.fixtureDefs.get(key);
                Fixture fixture = physicsComponent.body.createFixture(fixtureDef);
                fixture.setUserData(key);
                physicsComponent.fixtures.put(key, fixture);

                collisionComponent.beginContacts.put(key, new Array<Contact>());
                collisionComponent.currentContacts.put(key, new Array<Fixture>());
                collisionComponent.endContacts.put(key, new Array<Contact>());
            }
        }
        else {
            for (String key : physicsComponent.fixtureDefs.keySet()) {
                FixtureDef fixtureDef = physicsComponent.fixtureDefs.get(key);
                Fixture fixture = physicsComponent.body.createFixture(fixtureDef);
                fixture.setUserData(key);
                physicsComponent.fixtures.put(key, fixture);
            }
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        PhysicsComponent physicsComponent = ComponentLookup.physics(entity);
        _world.destroyBody(physicsComponent.body);
    }

    @Override
    public void update(float deltaTime) {
        _timeAccumulator += deltaTime;
        while(_timeAccumulator >= _timestep) {
            _world.step(_timestep, 3, 1);
            _timeAccumulator -= _timestep;
        }
        //_world.step(1/60f, 10, 4);

        for (int i = 0; i < _entities.size(); i++) {
            Entity entity = _entities.get(i);
            PositionComponent positionComponent = ComponentLookup.position(entity);
            PhysicsComponent physicsComponent = ComponentLookup.physics(entity);
            positionComponent.x = physicsComponent.body.getPosition().x;
            positionComponent.y = physicsComponent.body.getPosition().y;
            positionComponent.theta = physicsComponent.body.getAngle();
        }
    }

    @Override
    public void beginContact (Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Entity entityA = (Entity)fixtureA.getBody().getUserData();
        Entity entityB = (Entity)fixtureB.getBody().getUserData();
        CollisionComponent collisionComponentA = ComponentLookup.collision(entityA);
        CollisionComponent collisionComponentB = ComponentLookup.collision(entityB);

        if (collisionComponentA != null) {
            String fixtureName = (String) fixtureA.getUserData();
            collisionComponentA.beginContacts.get(fixtureName).add(contact);
            collisionComponentA.currentContacts.get(fixtureName).add(fixtureB);
        }
        if (collisionComponentB != null) {
            String fixtureName = (String) fixtureB.getUserData();
            collisionComponentB.beginContacts.get(fixtureName).add(contact);
            collisionComponentB.currentContacts.get(fixtureName).add(fixtureA);
        }

    }

    @Override
    public void endContact (Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Entity entityA = (Entity)fixtureA.getBody().getUserData();
        Entity entityB = (Entity)fixtureB.getBody().getUserData();
        CollisionComponent collisionComponentA = ComponentLookup.collision(entityA);
        CollisionComponent collisionComponentB = ComponentLookup.collision(entityB);

        if (collisionComponentA != null) {
            String fixtureName = (String) fixtureA.getUserData();
            collisionComponentA.endContacts.get(fixtureName).add(contact);
            collisionComponentA.currentContacts.get(fixtureName).removeValue(fixtureB, true);
        }
        if (collisionComponentB != null) {
            String fixtureName = (String) fixtureB.getUserData();
            collisionComponentB.endContacts.get(fixtureName).add(contact);
            collisionComponentB.currentContacts.get(fixtureName).removeValue(fixtureA, true);
        }
    }

    @Override
    public void preSolve (Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve (Contact contact, ContactImpulse impulse) {}

    @Override
    public boolean shouldCollide (Fixture fixtureA, Fixture fixtureB) {
        return true;
    }
}
