package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import com.jme3.scene.shape.Box;

import world.village.VillageMaterials;

public class House {

    private final Node house;

    public House(
        AssetManager assetManager,
        Vector3f position,
        float width,
        float depth,
        float height
    ) {

        house = new Node("House");

        // =========================
        // WALLS
        // =========================

        Material wallMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.WARM_OAK
            );

        Geometry walls =
            new Geometry(
                "Walls",
                new Box(
                    width / 2f,
                    height / 2f,
                    depth / 2f
                )
            );

        walls.setMaterial(wallMaterial);

        walls.setLocalTranslation(
            0f,
            height / 2f,
            0f
        );

        house.attachChild(walls);

        // =========================
        // WOODEN CORNER BEAMS
        // =========================

        Material beamMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        createBeam(
            house,
            beamMaterial,
            new Vector3f(
                -width / 2f + 0.2f,
                height / 2f,
                -depth / 2f
            ),
            0.2f,
            height / 2f,
            0.2f
        );

        createBeam(
            house,
            beamMaterial,
            new Vector3f(
                width / 2f - 0.2f,
                height / 2f,
                -depth / 2f
            ),
            0.2f,
            height / 2f,
            0.2f
        );

        createBeam(
            house,
            beamMaterial,
            new Vector3f(
                -width / 2f + 0.2f,
                height / 2f,
                depth / 2f
            ),
            0.2f,
            height / 2f,
            0.2f
        );

        createBeam(
            house,
            beamMaterial,
            new Vector3f(
                width / 2f - 0.2f,
                height / 2f,
                depth / 2f
            ),
            0.2f,
            height / 2f,
            0.2f
        );

        // =========================
        // ROOF
        // =========================

        Material roofMaterial =
            createMaterial(
                assetManager,
                VillageMaterials.ROOF_BROWN
            );

        createRoof(
            house,
            roofMaterial,
            width,
            depth,
            height
        );

        house.setLocalTranslation(position);
    }

    // =========================
    // WOODEN BEAM
    // =========================

    private void createBeam(
        Node parent,
        Material material,
        Vector3f position,
        float x,
        float y,
        float z
    ) {

        Geometry beam =
            new Geometry(
                "Wood Beam",
                new Box(x, y, z)
            );

        beam.setMaterial(material);

        beam.setLocalTranslation(
            position
        );

        parent.attachChild(beam);
    }

    // =========================
    // TRIANGULAR ROOF
    // =========================

    private void createRoof(
        Node parent,
        Material material,
        float width,
        float depth,
        float wallHeight
    ) {

        float halfWidth =
            width / 2f + 0.7f;

        float halfDepth =
            depth / 2f + 0.7f;

        float roofHeight = 2.5f;

        float y =
            wallHeight;

        Vector3f[] vertices = {

            // Front triangle
            new Vector3f(
                -halfWidth,
                y,
                -halfDepth
            ),

            new Vector3f(
                halfWidth,
                y,
                -halfDepth
            ),

            new Vector3f(
                0f,
                y + roofHeight,
                -halfDepth
            ),

            // Back triangle
            new Vector3f(
                -halfWidth,
                y,
                halfDepth
            ),

            new Vector3f(
                halfWidth,
                y,
                halfDepth
            ),

            new Vector3f(
                0f,
                y + roofHeight,
                halfDepth
            )
        };

        int[] indices = {

            // Left roof slope
            0, 3, 5,
            0, 5, 2,

            // Right roof slope
            1, 2, 5,
            1, 5, 4,

            // Front triangle
            0, 2, 1,

            // Back triangle
            3, 4, 5
        };

        Mesh mesh =
            new Mesh();

        mesh.setBuffer(
            VertexBuffer.Type.Position,
            3,
            BufferUtils.createFloatBuffer(
                vertices
            )
        );

        mesh.setBuffer(
            VertexBuffer.Type.Index,
            3,
            BufferUtils.createIntBuffer(
                indices
            )
        );

        mesh.updateBound();

        Geometry roof =
            new Geometry(
                "Roof",
                mesh
            );

        roof.setMaterial(material);

        parent.attachChild(roof);
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
        return house;
    }
}