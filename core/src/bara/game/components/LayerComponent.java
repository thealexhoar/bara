package bara.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class LayerComponent implements Component, Pool.Poolable {
    public int layer = 0;

    @Override
    public void reset() {
        layer = 0;
    }
}
