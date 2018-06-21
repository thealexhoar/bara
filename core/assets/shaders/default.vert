attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord;
uniform mat4 u_projTrans;
varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
    v_color = a_color;
    v_color.a = v_color.a * (255.0 / 254.0);
    v.texCoords = a_texCoord;
    gl_position = u_projTrans * a_position;
}