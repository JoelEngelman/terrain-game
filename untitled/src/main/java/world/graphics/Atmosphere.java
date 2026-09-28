package world.graphics;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.renderer.queue.RenderQueue;
import java.util.ArrayList;
import java.util.List;

public class Atmosphere {

    private final SimpleApplication game;
    private Geometry sky;
    private final Node clouds = new Node("Clouds");
    private final List<Cloud> cloudList = new ArrayList<>();

    private static class Cloud {
        Node node;
        float speed;
        float phase;
        Cloud(Node node, float speed, float phase) {
            this.node = node;
            this.speed = speed;
            this.phase = phase;
        }
    }

    public Atmosphere(SimpleApplication game) {
        this.game = game;
    }

    public void create() {
        createSky();
        createClouds();
        game.getRootNode().attachChild(clouds);
    }

    private void createSky() {
        Sphere sphere = new Sphere(64, 64, 450f);
        sky = new Geometry("Sky Dome", sphere);

        Material material = new Material(
            game.getAssetManager(),
            "Common/MatDefs/Misc/Unshaded.j3md"
        );
        material.setColor(
            "Color",
            new ColorRGBA(0.34f, 0.66f, 0.96f, 1f)
        );
        material.getAdditionalRenderState().setFaceCullMode(
            com.jme3.material.RenderState.FaceCullMode.Off
        );

        sky.setMaterial(material);
        sky.setQueueBucket(RenderQueue.Bucket.Sky);
        sky.setShadowMode(RenderQueue.ShadowMode.Off);
        game.getRootNode().attachChild(sky);
    }

    private void createClouds() {
        Material cloudMaterial = new Material(
            game.getAssetManager(),
            "Common/MatDefs/Misc/Unshaded.j3md"
        );
        cloudMaterial.setColor(
            "Color",
            new ColorRGBA(0.97f, 0.98f, 1f, 1f)
        );

        float[][] positions = {
            {-120f, 105f, -70f, 1.5f},
            {-20f, 125f, -110f, 1.0f},
            {100f, 115f, -40f, 1.3f},
            {145f, 130f, 80f, 0.9f},
            {-150f, 118f, 100f, 1.2f},
            {30f, 140f, 145f, 1.1f}
        };

        for (int i = 0; i < positions.length; i++) {
            Node cloud = new Node("Cloud " + i);

            for (int j = 0; j < 5; j++) {
                float x = (j - 2) * 4.0f;
                float y = (j % 2) * 1.2f;
                float z = (j % 3) * 1.5f;

                Geometry puff = new Geometry(
                    "Cloud Puff",
                    new Sphere(16, 16, 3.5f + (j % 2))
                );
                puff.setMaterial(cloudMaterial);
                puff.setLocalTranslation(x, y, z);
                puff.setLocalScale(1.25f, 0.55f, 0.85f);
                cloud.attachChild(puff);
            }

            cloud.setLocalTranslation(
                positions[i][0],
                positions[i][1],
                positions[i][2]
            );

            clouds.attachChild(cloud);
            cloudList.add(
                new Cloud(cloud, positions[i][3], i * 0.8f)
            );
        }
    }

    public void update(float tpf) {
        Vector3f camera = game.getCamera().getLocation();
        sky.setLocalTranslation(camera);

        for (Cloud cloud : cloudList) {
            cloud.node.move(cloud.speed * tpf, 0f, 0f);
            if (cloud.node.getLocalTranslation().x > 260f) {
                cloud.node.setLocalTranslation(
                    -260f,
                    cloud.node.getLocalTranslation().y,
                    cloud.node.getLocalTranslation().z
                );
            }
        }
    }
}