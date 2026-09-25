uniform vec4 m_BaseColor;

varying vec3 worldPosition;
varying vec3 worldNormal;

void main() {

    vec3 normal =
        normalize(worldNormal);

    // Sun direction
    vec3 sunDirection =
        normalize(
            vec3(-0.5, 1.0, 0.35)
        );

    float sunlight =
        max(
            dot(normal, sunDirection),
            0.0
        );

    // Stronger lighting contrast
    float lighting =
        0.35 + sunlight * 0.65;

    // Terrain height
    float height =
        clamp(
            (worldPosition.y + 2.0) / 20.0,
            0.0,
            1.0
        );

    // Darker grass at lower elevations
    vec3 lowGreen =
        vec3(
            0.16,
            0.42,
            0.10
        );

    // Brighter grass at higher elevations
    vec3 highGreen =
        vec3(
            0.48,
            0.78,
            0.30
        );

    vec3 terrainColour =
        mix(
            lowGreen,
            highGreen,
            height
        );

    // Preserve the material's main colour
    terrainColour =
        mix(
            terrainColour,
            m_BaseColor.rgb,
            0.35
        );

    // Slightly darken steep slopes
    float slope =
        clamp(
            normal.y,
            0.0,
            1.0
        );

    float slopeLighting =
        0.78 + slope * 0.22;

    terrainColour *=
        lighting * slopeLighting;

    gl_FragColor =
        vec4(
            terrainColour,
            1.0
        );
}