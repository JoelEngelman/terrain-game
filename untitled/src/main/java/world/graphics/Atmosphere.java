package world.graphics;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class Atmosphere {

    private final SimpleApplication game;

    private Geometry sky;

    public Atmosphere(SimpleApplication game) {
        this.game = game;
    }

    public void create() {

        Sphere sphere = new Sphere(
            300,
            32,
            32
        );

        sky = new Geometry(
            "Sky",
            sphere
        );

        Material material = new Material(
            game.getAssetManager(),
            "Common/MatDefs/Misc/Unshaded.j3md"
        );

        material.setColor(
            "Color",
            new ColorRGBA(
                0.35f,
                0.65f,
                0.95f,
                1f
            )
        );

        // We are inside the sphere,
        // so render both sides.
        material.getAdditionalRenderState()
            .setFaceCullMode(
                com.jme3.material.RenderState.FaceCullMode.Off
            );

        sky.setMaterial(material);

        sky.setQueueBucket(
            RenderQueue.Bucket.Sky
        );

        sky.setShadowMode(
            RenderQueue.ShadowMode.Off
        );

        game.getRootNode().attachChild(sky);
    }

    public void update() {

        if (sky != null) {

            sky.setLocalTranslation(
                game.getCamera().getLocation()
            );
        }
    }
}