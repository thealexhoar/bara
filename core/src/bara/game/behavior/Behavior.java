package bara.game.behavior;

import com.badlogic.ashley.core.Entity;

public interface Behavior {
    void run(float deltaTime, Entity entity);
}
