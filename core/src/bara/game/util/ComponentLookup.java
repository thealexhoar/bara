package bara.game.util;

import com.badlogic.ashley.core.ComponentMapper;
import bara.game.components.*;
import com.badlogic.ashley.core.Entity;

public class ComponentLookup {
    private static ComponentMapper<BehaviorComponent> _behavior = ComponentMapper.getFor(BehaviorComponent.class);
    private static ComponentMapper<CameraComponent> _camera = ComponentMapper.getFor(CameraComponent.class);
    private static ComponentMapper<ChildrenComponent> _children = ComponentMapper.getFor(ChildrenComponent.class);
    private static ComponentMapper<CollisionComponent> _collision = ComponentMapper.getFor(CollisionComponent.class);
    private static ComponentMapper<CombatComponent> _combat = ComponentMapper.getFor(CombatComponent.class);
    private static ComponentMapper<CullComponent> _cull = ComponentMapper.getFor(CullComponent.class);
    private static ComponentMapper<InventoryComponent> _inventory = ComponentMapper.getFor(InventoryComponent.class);
    private static ComponentMapper<LayerComponent> _layer = ComponentMapper.getFor(LayerComponent.class);
    private static ComponentMapper<LightComponent> _light = ComponentMapper.getFor(LightComponent.class);
    private static ComponentMapper<ParallaxComponent> _parallax = ComponentMapper.getFor(ParallaxComponent.class);
    private static ComponentMapper<PhysicsComponent> _physics = ComponentMapper.getFor(PhysicsComponent.class);
    private static ComponentMapper<PlayerComponent> _player = ComponentMapper.getFor(PlayerComponent.class);
    private static ComponentMapper<PositionComponent> _position = ComponentMapper.getFor(PositionComponent.class);
    private static ComponentMapper<SpriteComponent> _sprite = ComponentMapper.getFor(SpriteComponent.class);
    private static ComponentMapper<StatsComponent> _stats = ComponentMapper.getFor(StatsComponent.class);


    public static BehaviorComponent behavior(Entity entity) { return _behavior.get(entity); }
    public static CameraComponent camera(Entity entity) { return _camera.get(entity); }
    public static ChildrenComponent children(Entity entity) { return _children.get(entity); }
    public static CollisionComponent collision(Entity entity) { return _collision.get(entity); }
    public static CombatComponent combat(Entity entity) { return _combat.get(entity); }
    public static CullComponent cull(Entity entity) { return _cull.get(entity); }
    public static InventoryComponent inventory(Entity entity) { return _inventory.get(entity); }
    public static LayerComponent layer(Entity entity) { return _layer.get(entity); }
    public static LightComponent light(Entity entity) { return _light.get(entity); }
    public static ParallaxComponent parallax(Entity entity) { return _parallax.get(entity); }
    public static PhysicsComponent physics(Entity entity) { return _physics.get(entity); }
    public static PlayerComponent player(Entity entity) { return _player.get(entity); }
    public static PositionComponent position(Entity entity) { return _position.get(entity); }
    public static SpriteComponent sprite(Entity entity) { return _sprite.get(entity); }
    public static StatsComponent stats(Entity entity) { return _stats.get(entity); }
}
