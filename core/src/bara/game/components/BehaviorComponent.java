package bara.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.decorator.AlwaysFail;
import com.badlogic.gdx.ai.btree.decorator.AlwaysSucceed;
import com.badlogic.gdx.utils.Pool;
import bara.game.behavior.Behavior;

public class BehaviorComponent implements Component, Pool.Poolable {
    public Behavior behavior;

    @Override
    public void reset() {
        behavior = null;
    }

}
