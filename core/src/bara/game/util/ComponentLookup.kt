package bara.game.util

import bara.game.ashley.ChildrenComponent
import bara.game.ashley.ParentComponent
import com.badlogic.ashley.core.ComponentMapper
import bara.game.components.*
import com.badlogic.ashley.core.Entity

object ComponentLookup {
    private val _behavior = ComponentMapper.getFor(BehaviorComponent::class.java)
    private val _camera = ComponentMapper.getFor(CameraComponent::class.java)
    private val _children = ComponentMapper.getFor(ChildrenComponent::class.java)
    private val _collision = ComponentMapper.getFor(CollisionComponent::class.java)
    private val _combat = ComponentMapper.getFor(CombatComponent::class.java)
    private val _layer = ComponentMapper.getFor(LayerComponent::class.java)
    private val _light = ComponentMapper.getFor(LightComponent::class.java)
    private val _parallax = ComponentMapper.getFor(ParallaxComponent::class.java)
    private val _parent = ComponentMapper.getFor(ParentComponent::class.java)
    private val _physics = ComponentMapper.getFor(PhysicsComponent::class.java)
    private val _player = ComponentMapper.getFor(PlayerComponent::class.java)
    private val _position = ComponentMapper.getFor(PositionComponent::class.java)
    private val _sprite = ComponentMapper.getFor(SpriteComponent::class.java)


    fun behavior(entity: Entity): BehaviorComponent? {
        return _behavior.get(entity)
    }

    fun camera(entity: Entity): CameraComponent? {
        return _camera.get(entity)
    }

    fun children(entity: Entity): ChildrenComponent? {
        return _children.get(entity)
    }

    fun collision(entity: Entity): CollisionComponent? {
        return _collision.get(entity)
    }

    fun combat(entity: Entity): CombatComponent? {
        return _combat.get(entity)
    }

    fun layer(entity: Entity): LayerComponent? {
        return _layer.get(entity)
    }

    fun light(entity: Entity): LightComponent? {
        return _light.get(entity)
    }

    fun parallax(entity: Entity): ParallaxComponent? {
        return _parallax.get(entity)
    }

    fun parent(entity: Entity): ParentComponent? {
        return _parent.get(entity)
    }

    fun physics(entity: Entity): PhysicsComponent? {
        return _physics.get(entity)
    }

    fun player(entity: Entity): PlayerComponent? {
        return _player.get(entity)
    }

    fun position(entity: Entity): PositionComponent? {
        return _position.get(entity)
    }

    fun sprite(entity: Entity): SpriteComponent? {
        return _sprite.get(entity)
    }
}
