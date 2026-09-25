attribute vec3 inPosition;
attribute vec3 inNormal;

uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldMatrix;
uniform mat3 g_NormalMatrix;

varying vec3 worldPosition;
varying vec3 worldNormal;

void main() {

    vec4 position =
        g_WorldMatrix * vec4(inPosition, 1.0);

    worldPosition = position.xyz;

    worldNormal =
        normalize(g_NormalMatrix * inNormal);

    gl_Position =
        g_WorldViewProjectionMatrix *
        vec4(inPosition, 1.0);
}