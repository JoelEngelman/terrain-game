package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import world.village.VillageMaterials;

public class House {

    private final Node house;

    public House(
        AssetManager assetManager,
        Vector3f position,
        float width,
        float depth,
        float height,
        HouseStyle style
    ) {

        house = new Node("House");

        Material wallMaterial =
            createMaterial(
                assetManager,
                getWallColor(style)
            );

        Material beamMaterial =
            createMaterial(
                assetManager,
                getBeamColor(style)
            );

        /*
         * ============================
         * WALLS
         * ============================
         */

        float wallThickness = 0.25f;

        // Back
        createBox(
            "Back Wall",
            wallMaterial,
            new Vector3f(
                0f,
                height / 2f,
                depth / 2f
            ),
            width,
            height,
            wallThickness
        );

        // Left
        createBox(
            "Left Wall",
            wallMaterial,
            new Vector3f(
                -width / 2f,
                height / 2f,
                0f
            ),
            wallThickness,
            height,
            depth
        );

        // Right
        createBox(
            "Right Wall",
            wallMaterial,
            new Vector3f(
                width / 2f,
                height / 2f,
                0f
            ),
            wallThickness,
            height,
            depth
        );

        /*
         * ============================
         * FRONT WALL + DOOR OPENING
         * ============================
         */

        float doorWidth = 1.8f;
        float doorHeight = 3.0f;

        float sideWidth =
            (width - doorWidth) / 2f;

        createBox(
            "Front Wall Left",
            wallMaterial,
            new Vector3f(
                -(doorWidth / 2f + sideWidth / 2f),
                height / 2f,
                -depth / 2f
            ),
            sideWidth,
            height,
            wallThickness
        );

        createBox(
            "Front Wall Right",
            wallMaterial,
            new Vector3f(
                doorWidth / 2f + sideWidth / 2f,
                height / 2f,
                -depth / 2f
            ),
            sideWidth,
            height,
            wallThickness
        );

        if (height > doorHeight) {

            createBox(
                "Front Wall Above Door",
                wallMaterial,
                new Vector3f(
                    0f,
                    doorHeight +
                    (height - doorHeight) / 2f,
                    -depth / 2f
                ),
                doorWidth,
                height - doorHeight,
                wallThickness
            );
        }

        /*
         * ============================
         * CORNER POSTS
         * ============================
         */

        float postSize = 0.25f;

        createBeam(
            beamMaterial,
            new Vector3f(
                -width / 2f + postSize / 2f,
                height / 2f,
                -depth / 2f
            ),
            postSize,
            height,
            postSize
        );

        createBeam(
            beamMaterial,
            new Vector3f(
                width / 2f - postSize / 2f,
                height / 2f,
                -depth / 2f
            ),
            postSize,
            height,
            postSize
        );

        createBeam(
            beamMaterial,
            new Vector3f(
                -width / 2f + postSize / 2f,
                height / 2f,
                depth / 2f
            ),
            postSize,
            height,
            postSize
        );

        createBeam(
            beamMaterial,
            new Vector3f(
                width / 2f - postSize / 2f,
                height / 2f,
                depth / 2f
            ),
            postSize,
            height,
            postSize
        );

        /*
         * ============================
         * ROOF
         * ============================
         */

        HouseRoof roof =
            new HouseRoof(
                assetManager,
                width,
                depth,
                height
            );

        house.attachChild(
            roof.getNode()
        );

        /*
         * ============================
         * DOOR
         * ============================
         */

        HouseDoors doors =
            new HouseDoors(
                assetManager,
                width,
                depth,
                height
            );

        house.attachChild(
            doors.getNode()
        );

        /*
         * ============================
         * WINDOWS
         * ============================
         */

        HouseWindows windows =
            new HouseWindows(
                assetManager,
                width,
                depth,
                height
            );

        house.attachChild(
            windows.getNode()
        );

        /*
         * ============================
         * STYLE-SPECIFIC EXTERIOR
         * ============================
         */

        HouseExterior exterior =
            new HouseExterior(
                assetManager,
                width,
                depth,
                height,
                style
            );

        house.attachChild(
            exterior.getNode()
        );

        /*
         * ============================
         * FINAL POSITION
         * ============================
         */

        house.setLocalTranslation(
            position
        );
    }

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

        geometry.setMaterial(
            material
        );

        geometry.setLocalTranslation(
            position
        );

        house.attachChild(
            geometry
        );
    }

    private void createBeam(
        Material material,
        Vector3f position,
        float width,
        float height,
        float depth
    ) {

        createBox(
            "Structural Beam",
            material,
            position,
            width,
            height,
            depth
        );
    }

    private ColorRGBA getWallColor(
        HouseStyle style
    ) {

        switch (style) {

            case MODERN:
                return VillageMaterials.LIGHT_OAK;

            case BLACKSMITH:
                return VillageMaterials.DARK_OAK;

            case FARMHOUSE:
                return VillageMaterials.BIRCH;

            case SHOP:
                return VillageMaterials.RED_WOOD;

            case COTTAGE:
            default:
                return VillageMaterials.WARM_OAK;
        }
    }

    private ColorRGBA getBeamColor(
        HouseStyle style
    ) {

        switch (style) {

            case MODERN:
                return VillageMaterials.OAK;

            case BLACKSMITH:
                return VillageMaterials.DARK_STONE;

            case FARMHOUSE:
                return VillageMaterials.OAK;

            case SHOP:
                return VillageMaterials.DARK_OAK;

            case COTTAGE:
            default:
                return VillageMaterials.DARK_OAK;
        }
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
        return house;
    }
}