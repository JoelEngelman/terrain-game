package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import world.village.VillageMaterials;

public class HouseDoors {

    private final Node doorsNode;

    public HouseDoors(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {

        doorsNode = new Node("House Doors");

        // =========================
        // DOOR
        // =========================

        Material doorMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        float doorWidth = 1.6f;
        float doorHeight = 3.0f;
        float doorDepth = 0.12f;

        Geometry door =
            new Geometry(
                "Front Door",
                new Box(
                    doorWidth / 2f,
                    doorHeight / 2f,
                    doorDepth / 2f
                )
            );

        door.setMaterial(doorMaterial);

        door.setLocalTranslation(
            0f,
            doorHeight / 2f,
            -depth / 2f - 0.08f
        );

        doorsNode.attachChild(door);

        // =========================
        // DOOR FRAME
        // =========================

        Material frameMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.OAK
            );

        // Left frame
        createPart(
            doorsNode,
            frameMaterial,
            new Vector3f(
                -doorWidth / 2f - 0.12f,
                doorHeight / 2f,
                -depth / 2f - 0.15f
            ),
            0.12f,
            doorHeight / 2f + 0.1f,
            0.15f
        );

        // Right frame
        createPart(
            doorsNode,
            frameMaterial,
            new Vector3f(
                doorWidth / 2f + 0.12f,
                doorHeight / 2f,
                -depth / 2f - 0.15f
            ),
            0.12f,
            doorHeight / 2f + 0.1f,
            0.15f
        );

        // Top frame
        createPart(
            doorsNode,
            frameMaterial,
            new Vector3f(
                0f,
                doorHeight + 0.1f,
                -depth / 2f - 0.15f
            ),
            doorWidth / 2f + 0.24f,
            0.12f,
            0.15f
        );

        // =========================
        // DOOR HANDLE
        // =========================

        Material handleMaterial =
            createMaterial(
                assetManager,
                new ColorRGBA(
                    0.85f,
                    0.65f,
                    0.20f,
                    1f
                )
            );

        Geometry handle =
            new Geometry(
                "Door Handle",
                new Box(
                    0.08f,
                    0.08f,
                    0.08f
                )
            );

        handle.setMaterial(handleMaterial);

        handle.setLocalTranslation(
            0.45f,
            doorHeight / 2f,
            -depth / 2f - 0.25f
        );

        doorsNode.attachChild(handle);
    }

    private void createPart(
        Node parent,
        Material material,
        Vector3f position,
        float x,
        float y,
        float z
    ) {

        Geometry part =
            new Geometry(
                "Door Frame",
                new Box(x, y, z)
            );

        part.setMaterial(material);

        part.setLocalTranslation(position);

        parent.attachChild(part);
    }

    private Material createMaterial(
        AssetManager assetManager,
        ColorRGBA color
    ) {

        Material material =
            new Material(
                assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md"
            );

        material.setColor(
            "Color",
            color
        );

        return material;
    }

    public Node getNode() {
        return doorsNode;
    }
}