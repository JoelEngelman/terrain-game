package world.graphics;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
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
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.48f, 0.56f, 0.68f, 1f));
        game.getRootNode().addLight(ambient);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.45f, -1f, 0.25f).normalizeLocal());
        sun.setColor(new ColorRGBA(1.0f, 0.93f, 0.82f, 1f));
        game.getRootNode().addLight(sun);

        DirectionalLightShadowRenderer shadows =
            new DirectionalLightShadowRenderer(
                game.getAssetManager(), 2048, 3
            );

        shadows.setLight(sun);
        shadows.setLambda(0.65f);
        shadows.setShadowIntensity(0.62f);
        shadows.setEnabledStabilization(true);
        shadows.setEdgeFilteringMode(EdgeFilteringMode.PCFPOISSON);
        game.getViewPort().addProcessor(shadows);

        addFillLight(0f, 12f, 0f, 0.22f, 0.30f, 0.45f);
    }

    private void addFillLight(
        float x, float y, float z,
        float r, float g, float b
    ) {
        PointLight light = new PointLight();
        light.setPosition(new Vector3f(x, y, z));
        light.setColor(new ColorRGBA(r, g, b, 1f));
        light.setRadius(80f);
        game.getRootNode().addLight(light);
    }
}