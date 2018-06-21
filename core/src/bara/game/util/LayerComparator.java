package bara.game.util;

import com.badlogic.ashley.core.Entity;
import bara.game.components.LayerComponent;

import java.util.Comparator;

public class LayerComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity e1, Entity e2) {
        LayerComponent l1 = ComponentLookup.layer(e1);
        LayerComponent l2 = ComponentLookup.layer(e2);

        int z1 = l1 == null ? 0 : l1.layer;
        int z2 = l2 == null ? 0 : l2.layer;

        return z1 - z2;
    }
}
