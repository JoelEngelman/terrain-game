package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.util.BufferUtils;

public class HouseRoof {

    private final Node roofNode = new Node("House Roof");

    public HouseRoof(
        AssetManager assetManager,
        float width,
        float depth,
        float wallHeight,
        HouseStyle style
    ) {
        // Every building keeps the same clean modern roof language.
        // Profession-specific details make the shop and blacksmith distinct
        // without turning their buildings into old-fashioned houses.
        modern(assetManager, width, depth, wallHeight);

        if (style == HouseStyle.SHOP) {
            shopFeature(assetManager, width, depth, wallHeight);
        } else if (style == HouseStyle.BLACKSMITH) {
            industrialFeature(assetManager, width, depth, wallHeight);
        } else if (style == HouseStyle.FARMHOUSE) {
            gardenFeature(assetManager, width, depth, wallHeight);
        }
    }

    private void modern(
        AssetManager a, float w, float d, float h
    ) {
        Material dark = material(
            a, new ColorRGBA(0.06f,0.08f,0.09f,1f), 35f
        );
        Material light = material(
            a, new ColorRGBA(0.72f,0.74f,0.72f,1f), 18f
        );

        box(dark, new Vector3f(0,h+0.16f,0),
            w+1.1f,0.32f,d+1.1f);

        box(light, new Vector3f(w*0.14f,h+0.48f,0.08f),
            w*0.56f,0.42f,d*0.72f);

        box(dark, new Vector3f(-w*0.18f,h+0.75f,-d*0.08f),
            w*0.50f,0.18f,d*0.85f);
    }

    private void shopFeature(
        AssetManager a, float w, float d, float h
    ) {
        Material awning = material(
            a, new ColorRGBA(0.13f,0.20f,0.22f,1f), 24f
        );
        box(awning, new Vector3f(0,h*0.68f,-d/2f-0.42f),
            w*0.82f,0.18f,0.95f);
    }

    private void industrialFeature(
        AssetManager a, float w, float d, float h
    ) {
        Material metal = material(
            a, new ColorRGBA(0.10f,0.12f,0.13f,1f), 28f
        );
        box(metal, new Vector3f(w*0.28f,h+1.8f,d*0.15f),
            0.75f,2.8f,0.75f);
        box(metal, new Vector3f(w*0.28f,h+3.25f,d*0.15f),
            1.0f,0.18f,1.0f);
    }

    private void gardenFeature(
        AssetManager a, float w, float d, float h
    ) {
        Material planter = material(
            a, new ColorRGBA(0.25f,0.31f,0.20f,1f), 4f
        );
        Material foliage = material(
            a, new ColorRGBA(0.20f,0.48f,0.22f,1f), 2f
        );
        box(planter, new Vector3f(-w*0.25f,h+0.85f,d*0.12f),
            w*0.22f,0.24f,d*0.28f);
        box(foliage, new Vector3f(-w*0.25f,h+1.02f,d*0.12f),
            w*0.18f,0.12f,d*0.22f);
    }

    // Retained for existing styles/future use, with normals supplied so the
    // mesh remains compatible with jMonkeyEngine's Lighting material.
    private void lowGable(
        AssetManager a, float w, float d, float h, HouseStyle style
    ) {
        Material roof = material(
            a,
            style == HouseStyle.FARMHOUSE
                ? new ColorRGBA(0.18f,0.22f,0.20f,1f)
                : new ColorRGBA(0.24f,0.15f,0.10f,1f),
            14f
        );

        box(roof,new Vector3f(0,h+0.16f,0),
            w+1.0f,0.32f,d+1.0f);

        float over = w/2f+0.45f;
        float halfD = d/2f+0.45f;
        float top = h+2.35f;

        createSlope(roof,"Roof Left",
            new Vector3f(-over,h+0.32f,-halfD),
            new Vector3f(0,top,-halfD),
            new Vector3f(0,top,halfD),
            new Vector3f(-over,h+0.32f,halfD));

        createSlope(roof,"Roof Right",
            new Vector3f(0,top,-halfD),
            new Vector3f(over,h+0.32f,-halfD),
            new Vector3f(over,h+0.32f,halfD),
            new Vector3f(0,top,halfD));
    }

    private void createSlope(
        Material m, String name,
        Vector3f a, Vector3f b, Vector3f c, Vector3f d
    ) {
        Mesh mesh = new Mesh();
        Vector3f normal = b.subtract(a).cross(c.subtract(a)).normalizeLocal();
        mesh.setBuffer(
            VertexBuffer.Type.Position, 3,
            BufferUtils.createFloatBuffer(new Vector3f[]{a,b,c,d})
        );
        mesh.setBuffer(
            VertexBuffer.Type.Normal, 3,
            BufferUtils.createFloatBuffer(
                new Vector3f[]{normal,normal,normal,normal}
            )
        );
        mesh.setBuffer(
            VertexBuffer.Type.Index, 3,
            BufferUtils.createIntBuffer(new int[]{0,1,2,0,2,3})
        );
        mesh.updateBound();
        mesh.updateCounts();

        Geometry g = new Geometry(name,mesh);
        g.setMaterial(m);
        roofNode.attachChild(g);
    }

    private void box(
        Material m, Vector3f p, float w, float h, float d
    ) {
        Geometry g = new Geometry(
            "Roof Detail", new Box(w/2f,h/2f,d/2f)
        );
        g.setMaterial(m);
        g.setLocalTranslation(p);
        roofNode.attachChild(g);
    }

    private Material material(
        AssetManager a, ColorRGBA c, float shininess
    ) {
        Material m = new Material(
            a, "Common/MatDefs/Light/Lighting.j3md"
        );
        m.setBoolean("UseMaterialColors",true);
        m.setColor("Ambient",c.mult(0.55f));
        m.setColor("Diffuse",c);
        m.setColor("Specular",ColorRGBA.White);
        m.setFloat("Shininess",shininess);
        return m;
    }

    public Node getNode() { return roofNode; }
}
