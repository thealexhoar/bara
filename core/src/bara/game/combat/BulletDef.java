package bara.game.combat;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import bara.game.behavior.Behavior;

public class BulletDef {
    public Vector2 velocity;
    public Vector2 position;
    public Behavior behavior;
    public Array<Effect> effects;

    public BulletDef() {
        velocity = new Vector2();
        position = new Vector2();
        behavior = null;
        effects = new Array<Effect>();
    }
}
