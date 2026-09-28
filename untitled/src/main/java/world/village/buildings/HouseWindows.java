package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import world.village.VillageMaterials;

public class HouseWindows {

    private final Node windowsNode;

    public HouseWindows(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {

        windowsNode = new Node("House Windows");

        Material glassMaterial =
            createMaterial(
                assetManager,
                new ColorRGBA(
                    0.18f,
                    0.45f,
                    0.55f,
                    1f
                )
            );

        Material frameMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        // =========================
        // FRONT LEFT WINDOW
        // =========================

        createFrontWindow(
            glassMaterial,
            frameMaterial,
            new Vector3f(
                -width * 0.27f,
                height * 0.58f,
                -depth / 2f - 0.06f
            ),
            1.5f,
            1.4f
        );

        // =========================
        // FRONT RIGHT WINDOW
        // =========================

        createFrontWindow(
            glassMaterial,
            frameMaterial,
            new Vector3f(
                width * 0.27f,
                height * 0.58f,
                -depth / 2f - 0.06f
            ),
            1.5f,
            1.4f
        );

        // =========================
        // LEFT SIDE WINDOW
        // =========================

        createSideWindow(
            glassMaterial,
            frameMaterial,
            new Vector3f(
                -width / 2f - 0.06f,
                height * 0.58f,
                0f
            ),
            1.5f,
            1.4f
        );

        // =========================
        // RIGHT SIDE WINDOW
        // =========================

        createSideWindow(
            glassMaterial,
            frameMaterial,
            new Vector3f(
                width / 2f + 0.06f,
                height * 0.58f,
                0f
            ),
            1.5f,
            1.4f
        );
    }

    // =========================
    // FRONT WINDOW
    // =========================

    private void createFrontWindow(
        Material glassMaterial,
        Material frameMaterial,
        Vector3f position,
        float width,
        float height
    ) {

        // Glass
        createPart(
            glassMaterial,
            position,
            width,
            height,
            0.08f
        );

        float frameSize = 0.10f;

        // Top
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y + height / 2f,
                position.z - 0.02f
            ),
            width + frameSize * 2f,
            frameSize,
            0.14f
        );

        // Bottom
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y - height / 2f,
                position.z - 0.02f
            ),
            width + frameSize * 2f,
            frameSize,
            0.14f
        );

        // Left
        createPart(
            frameMaterial,
            new Vector3f(
                position.x - width / 2f,
                position.y,
                position.z - 0.02f
            ),
            frameSize,
            height,
            0.14f
        );

        // Right
        createPart(
            frameMaterial,
            new Vector3f(
                position.x + width / 2f,
                position.y,
                position.z - 0.02f
            ),
            frameSize,
            height,
            0.14f
        );

        // Vertical middle
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y,
                position.z - 0.03f
            ),
            frameSize,
            height,
            0.16f
        );

        // Horizontal middle
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y,
                position.z - 0.03f
            ),
            width,
            frameSize,
            0.16f
        );
    }

    // =========================
    // SIDE WINDOW
    // =========================

    private void createSideWindow(
        Material glassMaterial,
        Material frameMaterial,
        Vector3f position,
        float width,
        float height
    ) {

        // Glass
        createPart(
            glassMaterial,
            position,
            0.08f,
            height,
            width
        );

        float frameSize = 0.10f;

        // Top
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y + height / 2f,
                position.z
            ),
            0.14f,
            frameSize,
            width + frameSize * 2f
        );

        // Bottom
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y - height / 2f,
                position.z
            ),
            0.14f,
            frameSize,
            width + frameSize * 2f
        );

        // Front
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y,
                position.z - width / 2f
            ),
            0.14f,
            height,
            frameSize
        );

        // Back
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y,
                position.z + width / 2f
            ),
            0.14f,
            height,
            frameSize
        );

        // Vertical middle
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y,
                position.z
            ),
            0.16f,
            height,
            frameSize
        );

        // Horizontal middle
        createPart(
            frameMaterial,
            new Vector3f(
                position.x,
                position.y,
                position.z
            ),
            0.16f,
            frameSize,
            width
        );
    }

    // =========================
    // WINDOW PART
    // =========================

    private void createPart(
        Material material,
        Vector3f position,
        float x,
        float y,
        float z
    ) {

        Geometry geometry =
            new Geometry(
                "Window Part",
                new Box(
                    x / 2f,
                    y / 2f,
                    z / 2f
                )
            );

        geometry.setMaterial(material);

        geometry.setLocalTranslation(
            position
        );

        windowsNode.attachChild(
            geometry
        );
    }

    // =========================
    // MATERIAL
    // =========================

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
        return windowsNode;
    }
}