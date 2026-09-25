package world.graphics;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;

public class Lighting {

    private final SimpleApplication game;

    public Lighting(SimpleApplication game) {
        this.game = game;
    }

    public void create() {

        // Soft overall light so shadows aren't black.
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(
            new ColorRGBA(
                0.65f,
                0.65f,
                0.65f,
                1f
            )
        );

        game.getRootNode().addLight(ambient);

        // Main sunlight.
        DirectionalLight sun = new DirectionalLight();

        sun.setDirection(
            new com.jme3.math.Vector3f(
                -0.5f,
                -1.0f,
                0.35f
            ).normalize()
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
    }
}