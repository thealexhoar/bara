package bara.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetManager {
    private static final String ERR = "ERROR";
    private static final String NULL_PIXEL = "NULL_PIXEL";
    private static final String TEXTURE_ROOT = "core/assets/textures/";
    private static final String SHADER_ROOT = "core/assets/shaders/";
    private static final String FONT_ROOT = "core/assets/fonts/";
    private static final String LEVEL_ROOT = "core/assets/levels/";
    private static HashMap<String, Texture> _textures;
    private static HashMap<String, HashMap<String, TextureRegion>> _textureRegions;
    private static HashMap<String, HashMap<String, Animation>> _textureAnimations;
    private static HashMap<String, ShaderProgram> _shaders;
    private static HashMap<String, HashMap<Integer, BitmapFont>> _fonts;
    private static HashMap<String, FreeTypeFontGenerator> _fontGenerators;

    public static void init() {
        ShaderProgram.pedantic = false;

        _textures = new HashMap<String, Texture>();
        _textures.put(ERR, new Texture(TEXTURE_ROOT + "badlogic.jpg"));

        _textureRegions = new HashMap<String, HashMap<String, TextureRegion>>();
        _textureRegions.put(ERR, new HashMap<String, TextureRegion>());
        _textureRegions.get(ERR).put(ERR, new TextureRegion(_textures.get(ERR)));

        _shaders = new HashMap<String, ShaderProgram>();
        _shaders.put(ERR, new ShaderProgram(
            Gdx.files.internal(SHADER_ROOT + "default.vert"),
            Gdx.files.internal(SHADER_ROOT + "default.frag")
        ));

        _fonts = new HashMap<String, HashMap<Integer, BitmapFont>>();
        _fontGenerators = new HashMap<String, FreeTypeFontGenerator>();
        _fontGenerators.put(ERR, new FreeTypeFontGenerator(
            Gdx.files.internal(FONT_ROOT + "8BitOperatorPlus-Regular.ttf")
        ));

        _textureAnimations = new HashMap<String, HashMap<String, Animation>>();

        Pixmap nullPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        nullPixmap.drawPixel(0, 0, 0xffffffff);
        _textures.put(NULL_PIXEL, new Texture(nullPixmap));

    }

    public static boolean loadTexture(String id, String source) {
        if (_textures.containsKey(id)) {
            return false;
        }
        try {
            Texture texture = new Texture(TEXTURE_ROOT + source);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
            _textures.put(id, texture);
            return true;
        } catch (Exception e) {
            Gdx.app.log("AssetManager", e.getMessage());
            return false;
        }

    }

    public static boolean loadTexture(String id, String source, Map<String, Rectangle> regions) {
        if (_textures.containsKey(id)) {
            return false;
        }
        try {
            Texture texture = new Texture(TEXTURE_ROOT + source);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Nearest);
            _textures.put(id, texture);

            HashMap<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
            for (String key : regions.keySet()) {
                Rectangle rect = regions.get(key);
                textureRegions.put(
                    key,
                    new TextureRegion(texture, (int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height)
                );
            }
            _textureRegions.put(id, textureRegions);

            return true;
        } catch (Exception e) {
            Gdx.app.log("AssetManager", e.getMessage());
            return false;
        }

    }

    public static boolean loadTextureRegion(String id, String regionID, Rectangle region) {
        if (!_textures.containsKey(id)) {
            return false;
        }
        try {
            if(!_textureRegions.containsKey(id)) {
                _textureRegions.put(id, new HashMap<String, TextureRegion>());
            }
            _textureRegions.get(id).put(
                regionID,
                new TextureRegion(
                    _textures.get(id),
                    (int)region.x, (int)region.y,
                    (int)region.width, (int)region.height
                )
            );

            return true;
        } catch (Exception e) {
            Gdx.app.log("AssetManager", e.getMessage());
            return false;
        }

    }

    public static boolean unloadTexture(String id) {
        if (!_textures.containsKey(id)) {
            return false;
        }
        _textures.remove(id);
        _textureRegions.remove(id);
        return true;
    }

    public static Texture getTexture(String id) {
        if (!_textures.containsKey(id)) {
            return _textures.get(ERR);
        }
        return _textures.get(id);
    }

    public static TextureRegion getTextureRegion(String textureId, String regionId) {
        TextureRegion out;
        try {
            out = _textureRegions.get(textureId).get(regionId);
        }catch (Exception e) {
            out = _textureRegions.get(ERR).get(ERR);
        }
        return new TextureRegion(out);
    }

    public static TextureRegion[] getKeyFrames(String textureId, String regionId, int frameWidth, int frameHeight) {
        TextureRegion region;
        try {
            region = _textureRegions.get(textureId).get(regionId);
        }catch (Exception e) {
            return new TextureRegion[0];
        }
        TextureRegion[][] frames = region.split(frameWidth, frameHeight);
        TextureRegion[] out = new TextureRegion[frames.length * frames[0].length];
        int index = 0;
        for (int i = 0; i < frames.length; i++) {
            for (int j = 0; j < frames[i].length; j++) {
                out[index++] = frames[i][j];
            }
        }
        return out;
    }

    public static Texture getDefaultTexture() {
        return _textures.get(ERR);
    }

    public static Texture getNullPixel() {
        return _textures.get(NULL_PIXEL);
    }

    public static boolean loadShader(String id, String vertSource, String fragSource) {
        if (_shaders.containsKey(id)) {
            return false;
        }
        try {
            ShaderProgram shader = new ShaderProgram(
                Gdx.files.internal(SHADER_ROOT + vertSource),
                Gdx.files.internal(SHADER_ROOT + fragSource)
            );
            if(!shader.isCompiled()) {
                throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
            }
            _shaders.put(id, shader);
            return true;
        } catch (Exception e) {
            Gdx.app.log("AssetManager", e.getMessage());
            return false;
        }

    }

    public static boolean unloadShader(String id) {
        if (!_shaders.containsKey(id)) {
            return false;
        }
        _shaders.remove(id);
        return true;
    }

    public static ShaderProgram getShader(String id) {
        if (!_shaders.containsKey(id)) {
            return _shaders.get(ERR);
        }
        return _shaders.get(id);
    }

    public static ShaderProgram getDefaultShader() {
        return _shaders.get(ERR);
    }

    public static boolean loadFont(String id, String source) {
        if(_fontGenerators.containsKey(id)) {
            return false;
        }
        try {
            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(
                Gdx.files.internal(FONT_ROOT + source)
            );
            _fontGenerators.put(id, fontGenerator);
            return true;
        } catch (Exception e) {
            Gdx.app.log("AssetManager", e.getMessage());
            return false;
        }
    }

    public static boolean unloadFont(String id) {
        if (!_fontGenerators.containsKey(id)) {
            return false;
        }
        _fontGenerators.remove(id);
        if(_fonts.containsKey(id)) {
            _fonts.remove(id);
        }
        return true;
    }

    public static BitmapFont getFont(String id, int size) {
        String key = _fontGenerators.containsKey(id) ? id : ERR;
        if(!_fonts.containsKey(key)) {
            _fonts.put(key, new HashMap<Integer, BitmapFont>());
        }
        if(!_fonts.get(key).containsKey(size)) {
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;
            _fonts.get(key).put(size, _fontGenerators.get(key).generateFont(parameter));
        }

        return _fonts.get(key).get(size);
    }

    public static BitmapFont getDefaultFont(int size) {
        return getFont(ERR, size);
    }

    public static boolean loadLevel(String id, String source) {
        return false;
    }

    public static boolean unloadLevel(String id) {
        return false;
    }

    public static List<List<Character>> getLevel(String id) {
        return null;
    }
}
