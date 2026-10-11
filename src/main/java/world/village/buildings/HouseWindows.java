package world.village.buildings;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class HouseWindows {

    private final Node windowsNode = new Node("House Windows");

    public HouseWindows(
        AssetManager assetManager,
        float width,
        float depth,
        float height
    ) {
        Material glass = material(
            assetManager,
            new ColorRGBA(0.20f,0.52f,0.68f,1f), 96f
        );
        Material frame = material(
            assetManager,
            new ColorRGBA(0.07f,0.09f,0.10f,1f), 45f
        );

        // Clean modern front glazing.
        createFront(glass, frame,
            new Vector3f(-width*0.24f,height*0.57f,-depth/2f-0.08f),
            2.1f,1.8f);
        createFront(glass, frame,
            new Vector3f(width*0.25f,height*0.57f,-depth/2f-0.08f),
            2.1f,1.8f);

        // Large side windows.
        createSide(glass, frame,
            new Vector3f(-width/2f-0.08f,height*0.58f,0.2f),
            2.4f,1.7f);
        createSide(glass, frame,
            new Vector3f(width/2f+0.08f,height*0.58f,-0.4f),
            2.4f,1.7f);
    }

    private void createFront(
        Material glass, Material frame,
        Vector3f p, float w, float h
    ) {
        create(glass,p,w,h,0.08f);
        float f=0.09f;
        create(frame,new Vector3f(p.x,p.y+h/2f,p.z-0.05f),w+f*2f,f,0.15f);
        create(frame,new Vector3f(p.x,p.y-h/2f,p.z-0.05f),w+f*2f,f,0.15f);
        create(frame,new Vector3f(p.x-w/2f,p.y,p.z-0.05f),f,h,0.15f);
        create(frame,new Vector3f(p.x+w/2f,p.y,p.z-0.05f),f,h,0.15f);
        create(frame,new Vector3f(p.x,p.y,p.z-0.06f),f,h,0.17f);
    }

    private void createSide(
        Material glass, Material frame,
        Vector3f p, float w, float h
    ) {
        create(glass,p,0.08f,h,w);
        float f=0.09f;
        create(frame,new Vector3f(p.x,p.y+h/2f,p.z),0.15f,f,w+f*2f);
        create(frame,new Vector3f(p.x,p.y-h/2f,p.z),0.15f,f,w+f*2f);
        create(frame,new Vector3f(p.x,p.y,p.z-w/2f),0.15f,h,f);
        create(frame,new Vector3f(p.x,p.y,p.z+w/2f),0.15f,h,f);
    }

    private void create(
        Material material, Vector3f p,
        float x, float y, float z
    ) {
        Geometry g = new Geometry(
            "Modern Window",
            new Box(x/2f,y/2f,z/2f)
        );
        g.setMaterial(material);
        g.setLocalTranslation(p);
        windowsNode.attachChild(g);
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
        return windowsNode;
    }
}