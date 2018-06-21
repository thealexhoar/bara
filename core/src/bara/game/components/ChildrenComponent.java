package bara.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

public class ChildrenComponent implements Component, Pool.Poolable {
    //NOTE 8/13/17: use enums/ints for keys instead?
    public HashMap<String, Entity> children = new HashMap<String, Entity>();

    @Override
    public void reset() {
        children.clear();
    }

}
