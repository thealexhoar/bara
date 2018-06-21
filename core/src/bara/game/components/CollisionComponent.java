package bara.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;


public class CollisionComponent implements Component, Pool.Poolable {
    public HashMap<String, Array<Contact>> beginContacts = new HashMap<String, Array<Contact>>();
    public HashMap<String, Array<Fixture>> currentContacts = new HashMap<String, Array<Fixture>>();
    public HashMap<String, Array<Contact>> endContacts = new HashMap<String, Array<Contact>>();

    @Override
    public void reset() {
        beginContacts.clear();
        currentContacts.clear();
        endContacts.clear();
    }

}
