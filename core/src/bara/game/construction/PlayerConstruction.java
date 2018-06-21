package bara.game.construction;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import bara.game.behavior.PlayerBehavior;
import bara.game.components.*;
import bara.game.util.Blackboard;

public class PlayerConstruction {

    public static final float PLAYER_SIZE = 3.0f;

    public static final String PLAYER_COLLISION_FIXTURE = "collision";

    public static final String PLAYER_ANIMATION_1 = "player_animation_1";
    public static final String PLAYER_ANIMATION_2 = "player_animation_2";
    public static final String PLAYER_ENERGY_COUNTER = "player_energy_counter";

    public static final Color PLAYER_OUTLINE_COLOR = new Color(0.06f, 0.06f, 0.12f, 1.0f);
    public static final Color PLAYER_INNER_COLOR = new Color(0.5f, 0.8f, 0.3f, 1f);
    public static final Color PLAYER_ROTATOR_COLOR = new Color(0.3f, 0.4f, 0.1f, 1);
    public static final Color PLAYER_AMMO_COLOR = Color.GREEN;
    public static final Color PLAYER_EMPTY_AMMO_COLOR = Color.CLEAR;

    public static final Vector2[] PLAYER_VERTS = {
        new Vector2(-0.35f * PLAYER_SIZE, 0),
        new Vector2(-0.15f * PLAYER_SIZE, -0.4f * PLAYER_SIZE),
        new Vector2(0.35f * PLAYER_SIZE, -0.1f * PLAYER_SIZE),
        new Vector2(0.35f * PLAYER_SIZE, 0.1f * PLAYER_SIZE),
        new Vector2(-0.15f * PLAYER_SIZE, 0.4f * PLAYER_SIZE)
    };

    public static Entity constructPlayer(PooledEngine engine, float x, float y, boolean addToEngine) {
        Entity player = engine.createEntity();

        return player;
    }
}
