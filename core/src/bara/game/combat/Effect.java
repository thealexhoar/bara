package bara.game.combat;

import com.badlogic.ashley.core.Entity;

public interface Effect {
    boolean applyTo(Entity entity);
}
