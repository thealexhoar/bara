package bara.game.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class RenderPipeline {
    private Array<RenderStage> _stages;
    private int _width, _height;
    private OrthographicCamera _nullCamera;

    public RenderPipeline(int width, int height) {
        _stages = new Array<RenderStage>();
        _width = width;
        _height = height;
        _nullCamera = new OrthographicCamera(1, 1);
        _nullCamera.position.set(0.5f, 0.5f, 0);
        _nullCamera.update();
    }

    public void append(RenderStage stage) {
        _stages.add(stage);
    }

    public void insert(int index, RenderStage stage) {
        _stages.insert(index, stage);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(_nullCamera.combined);
        spriteBatch.begin();
        for(int i = 0; i < _stages.size; i++) {
            spriteBatch.draw(
                _stages.get(i).getTexture(),
                0, 1,
                1, -1 //TODO: check if the vertical inversion is necessary
            );
        }
        spriteBatch.end();
    }

    public void resize(int width, int height) {
        for(int i = 0; i < _stages.size; i++) {
            _stages.get(i).resize(width, height);
        }
        width = width;
        height = height;
    }
}
