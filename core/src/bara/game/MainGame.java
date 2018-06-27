package bara.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import bara.game.graphics.RenderPipeline;
import bara.game.systems.*;
import bara.game.util.AssetManager;
import bara.game.util.InputManager;
import bara.game.util.KeyBind;

import static bara.game.construction.PlayerConstructionKt.constructPlayer;

public class MainGame extends ApplicationAdapter {
    private int _width, _height;
    private PooledEngine _engine;
    private RenderPipeline _renderPipeline;
    private SpriteBatch _spriteBatch;
    private ImmediateModeRenderer _renderer;
    private World _world;

    public MainGame(int width, int height) {
        _width = width;
        _height = height;

    }

    @Override
    public void create () {
        _engine = new PooledEngine(2048, 8192, 2048, 8192);
        _renderPipeline = new RenderPipeline(_width, _height);
        _spriteBatch = new SpriteBatch(); //TODO: create a default shader
        _world = new World(new Vector2(0, 0), false);

        AssetManager.init();
        InputManager.init();
        InputManager.setCurrentKeyval(KeyBind.Up, Input.Keys.W);
        InputManager.setCurrentKeyval(KeyBind.Down, Input.Keys.S);
        InputManager.setCurrentKeyval(KeyBind.Left, Input.Keys.A);
        InputManager.setCurrentKeyval(KeyBind.Right, Input.Keys.D);
        InputManager.setCurrentKeyval(KeyBind.Pause, Input.Keys.ESCAPE);
        InputManager.setCurrentKeyval(KeyBind.Special, Input.Keys.SPACE);

        InputManager.setAllowHoldMouse(false);

        _renderer = new ImmediateModeRenderer20(false, true, 0);

        OrthographicCamera simpleCamera = new OrthographicCamera(_width / PhysicsSystem.METER, _height / PhysicsSystem.METER);
        simpleCamera.position.set(0, 0, 0);
        simpleCamera.update();

        InputManager.setWorldCamera(simpleCamera);

        CameraSystem cameraSystem = new CameraSystem(simpleCamera);
        WorldRenderSystem worldRenderSystem = new WorldRenderSystem(
            _width, _height, _renderer, simpleCamera);
        DebugRenderSystem debugRenderSystem = new DebugRenderSystem(_width, _height, _spriteBatch, _world, simpleCamera);

        _engine.addSystem(cameraSystem);
        _engine.addSystem(worldRenderSystem);
        _engine.addSystem(debugRenderSystem);
        _engine.addSystem(new PhysicsSystem(_world, 300));
        _engine.addSystem(new BehaviorSystem());

        _renderPipeline.append(debugRenderSystem);

        constructPlayer(_engine, 0f, 0f, true);
    }

    @Override
    public void render () {
        //update
        float deltaTime = 1/60f;// Gdx.bara.game.graphics.getDeltaTime();
        //TODO: use fixed timestep and interpolation
        //render
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //Gdx.gl.glClearColor(0.6f, 0.1f, 0.4f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        InputManager.update();
        _engine.update(deltaTime);

        _renderPipeline.render(_spriteBatch);
    }

    @Override
    public void resize (int width, int height) {
        _width = width;
        _height = height;
    }

    @Override
    public void pause () {
        System.out.println("Pause");
    }

    @Override
    public void resume () {
        System.out.println("Resume");
    }

    @Override
    public void dispose () {
    }
}
