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
        PhysicsComponent physicsComponent = ComponentLookup.INSTANCE.physics(entity);
        CollisionComponent collisionComponent = ComponentLookup.INSTANCE.collision(entity);
        physicsComponent.setBody(_world.createBody(physicsComponent.getBodyDef()));
        //set body data to point at the entity
        //but set fixture data to hold its name
        physicsComponent.getBody().setUserData(entity);
        if (collisionComponent != null) {
            for (String key : physicsComponent.getFixtureDefs().keys()) {
                FixtureDef fixtureDef = physicsComponent.getFixtureDefs().get(key);
                Fixture fixture = physicsComponent.getBody().createFixture(fixtureDef);
                fixture.setUserData(key);
                physicsComponent.getFixtures().put(key, fixture);

                collisionComponent.getBeginContacts().put(key, new Array<Contact>());
                collisionComponent.getCurrentContacts().put(key, new Array<Fixture>());
                collisionComponent.getEndContacts().put(key, new Array<Contact>());
            }
        }
        else {
            for (String key : physicsComponent.getFixtureDefs().keys()) {
                FixtureDef fixtureDef = physicsComponent.getFixtureDefs().get(key);
                Fixture fixture = physicsComponent.getBody().createFixture(fixtureDef);
                fixture.setUserData(key);
                physicsComponent.getFixtures().put(key, fixture);
            }
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        PhysicsComponent physicsComponent = ComponentLookup.INSTANCE.physics(entity);
        _world.destroyBody(physicsComponent.getBody());
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
            PositionComponent positionComponent = ComponentLookup.INSTANCE.position(entity);
            PhysicsComponent physicsComponent = ComponentLookup.INSTANCE.physics(entity);
            positionComponent.setX(physicsComponent.getBody().getPosition().x);
            positionComponent.setY(physicsComponent.getBody().getPosition().y);
            positionComponent.setTheta(physicsComponent.getBody().getAngle());
        }
    }

    @Override
    public void beginContact (Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Entity entityA = (Entity)fixtureA.getBody().getUserData();
        Entity entityB = (Entity)fixtureB.getBody().getUserData();
        CollisionComponent collisionComponentA = ComponentLookup.INSTANCE.collision(entityA);
        CollisionComponent collisionComponentB = ComponentLookup.INSTANCE.collision(entityB);

        if (collisionComponentA != null) {
            String fixtureName = (String) fixtureA.getUserData();
            collisionComponentA.getBeginContacts().get(fixtureName).add(contact);
            collisionComponentA.getCurrentContacts().get(fixtureName).add(fixtureB);
        }
        if (collisionComponentB != null) {
            String fixtureName = (String) fixtureB.getUserData();
            collisionComponentB.getBeginContacts().get(fixtureName).add(contact);
            collisionComponentB.getCurrentContacts().get(fixtureName).add(fixtureA);
        }

    }

    @Override
    public void endContact (Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Entity entityA = (Entity)fixtureA.getBody().getUserData();
        Entity entityB = (Entity)fixtureB.getBody().getUserData();
        CollisionComponent collisionComponentA = ComponentLookup.INSTANCE.collision(entityA);
        CollisionComponent collisionComponentB = ComponentLookup.INSTANCE.collision(entityB);

        if (collisionComponentA != null) {
            String fixtureName = (String) fixtureA.getUserData();
            collisionComponentA.getEndContacts().get(fixtureName).add(contact);
            collisionComponentA.getCurrentContacts().get(fixtureName).removeValue(fixtureB, true);
        }
        if (collisionComponentB != null) {
            String fixtureName = (String) fixtureB.getUserData();
            collisionComponentB.getEndContacts().get(fixtureName).add(contact);
            collisionComponentB.getCurrentContacts().get(fixtureName).removeValue(fixtureA, true);
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
