package bara.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Pool;
import bara.game.systems.WorldRenderSystem;

import java.util.HashMap;

public class PhysicsComponent implements Component, Pool.Poolable {
    public BodyDef bodyDef = null;
    public HashMap<String, FixtureDef> fixtureDefs = new HashMap<String, FixtureDef>();
    public Body body = null;
    public HashMap<String, Fixture> fixtures = new HashMap<String, Fixture>();

    @Override
    public void reset() {
        bodyDef = null;
        fixtureDefs.clear();
        body = null;
        fixtures.clear();
    }

}
