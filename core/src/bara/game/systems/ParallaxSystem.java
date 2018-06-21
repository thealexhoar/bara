package bara.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import bara.game.components.ParallaxComponent;

public class ParallaxSystem extends IteratingSystem {

    public ParallaxSystem() {
        super(Family.all(ParallaxComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
