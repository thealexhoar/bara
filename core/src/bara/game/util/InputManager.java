package bara.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import sun.util.resources.cldr.en.CalendarData_en_MH;

import java.util.HashMap;

public class InputManager {
    private static HashMap<KeyBind, Integer> _keyBinds;
    private static HashMap<KeyBind, Boolean> _currentKeyStates;
    private static HashMap<KeyBind, Boolean> _oldKeyStates;
    private static Vector2 _mouseDelta;
    private static boolean _holdMouse;
    private static boolean _allowHoldMouse;

    private static OrthographicCamera _worldCamera = null;

    public static void init() {
        _keyBinds = new HashMap<KeyBind, Integer>();
        _currentKeyStates = new HashMap<KeyBind, Boolean>();
        _oldKeyStates = new HashMap<KeyBind, Boolean>();

        _mouseDelta = new Vector2(0,0);

        _holdMouse = true;
        _allowHoldMouse = false;

    }

    public static void loadFromConfig() {
        //TODO: load config keybinds here
    }

    public static void writeToConfig() {
        //TODO: write config keybinds here
    }

    public static void update() {
        for(KeyBind keyBind : _keyBinds.keySet()) {
            int keyVal = _keyBinds.get(keyBind);
            _oldKeyStates.put(keyBind, _currentKeyStates.get(keyBind));
            _currentKeyStates.put(keyBind, Gdx.input.isKeyPressed(keyVal));
        }

        if(_allowHoldMouse && _holdMouse) {
            _mouseDelta
                .set(Gdx.input.getDeltaX(), Gdx.input.getDeltaY())
                .scl(1f / Gdx.graphics.getBackBufferWidth(), 1f / Gdx.graphics.getBackBufferHeight());
            Gdx.input.setCursorPosition(
                Gdx.graphics.getBackBufferWidth()/2,
                Gdx.graphics.getBackBufferHeight()/2
            );
            Gdx.input.setCursorCatched(true);
        }
        else {
            _mouseDelta.set(0,0);
            Gdx.input.setCursorCatched(false);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)) {
            _holdMouse = !_holdMouse;
        }
    }

    public static boolean isKeyDown(KeyBind key) {
        return _currentKeyStates.get(key);
    }

    public static boolean isKeyUp(KeyBind key) {
        return !_currentKeyStates.get(key);
    }

    public static boolean isKeyPressed(KeyBind key) {
        return _currentKeyStates.get(key) && !_oldKeyStates.get(key);
    }

    public static boolean isKeyReleased(KeyBind key) {
        return !_currentKeyStates.get(key) && _oldKeyStates.get(key);
    }

    public static int getCurrentKeyval(KeyBind keyBind) {
        return _keyBinds.get(keyBind);
    }

    public static void setCurrentKeyval(KeyBind keyBind, int val) {
        if(!_keyBinds.containsKey(keyBind)) {
            _currentKeyStates.put(keyBind, false);
            _oldKeyStates.put(keyBind, false);
        }
        _keyBinds.put(keyBind, val);
    }

    public static void toggleAllowHoldMouse() {
        _allowHoldMouse = !_allowHoldMouse;
    }

    public static void setAllowHoldMouse(boolean hold) {
        _allowHoldMouse = hold;
    }

    public static boolean isHoldingMouse() {
        return _allowHoldMouse && _holdMouse;
    }

    public static Vector2 getMouseDelta() {
        return _mouseDelta;//.cpy().scl(1, -1);
    }

    public static Vector2 getWorldMouseDelta() {
        if (_worldCamera != null) {
            return _mouseDelta.cpy().scl(_worldCamera.viewportWidth, -_worldCamera.viewportHeight);
        }
        else {
            return new Vector2(0, 0);
        }
    }

    public static void setWorldCamera(OrthographicCamera camera) {
        _worldCamera = camera;
    }

    public static Vector2 getWorldMousePos() {
        if (_worldCamera != null) {
            Vector2 out = new Vector2();
            out = getMouseNormalPos().add(-0.5f, -0.5f);
            out.x *= _worldCamera.viewportWidth;
            out.y *= _worldCamera.viewportHeight;
            out.x += _worldCamera.position.x;
            out.y += _worldCamera.position.y;

            return out;
        }
        else {
            return new Vector2(0, 0);
        }
    }

    public static void setWorldMousePos(Vector2 pos) {
        if (_worldCamera != null) {
            pos.x -= _worldCamera.position.x;
            pos.y -= _worldCamera.position.y;
            pos.x /= _worldCamera.viewportWidth;
            pos.y /= _worldCamera.viewportHeight;
            pos.x += 0.5f;
            pos.y += 0.5f;
            setMouseNormalPos(pos);
        }
    }

    public static Vector2 getMouseNormalPos() {
        return new Vector2(
            1.0f * Gdx.input.getX() / Gdx.graphics.getBackBufferWidth(),
            1.0f - 1.0f * Gdx.input.getY() / Gdx.graphics.getBackBufferHeight()
        );
    }

    public static void setMouseNormalPos(Vector2 pos) {
        int x = (int)(pos.x * Gdx.graphics.getBackBufferWidth());
        int y = (int)((1f - pos.y) * Gdx.graphics.getBackBufferHeight());
        Gdx.input.setCursorPosition(x, y);
    }
}
