package bara.game.systems;

import bara.game.components.SpriteComponent;
import bara.game.graphics.RenderStage;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.math.Vector2;
import bara.game.components.PositionComponent;
import bara.game.util.LayerComparator;

public class WorldRenderSystem extends SortedIteratingSystem implements RenderStage {

    public static final float PI_OVER_180 = (float)(Math.PI / 180);
    public static final float INV_PI_OVER_180 = (float)(180 / Math.PI);

    private int _width, _height;
    private float _deltaTime;
    private ImmediateModeRenderer _renderer;
    private OrthographicCamera _camera;

    public WorldRenderSystem(int width, int height, ImmediateModeRenderer renderer, OrthographicCamera worldCamera) {
        super(Family.all(SpriteComponent.class, PositionComponent.class).get(), new LayerComparator());
        resize(width, height);
        _renderer = renderer;
        _camera = worldCamera;
        _deltaTime = 0;
    }

    @Override
    public void update(float deltaTime) {
        _renderer.begin(_camera.combined, GL20.GL_TRIANGLE_STRIP);
        super.update(_deltaTime);
        _renderer.end();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //TODO: implement
    }

    @Override
    public Texture getTexture() {
        return null; //TODO: implement
    }

    @Override
    public void resize(int width, int height) {
        _width = width;
        _height = height;
    }

    private void drawVert(Vector2 vert, Color color) {
        _renderer.color(color);
        _renderer.vertex(vert.x, vert.y, 0);
    }
}
