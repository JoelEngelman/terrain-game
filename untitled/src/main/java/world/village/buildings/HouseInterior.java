package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import world.village.VillageMaterials;

public class HouseInterior {

    private final Node interiorNode = new Node("House Interior");

    public HouseInterior(
        AssetManager assetManager,
        float width,
        float depth,
        float height,
        HouseStyle style
    ) {
        Material floor = material(
            assetManager,
            new ColorRGBA(0.30f,0.23f,0.18f,1f), 8f
        );
        Material cream = material(
            assetManager,
            new ColorRGBA(0.78f,0.76f,0.69f,1f), 4f
        );
        Material dark = material(
            assetManager,
            new ColorRGBA(0.10f,0.12f,0.13f,1f), 30f
        );
        Material accent = material(
            assetManager,
            accent(style), 20f
        );

        create(floor, new Vector3f(0f,0.08f,0f),
            width-0.25f,0.16f,depth-0.25f);

        create(cream, new Vector3f(0f,height-0.15f,0f),
            width-0.25f,0.20f,depth-0.25f);

        // Sofa
        create(accent, new Vector3f(-width*0.24f,0.65f,depth*0.18f),
            width*0.35f,0.8f,0.95f);
        create(cream, new Vector3f(-width*0.24f,1.15f,depth*0.18f),
            width*0.35f,0.20f,0.95f);

        // Coffee table
        create(dark, new Vector3f(0f,0.48f,depth*0.08f),
            1.3f,0.16f,0.75f);

        // Kitchen island
        create(dark, new Vector3f(width*0.27f,0.62f,-depth*0.18f),
            1.8f,0.85f,0.85f);

        // Bed / bedroom furniture at rear
        create(cream, new Vector3f(-width*0.24f,0.42f,depth*0.34f),
            2.4f,0.55f,3.0f);

        // Wall art
        create(accent, new Vector3f(0f,2.0f,-depth/2f+0.16f),
            2.0f,1.25f,0.10f);
    }

    private ColorRGBA accent(HouseStyle style) {
        switch (style) {
            case FARMHOUSE:
                return new ColorRGBA(0.20f,0.42f,0.30f,1f);
            case BLACKSMITH:
                return new ColorRGBA(0.48f,0.20f,0.12f,1f);
            case SHOP:
                return new ColorRGBA(0.20f,0.32f,0.48f,1f);
            case MODERN:
                return new ColorRGBA(0.16f,0.42f,0.52f,1f);
            default:
                return new ColorRGBA(0.48f,0.30f,0.18f,1f);
        }
    }

    private void create(
        Material material,
        Vector3f position,
        float width,
        float height,
        float depth
    ) {
        Geometry g = new Geometry(
            "Interior Detail",
            new Box(width/2f,height/2f,depth/2f)
        );
        g.setMaterial(material);
        g.setLocalTranslation(position);
        interiorNode.attachChild(g);
    }

    private Material material(
        AssetManager manager,
        ColorRGBA color,
        float shininess
    ) {
        Material m = new Material(
            manager,
            "Common/MatDefs/Light/Lighting.j3md"
        );
        m.setBoolean("UseMaterialColors",true);
        m.setColor("Ambient",color.mult(0.55f));
        m.setColor("Diffuse",color);
        m.setColor("Specular",ColorRGBA.White);
        m.setFloat("Shininess",shininess);
        return m;
    }

    public Node getNode() {
        return interiorNode;
    }
}