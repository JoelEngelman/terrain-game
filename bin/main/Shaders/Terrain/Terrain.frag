uniform vec4 m_BaseColor;

varying vec3 worldPosition;
varying vec3 worldNormal;

void main() {

    // =========================
    // NORMAL
    // =========================

    vec3 normal = normalize(worldNormal);


    // =========================
    // SUN LIGHT
    // =========================

    vec3 sunDirection =
        normalize(
            vec3(-0.55, 1.0, 0.30)
        );

    float sun =
        max(
            dot(normal, sunDirection),
            0.0
        );

    float directLight =
        0.30 + sun * 0.70;


    // =========================
    // AMBIENT / SKY LIGHT
    // =========================

    float skyLight =
        clamp(
            normal.y,
            0.0,
            1.0
        );

    skyLight =
        0.55 + skyLight * 0.45;


    // =========================
    // HEIGHT
    // =========================

    float height =
        clamp(
            (worldPosition.y + 2.0) / 20.0,
            0.0,
            1.0
        );


    // =========================
    // TERRAIN COLOURS
    // =========================

    // Your requested normal green:
    // RGBA(56, 245, 82, 0.8)
    //
    // Converted to GLSL:
    // RGB = (0.22, 0.96, 0.32)

    vec3 grass =
        vec3(
            0.22,
            0.96,
            0.32
        );

    vec3 lowGrass =
        vec3(
            0.10,
            0.52,
            0.14
        );

    vec3 highGrass =
        vec3(
            0.30,
            1.00,
            0.38
        );


    vec3 terrainColour;

    if (height < 0.35) {

        terrainColour =
            mix(
                lowGrass,
                grass,
                height / 0.35
            );

    } else {

        terrainColour =
            mix(
                grass,
                highGrass,
                (height - 0.35) / 0.65
            );
    }


    // =========================
    // SLOPE SHADING
    // =========================

    float flatness =
        clamp(
            normal.y,
            0.0,
            1.0
        );

    float slopeShade =
        mix(
            0.72,
            1.0,
            flatness
        );

    terrainColour *= slopeShade;


    // =========================
    // TOP LIGHT
    // =========================

    float topLight =
        smoothstep(
            0.35,
            0.95,
            normal.y
        );

    terrainColour *=
        0.88 + topLight * 0.16;


    // =========================
    // MATERIAL
    // =========================

    terrainColour =
        mix(
            terrainColour,
            terrainColour * m_BaseColor.rgb,
            0.12
        );


    // =========================
    // LIGHTING
    // =========================

    float lighting =
        directLight *
        skyLight;

    terrainColour *= lighting;


    // =========================
    // SUBTLE CONTRAST
    // =========================

    // IMPORTANT:
    // This is a vec3 because terrainColour is a vec3.

    vec3 contrast =
        0.5 +
        0.5 * terrainColour;

    terrainColour =
        mix(
            terrainColour,
            contrast,
            0.08
        );


    // =========================
    // FINAL OUTPUT
    // =========================

    gl_FragColor =
        vec4(
            terrainColour,
            1.0
        );
}