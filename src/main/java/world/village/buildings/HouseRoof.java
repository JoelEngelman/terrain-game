package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class HouseRoof {

    private final Node roofNode = new Node("House Roof");

    public HouseRoof(
        AssetManager assetManager,
        float width,
        float depth,
        float wallHeight,
        HouseStyle style
    ) {
        switch(style) {
            case MODERN:
                modern(assetManager,width,depth,wallHeight);
                break;
            case SHOP:
                canopy(assetManager,width,depth,wallHeight);
                break;
            case BLACKSMITH:
                industrial(assetManager,width,depth,wallHeight);
                break;
            default:
                lowGable(assetManager,width,depth,wallHeight,style);
        }
    }

    private void modern(
        AssetManager a,float w,float d,float h
    ) {
        Material dark=material(a,new ColorRGBA(0.06f,0.08f,0.09f,1f),35f);
        Material light=material(a,new ColorRGBA(0.72f,0.74f,0.72f,1f),18f);

        box(dark,new Vector3f(0,h+0.16f,0),
            w+1.1f,0.32f,d+1.1f);

        box(light,new Vector3f(w*0.14f,h+0.48f,0.08f),
            w*0.56f,0.42f,d*0.72f);

        box(dark,new Vector3f(-w*0.18f,h+0.75f,-d*0.08f),
            w*0.50f,0.18f,d*0.85f);
    }

    private void lowGable(
        AssetManager a,float w,float d,float h,HouseStyle style
    ) {
        Material roof=material(a,
            style==HouseStyle.FARMHOUSE
                ? new ColorRGBA(0.18f,0.22f,0.20f,1f)
                : new ColorRGBA(0.24f,0.15f,0.10f,1f),
            14f);

        box(roof,new Vector3f(0,h+0.16f,0),
            w+1.0f,0.32f,d+1.0f);

        float over=w/2f+0.45f;
        float halfD=d/2f+0.45f;
        float top=h+2.35f;

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

    private void canopy(
        AssetManager a,float w,float d,float h
    ) {
        Material roof=material(a,new ColorRGBA(0.08f,0.11f,0.13f,1f),28f);
        box(roof,new Vector3f(0,h+0.16f,0),
            w+1.0f,0.30f,d+1.0f);
        box(roof,new Vector3f(0,h+0.70f,-d*0.58f),
            w+1.5f,0.18f,1.3f);
        box(roof,new Vector3f(0,h+0.95f,-d*0.35f),
            w*0.72f,0.12f,0.9f);
    }

    private void industrial(
        AssetManager a,float w,float d,float h
    ) {
        Material roof=material(a,new ColorRGBA(0.10f,0.11f,0.11f,1f),24f);
        box(roof,new Vector3f(0,h+0.18f,0),
            w+1.2f,0.36f,d+1.2f);
        box(roof,new Vector3f(0,h+0.65f,0),
            w*0.92f,0.25f,d*0.88f);
        box(roof,new Vector3f(w*0.28f,h+1.8f,d*0.15f),
            0.75f,2.8f,0.75f);
    }

    private void createSlope(
        Material m,String name,
        Vector3f a,Vector3f b,Vector3f c,Vector3f d
    ) {
        com.jme3.scene.Mesh mesh=new com.jme3.scene.Mesh();
        mesh.setBuffer(
            com.jme3.scene.VertexBuffer.Type.Position,3,
            com.jme3.util.BufferUtils.createFloatBuffer(
                new Vector3f[]{a,b,c,d}
            )
        );
        mesh.setBuffer(
            com.jme3.scene.VertexBuffer.Type.Index,3,
            com.jme3.util.BufferUtils.createIntBuffer(
                new int[]{0,1,2,0,2,3}
            )
        );
        mesh.updateBound();
        Geometry g=new Geometry(name,mesh);
        g.setMaterial(m);
        roofNode.attachChild(g);
    }

    private void box(
        Material m,Vector3f p,float w,float h,float d
    ) {
        Geometry g=new Geometry(
            "Roof Detail",new Box(w/2f,h/2f,d/2f)
        );
        g.setMaterial(m);
        g.setLocalTranslation(p);
        roofNode.attachChild(g);
    }

    private Material material(
        AssetManager a,ColorRGBA c,float shininess
    ) {
        Material m=new Material(
            a,"Common/MatDefs/Light/Lighting.j3md"
        );
        m.setBoolean("UseMaterialColors",true);
        m.setColor("Ambient",c.mult(0.55f));
        m.setColor("Diffuse",c);
        m.setColor("Specular",ColorRGBA.White);
        m.setFloat("Shininess",shininess);
        return m;
    }

    public Node getNode(){return roofNode;}
}