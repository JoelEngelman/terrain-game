package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import world.village.VillageMaterials;

public class HouseExterior {

    private final Node exteriorNode;

    public HouseExterior(
        AssetManager assetManager,
        float width,
        float depth,
        float height,
        HouseStyle style
    ) {

        exteriorNode = new Node("House Exterior");

        switch (style) {

            case MODERN:
                createModern(
                    assetManager,
                    width,
                    depth,
                    height
                );
                break;

            case FARMHOUSE:
                createFarmhouse(
                    assetManager,
                    width,
                    depth,
                    height
                );
                break;

            case BLACKSMITH:
                createBlacksmith(
                    assetManager,
                    width,
                    depth,
                    height
                );
                break;

            case SHOP:
                createShop(
                    assetManager,
                    width,
                    depth,
                    height
                );
                break;

            case COTTAGE:
            default:
                createCottage(
                    assetManager,
                    width,
                    depth,
                    height
                );
                break;
        }
    }

    // =========================================================
    // MODERN
    // =========================================================

    private void createModern(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {

        Material dark =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        Material light =
            createMaterial(
                assetManager,
                new ColorRGBA(
                    0.82f,
                    0.80f,
                    0.72f,
                    1f
                )
            );

        Material glass =
            createMaterial(
                assetManager,
                new ColorRGBA(
                    0.18f,
                    0.38f,
                    0.48f,
                    1f
                )
            );

        // Large clean front panel
        createBox(
            "Modern Front Panel",
            light,
            new Vector3f(
                0f,
                height * 0.68f,
                -depth / 2f - 0.08f
            ),
            width * 0.72f,
            height * 0.32f,
            0.16f
        );

        // Large glass section
        createBox(
            "Modern Glass Section",
            glass,
            new Vector3f(
                0f,
                height * 0.40f,
                -depth / 2f - 0.10f
            ),
            width * 0.68f,
            height * 0.32f,
            0.08f
        );

        // Strong horizontal overhang
        createBox(
            "Modern Overhang",
            dark,
            new Vector3f(
                width * 0.08f,
                height + 0.45f,
                -depth * 0.02f
            ),
            width * 0.95f,
            0.28f,
            depth * 0.92f
        );

        // Offset upper section
        createBox(
            "Modern Upper Volume",
            light,
            new Vector3f(
                width * 0.16f,
                height * 0.92f,
                depth * 0.05f
            ),
            width * 0.48f,
            height * 0.30f,
            depth * 0.70f
        );

        // Dark vertical architectural column
        createBox(
            "Modern Vertical Column",
            dark,
            new Vector3f(
                -width * 0.36f,
                height * 0.55f,
                -depth / 2f - 0.13f
            ),
            0.28f,
            height * 0.90f,
            0.22f
        );
    }

    // =========================================================
    // COTTAGE
    // =========================================================

    private void createCottage(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {

        Material wood =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        Material stone =
            createMaterial(
                assetManager,
                VillageMaterials.STONE
            );

        // Stone foundation
        createBox(
            "Cottage Foundation",
            stone,
            new Vector3f(
                0f,
                0.25f,
                0f
            ),
            width + 0.25f,
            0.50f,
            depth + 0.25f
        );

        // Front timber beam
        createBox(
            "Cottage Front Beam",
            wood,
            new Vector3f(
                0f,
                height * 0.72f,
                -depth / 2f - 0.13f
            ),
            width * 0.85f,
            0.20f,
            0.20f
        );

        // Small porch
        createBox(
            "Cottage Porch",
            wood,
            new Vector3f(
                0f,
                0.16f,
                -depth / 2f - 0.75f
            ),
            width * 0.55f,
            0.32f,
            1.50f
        );

        // Porch roof
        createBox(
            "Cottage Porch Roof",
            wood,
            new Vector3f(
                0f,
                height * 0.62f,
                -depth / 2f - 0.75f
            ),
            width * 0.68f,
            0.18f,
            1.65f
        );
    }

    // =========================================================
    // FARMHOUSE
    // =========================================================

    private void createFarmhouse(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {

        Material wood =
            createMaterial(
                assetManager,
                VillageMaterials.OAK
            );

        Material dark =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        // Large front porch
        createBox(
            "Farmhouse Porch",
            wood,
            new Vector3f(
                0f,
                0.18f,
                -depth / 2f - 1.0f
            ),
            width * 0.90f,
            0.36f,
            2.0f
        );

        // Porch support posts
        createBox(
            "Farmhouse Porch Post Left",
            dark,
            new Vector3f(
                -width * 0.38f,
                height * 0.35f,
                -depth / 2f - 1.65f
            ),
            0.22f,
            height * 0.70f,
            0.22f
        );

        createBox(
            "Farmhouse Porch Post Right",
            dark,
            new Vector3f(
                width * 0.38f,
                height * 0.35f,
                -depth / 2f - 1.65f
            ),
            0.22f,
            height * 0.70f,
            0.22f
        );

        // Long porch beam
        createBox(
            "Farmhouse Porch Beam",
            dark,
            new Vector3f(
                0f,
                height * 0.70f,
                -depth / 2f - 1.65f
            ),
            width * 0.90f,
            0.22f,
            0.22f
        );
    }

    // =========================================================
    // BLACKSMITH
    // =========================================================

    private void createBlacksmith(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {

        Material stone =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_STONE
            );

        Material metal =
            createMaterial(
                assetManager,
                new ColorRGBA(
                    0.16f,
                    0.16f,
                    0.15f,
                    1f
                )
            );

        // Heavy stone foundation
        createBox(
            "Blacksmith Stone Base",
            stone,
            new Vector3f(
                0f,
                0.40f,
                0f
            ),
            width + 0.35f,
            0.80f,
            depth + 0.35f
        );

        // Workshop awning
        createBox(
            "Blacksmith Awning",
            metal,
            new Vector3f(
                0f,
                height * 0.62f,
                -depth / 2f - 0.80f
            ),
            width * 0.85f,
            0.22f,
            1.30f
        );

        // Heavy entrance frame
        createBox(
            "Blacksmith Entrance Beam",
            metal,
            new Vector3f(
                0f,
                height * 0.70f,
                -depth / 2f - 0.15f
            ),
            width * 0.40f,
            0.30f,
            0.25f
        );

        // Chimney
        createBox(
            "Blacksmith Chimney",
            stone,
            new Vector3f(
                width * 0.28f,
                height + 1.20f,
                depth * 0.18f
            ),
            0.75f,
            2.40f,
            0.75f
        );
    }

    // =========================================================
    // SHOP
    // =========================================================

    private void createShop(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {

        Material wood =
            createMaterial(
                assetManager,
                VillageMaterials.WARM_OAK
            );

        Material dark =
            createMaterial(
                assetManager,
                VillageMaterials.DARK_OAK
            );

        Material glass =
            createMaterial(
                assetManager,
                new ColorRGBA(
                    0.20f,
                    0.48f,
                    0.55f,
                    1f
                )
            );

        // Wide storefront
        createBox(
            "Shop Storefront",
            glass,
            new Vector3f(
                0f,
                height * 0.42f,
                -depth / 2f - 0.12f
            ),
            width * 0.72f,
            height * 0.48f,
            0.08f
        );

        // Shop sign
        createBox(
            "Shop Sign",
            wood,
            new Vector3f(
                0f,
                height + 0.55f,
                -depth / 2f - 0.30f
            ),
            width * 0.65f,
            0.70f,
            0.16f
        );

        // Sign support
        createBox(
            "Shop Sign Support",
            dark,
            new Vector3f(
                0f,
                height + 0.15f,
                -depth / 2f - 0.24f
            ),
            0.16f,
            0.70f,
            0.16f
        );

        // Store awning
        createBox(
            "Shop Awning",
            dark,
            new Vector3f(
                0f,
                height * 0.72f,
                -depth / 2f - 0.75f
            ),
            width * 0.90f,
            0.18f,
            1.20f
        );
    }

    // =========================================================
    // BOX
    // =========================================================

    private void createBox(
        String name,
        Material material,
        Vector3f position,
        float width,
        float height,
        float depth
    ) {

        Geometry geometry =
            new Geometry(
                name,
                new Box(
                    width / 2f,
                    height / 2f,
                    depth / 2f
                )
            );

        geometry.setMaterial(material);
        geometry.setLocalTranslation(position);

        exteriorNode.attachChild(
            geometry
        );
    }

    // =========================================================
    // MATERIAL
    // =========================================================

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
        return exteriorNode;
    }
}