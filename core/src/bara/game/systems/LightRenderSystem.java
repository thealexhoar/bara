package bara.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import bara.game.graphics.RenderStage;

public class LightRenderSystem extends EntitySystem implements RenderStage {
    private FrameBuffer _frameBuffer;
    private int _width, _height;

    public LightRenderSystem(int width, int height) {
        super();
        _width = width;
        _height = height;
        _frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public Texture getTexture() {
        return _frameBuffer.getColorBufferTexture();
    }

    @Override
    public void resize(int width, int height) {
        _width = width;
        _height = height;
        _frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
    }
}
