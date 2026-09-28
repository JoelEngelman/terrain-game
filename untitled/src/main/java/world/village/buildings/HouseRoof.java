package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import world.village.VillageMaterials;

public class HouseRoof {

    private final Node roofNode;

    public HouseRoof(
        AssetManager assetManager,
        float width,
        float depth,
        float wallHeight
    ) {

        roofNode = new Node("House Roof");

        Material roofMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.ROOF_BROWN
            );

        Material ridgeMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        /*
         * ============================
         * ROOF EAVE / BASE
         * ============================
         */

        float eaveWidth = width + 1.2f;
        float eaveDepth = depth + 1.2f;
        float eaveHeight = 0.35f;

        Geometry eave =
            new Geometry(
                "Roof Eave",
                new Box(
                    eaveWidth / 2f,
                    eaveHeight / 2f,
                    eaveDepth / 2f
                )
            );

        eave.setMaterial(roofMaterial);

        eave.setLocalTranslation(
            0f,
            wallHeight + eaveHeight / 2f,
            0f
        );

        roofNode.attachChild(eave);

        /*
         * ============================
         * ROOF DIMENSIONS
         * ============================
         */

        float roofWidth = width + 0.8f;
        float roofDepth = depth + 0.8f;
        float roofHeight = 2.5f;

        float bottomY =
            wallHeight + eaveHeight;

        float halfWidth =
            roofWidth / 2f;

        float halfDepth =
            roofDepth / 2f;

        /*
         * ============================
         * LEFT SLOPE
         * ============================
         */

        createRoofPiece(
            roofMaterial,
            "Left Roof",
            new Vector3f(
                -halfWidth,
                bottomY,
                -halfDepth
            ),
            new Vector3f(
                0f,
                bottomY + roofHeight,
                -halfDepth
            ),
            new Vector3f(
                0f,
                bottomY + roofHeight,
                halfDepth
            ),
            new Vector3f(
                -halfWidth,
                bottomY,
                halfDepth
            )
        );

        /*
         * ============================
         * RIGHT SLOPE
         * ============================
         */

        createRoofPiece(
            roofMaterial,
            "Right Roof",
            new Vector3f(
                0f,
                bottomY + roofHeight,
                -halfDepth
            ),
            new Vector3f(
                halfWidth,
                bottomY,
                -halfDepth
            ),
            new Vector3f(
                halfWidth,
                bottomY,
                halfDepth
            ),
            new Vector3f(
                0f,
                bottomY + roofHeight,
                halfDepth
            )
        );

        /*
         * ============================
         * RIDGE
         * ============================
         */

        Geometry ridge =
            new Geometry(
                "Roof Ridge",
                new Box(
                    0.18f,
                    0.18f,
                    halfDepth
                )
            );

        ridge.setMaterial(ridgeMaterial);

        ridge.setLocalTranslation(
            0f,
            bottomY + roofHeight,
            0f
        );

        roofNode.attachChild(ridge);
    }

    private void createRoofPiece(
        Material material,
        String name,
        Vector3f a,
        Vector3f b,
        Vector3f c,
        Vector3f d
    ) {

        Vector3f[] vertices = {
            a,
            b,
            c,
            d
        };

        int[] indices = {
            0, 1, 2,
            0, 2, 3
        };

        com.jme3.scene.Mesh mesh =
            new com.jme3.scene.Mesh();

        mesh.setBuffer(
            com.jme3.scene.VertexBuffer.Type.Position,
            3,
            com.jme3.util.BufferUtils.createFloatBuffer(
                vertices
            )
        );

        mesh.setBuffer(
            com.jme3.scene.VertexBuffer.Type.Index,
            3,
            com.jme3.util.BufferUtils.createIntBuffer(
                indices
            )
        );

        mesh.updateBound();

        Geometry geometry =
            new Geometry(
                name,
                mesh
            );

        geometry.setMaterial(material);

        roofNode.attachChild(
            geometry
        );
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
        return roofNode;
    }
}