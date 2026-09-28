package world.graphics;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;

public class Lighting {

    private final SimpleApplication game;

    public Lighting(SimpleApplication game) {
        this.game = game;
    }

    public void create() {

        // --------------------------------
        // Soft world light
        // --------------------------------

        AmbientLight ambient = new AmbientLight();

        ambient.setColor(
            new ColorRGBA(
                0.55f,
                0.60f,
                0.55f,
                1f
            )
        );

        game.getRootNode().addLight(ambient);


        // --------------------------------
        // Sun
        // --------------------------------

        DirectionalLight sun = new DirectionalLight();

        sun.setDirection(
            new Vector3f(
                -0.5f,
                -1.0f,
                0.35f
            ).normalizeLocal()
        );

        sun.setColor(
            new ColorRGBA(
                1.0f,
                0.95f,
                0.85f,
                1f
            )
        );

        game.getRootNode().addLight(sun);


        // --------------------------------
        // REAL DYNAMIC SHADOWS
        // --------------------------------

        DirectionalLightShadowRenderer shadows =
            new DirectionalLightShadowRenderer(
                game.getAssetManager(),
                2048,
                3
            );

        shadows.setLight(sun);

        // Controls how the shadow-map space is
        // distributed around the camera.
        shadows.setLambda(0.65f);

        // Make shadows visible without making
        // them completely black.
        shadows.setShadowIntensity(0.75f);

        // Stabilise the shadow edges while moving.
        shadows.setEnabledStabilization(true);

        // Softer shadow edges.
        shadows.setEdgeFilteringMode(
            EdgeFilteringMode.PCFPOISSON
        );

        game.getViewPort().addProcessor(shadows);
    }
}