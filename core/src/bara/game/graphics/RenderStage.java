package bara.game.graphics;

import com.badlogic.gdx.graphics.Texture;

public interface RenderStage {
    Texture getTexture();
    void resize(int width, int height);
}
