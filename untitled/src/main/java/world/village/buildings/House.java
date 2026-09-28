package world.village.buildings;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import world.village.DoorSystem;

public class House {

    private final Node house = new Node("Modern House");

    public House(
        SimpleApplication game,
        Vector3f position,
        float width,
        float depth,
        float height,
        HouseStyle style,
        DoorSystem doorSystem
    ) {
        AssetManager assets=game.getAssetManager();

        Material wall=material(
            assets, wallColor(style), 10f
        );
        Material dark=material(
            assets, new ColorRGBA(0.07f,0.09f,0.10f,1f), 35f
        );

        // Clean modern shell with a genuine front opening.
        float t=0.28f;
        float doorW=1.8f;
        float side=(width-doorW)/2f;

        box(wall,new Vector3f(0,height/2f,depth/2f),
            width,height,t);
        box(wall,new Vector3f(-width/2f,height/2f,0),
            t,height,depth);
        box(wall,new Vector3f(width/2f,height/2f,0),
            t,height,depth);

        box(wall,new Vector3f(
            -(doorW/2f+side/2f),height/2f,-depth/2f
        ),side,height,t);
        box(wall,new Vector3f(
            doorW/2f+side/2f,height/2f,-depth/2f
        ),side,height,t);
        box(wall,new Vector3f(
            0f,doorH(height),-depth/2f
        ),doorW,height-doorH(height),t);

        // Modern corner fins.
        box(dark,new Vector3f(-width/2f+0.18f,height/2f,-depth/2f-0.03f),
            0.22f,height+0.05f,0.22f);
        box(dark,new Vector3f(width/2f-0.18f,height/2f,-depth/2f-0.03f),
            0.22f,height+0.05f,0.22f);

        HouseRoof roof=new HouseRoof(
            assets,width,depth,height,style
        );
        house.attachChild(roof.getNode());

        HouseWindows windows=new HouseWindows(
            assets,width,depth,height
        );
        house.attachChild(windows.getNode());

        HouseDoors doors=new HouseDoors(
            game,assets,width,depth,height,doorSystem
        );
        doors.getNode().setLocalTranslation(0,0,0);
        house.attachChild(doors.getNode());

        HouseInterior interior=new HouseInterior(
            assets,width,depth,height,style
        );
        house.attachChild(interior.getNode());

        // Porch: every house has one, but proportions vary.
        float porchW=width*0.78f;
        box(dark,new Vector3f(0,0.16f,-depth/2f-0.85f),
            porchW,0.32f,1.7f);
        box(dark,new Vector3f(-porchW/2f+0.12f,height*0.34f,-depth/2f-1.55f),
            0.20f,height*0.68f,0.20f);
        box(dark,new Vector3f(porchW/2f-0.12f,height*0.34f,-depth/2f-1.55f),
            0.20f,height*0.68f,0.20f);
        box(dark,new Vector3f(0,height*0.68f,-depth/2f-1.55f),
            porchW,0.18f,0.20f);

        house.setLocalTranslation(position);
    }

    private float doorH(float h){ return Math.min(3.0f,h-0.5f); }

    private ColorRGBA wallColor(HouseStyle style){
        switch(style){
            case FARMHOUSE:
                return new ColorRGBA(0.68f,0.66f,0.58f,1f);
            case BLACKSMITH:
                return new ColorRGBA(0.27f,0.30f,0.31f,1f);
            case SHOP:
                return new ColorRGBA(0.50f,0.56f,0.57f,1f);
            case MODERN:
                return new ColorRGBA(0.80f,0.80f,0.76f,1f);
            default:
                return new ColorRGBA(0.64f,0.61f,0.55f,1f);
        }
    }

    private void box(Material m,Vector3f p,float w,float h,float d){
        Geometry g=new Geometry(
            "House Architecture",new Box(w/2f,h/2f,d/2f)
        );
        g.setMaterial(m);
        g.setLocalTranslation(p);
        house.attachChild(g);
    }

    private Material material(
        AssetManager a,ColorRGBA c,float shininess
    ){
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

    public Node getNode(){return house;}
}