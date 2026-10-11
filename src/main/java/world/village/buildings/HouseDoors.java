package world.village.buildings;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import world.village.DoorSystem;
import world.village.VillageMaterials;

public class HouseDoors {

    private final Node doorsNode = new Node("House Doors");
    private final Node hinge = new Node("Door Hinge");
    private final SimpleApplication game;
    private final float depth;

    private float angle;
    private float targetAngle;

    public HouseDoors(
        SimpleApplication game,
        AssetManager assetManager,
        float width,
        float depth,
        float height,
        DoorSystem doorSystem
    ) {
        this.game = game;
        this.depth = depth;

        Material doorMaterial = material(
            assetManager, VillageMaterials.DARK_OAK, 12f
        );
        Material frameMaterial = material(
            assetManager, new ColorRGBA(0.08f,0.10f,0.11f,1f), 28f
        );
        Material metal = material(
            assetManager, new ColorRGBA(0.72f,0.56f,0.25f,1f), 80f
        );

        float w = 1.8f;
        float h = 3.0f;
        float frontZ = -depth / 2f - 0.16f;

        hinge.setLocalTranslation(
            -w / 2f, h / 2f, frontZ
        );

        Geometry door = new Geometry(
            "Interactive Door",
            new Box(w / 2f, h / 2f, 0.10f)
        );
        door.setMaterial(doorMaterial);
        door.setLocalTranslation(w / 2f, 0f, 0f);
        hinge.attachChild(door);

        doorsNode.attachChild(hinge);

        createBox(
            frameMaterial, new Vector3f(-w/2f-0.14f,h/2f,frontZ),
            0.18f,h+0.2f,0.22f
        );
        createBox(
            frameMaterial, new Vector3f(w/2f+0.14f,h/2f,frontZ),
            0.18f,h+0.2f,0.22f
        );
        createBox(
            frameMaterial, new Vector3f(0f,h+0.1f,frontZ),
            w+0.45f,0.18f,0.22f
        );

        Geometry handle = new Geometry(
            "Door Handle",
            new Box(0.08f,0.08f,0.08f)
        );
        handle.setMaterial(metal);
        handle.setLocalTranslation(
            w/2f + 0.15f, 0f, -0.15f
        );
        hinge.attachChild(handle);

        doorSystem.register(this);
    }

    private void createBox(
        Material material,
        Vector3f position,
        float x, float y, float z
    ) {
        Geometry g = new Geometry(
            "Door Frame",
            new Box(x/2f,y/2f,z/2f)
        );
        g.setMaterial(material);
        g.setLocalTranslation(position);
        doorsNode.attachChild(g);
    }

    public void toggle() {
        targetAngle =
            Math.abs(targetAngle) < 0.1f ? -1.25f : 0f;
    }

    public void update(float tpf) {
        float difference = targetAngle - angle;
        angle += difference * Math.min(1f, tpf * 9f);

        hinge.setLocalRotation(
            new Quaternion().fromAngles(0f, angle, 0f)
        );
    }

    public Vector3f getWorldPosition() {
        return doorsNode.getWorldTranslation().clone();
    }

    public Node getNode() {
        return doorsNode;
    }

    private Material material(
        AssetManager manager,
        ColorRGBA color,
        float shininess
    ) {
        Material material = new Material(
            manager,
            "Common/MatDefs/Light/Lighting.j3md"
        );
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", color.mult(0.55f));
        material.setColor("Diffuse", color);
        material.setColor("Specular", ColorRGBA.White);
        material.setFloat("Shininess", shininess);
        return material;
    }
}