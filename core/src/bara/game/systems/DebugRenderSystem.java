package bara.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import bara.game.graphics.CustomBox2DRenderer;
import bara.game.graphics.RenderStage;
import bara.game.util.AssetManager;


public class DebugRenderSystem extends EntitySystem implements RenderStage {
    public static final boolean DRAW_BODIES = true;
    public static final boolean DRAW_JOINTS = true;
    public static final boolean DRAW_AABBS = false;
    public static final boolean DRAW_INACTIVE_BODIES = true;
    public static final boolean DRAW_VELOCITIES = false;
    public static final boolean DRAW_CONTACTS = true;
    public static final float DRAW_WIDTH = 1f / 16;

    private static final int FPS_HISTORY_COUNT = 15;

    private FrameBuffer _frameBuffer;
    private int _width, _height;
    private World _world;
    private OrthographicCamera _camera, _nullCamera;
    private Box2DDebugRenderer _debugRenderer;
    private CustomBox2DRenderer _customDebugRenderer;
    private SpriteBatch _spriteBatch;
    private BitmapFont _font;
    private Array<Float> _frameTimeHistory;
    private int _historyIndex = 0;

    public DebugRenderSystem(int width, int height, SpriteBatch spriteBatch, World world, OrthographicCamera camera) {
        resize(width, height);
        _world = world;
        _camera = camera;
        _nullCamera = new OrthographicCamera(width, height);
        _nullCamera.position.set(width/2, height/2, 0);
        _nullCamera.update();
        _debugRenderer = new Box2DDebugRenderer(
            DRAW_BODIES,
            DRAW_JOINTS,
            DRAW_AABBS,
            DRAW_INACTIVE_BODIES,
            DRAW_VELOCITIES,
            DRAW_CONTACTS
        );
        _customDebugRenderer = new CustomBox2DRenderer(
            DRAW_BODIES,
            DRAW_JOINTS,
            DRAW_AABBS,
            DRAW_INACTIVE_BODIES,
            DRAW_VELOCITIES,
            DRAW_CONTACTS,
            DRAW_WIDTH
        );
        _spriteBatch = spriteBatch;
        _font = AssetManager.getDefaultFont(16);
        _frameTimeHistory = new Array<Float>(FPS_HISTORY_COUNT);
    }

    @Override
    public void update(float deltaTime) {
        if(_frameTimeHistory.size < FPS_HISTORY_COUNT) {
            _frameTimeHistory.add(Gdx.graphics.getDeltaTime());
        }
        else {
            _frameTimeHistory.set(_historyIndex, Gdx.graphics.getDeltaTime());
        }
        _historyIndex++;
        _historyIndex %= FPS_HISTORY_COUNT;


        float frameSum = 0;
        for (int i = 0; i < _frameTimeHistory.size; i++) {
            frameSum += _frameTimeHistory.get(i);
        }
        float frameAverage = frameSum / FPS_HISTORY_COUNT;

        _frameBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //_debugRenderer.render(_world, _camera.combined);
        //_customDebugRenderer.render(_world, _camera.combined);
        _spriteBatch.setProjectionMatrix(_nullCamera.combined);
        _spriteBatch.begin();
        String fpsString = Integer.toString(Math.round(1f / frameAverage));
        _font.draw(_spriteBatch, fpsString, 10, 20);
        _spriteBatch.end();
        _frameBuffer.end();
    }

    @Override
    public Texture getTexture() {
        return _frameBuffer.getColorBufferTexture();
    }

    @Override
    public void resize(int width, int height) {
        _width = width;
        _height = height;
        _frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, _width, _height, false);
    }

}
