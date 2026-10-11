package world.village;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.List;

public class VillagePeople {

    private final SimpleApplication game;
    private final Node peopleNode=new Node("Villagers");
    private final List<Villager> villagers=new ArrayList<>();

    public VillagePeople(SimpleApplication game){this.game=game;}

    public void create(){
        add("Mara","FARMER",72,84,0.80f,0.35f,0);
        add("Kian","BLACKSMITH",116,104,0.55f,0.14f,1);
        add("Lena","SHOPKEEPER",100,88,0.65f,0.52f,2);
        add("Tavi","MEDIC",83,101,0.52f,0.20f,3);
        add("Noah","FISHER",108,116,0.72f,0.10f,4);
        add("Ari","CARPENTER",68,110,0.42f,0.30f,5);
        add("Mika","COOK",124,88,0.58f,0.68f,6);
        add("Rin","GUARD",92,78,0.38f,0.05f,7);
        game.getRootNode().attachChild(peopleNode);
    }

    private void add(String name,String job,float x,float z,
                     float skin,float hair,int index){
        Villager v=new Villager(name,job,x,z,skin,hair,index);
        villagers.add(v);
        peopleNode.attachChild(v.node);
    }

    public void update(float tpf){
        for(Villager v:villagers) v.update(tpf);
    }

    private class Villager {
        final Node node=new Node("Villager");
        final Vector3f center;
        final float radius;
        final float speed;
        float phase;

        Villager(String name,String job,float x,float z,
                 float skin,float hair,int index){
            center=new Vector3f(x,3.1f,z);
            radius=4.5f+(index%4);
            speed=0.28f+(index%5)*0.06f;
            phase=index*0.8f;

            Material skinM=mat(new ColorRGBA(
                0.42f+skin*0.38f,
                0.28f+skin*0.30f,
                0.18f+skin*0.24f,1f),8f);
            Material hairM=mat(new ColorRGBA(
                hair,Math.max(0.02f,hair*0.72f),
                Math.max(0.01f,hair*0.48f),1f),18f);
            Material clothes=mat(jobColor(job),12f);
            Material dark=mat(new ColorRGBA(0.07f,0.08f,0.09f,1f),25f);

            part(clothes,new Vector3f(0,1.25f,0),0.84f,1.24f,0.56f);
            part(skinM,new Vector3f(0,2.18f,0),0.72f,0.72f,0.72f);
            part(hairM,new Vector3f(0,2.37f,0),0.78f,0.32f,0.78f);

            limb(dark,new Vector3f(-0.22f,0.35f,0),0.15f,0.70f);
            limb(dark,new Vector3f(0.22f,0.35f,0),0.15f,0.70f);
            limb(skinM,new Vector3f(-0.55f,1.25f,0),0.12f,0.72f);
            limb(skinM,new Vector3f(0.55f,1.25f,0),0.12f,0.72f);

            // Job-specific wearable.
            if(job.equals("FARMER")) hat(clothes);
            if(job.equals("BLACKSMITH")) apron(dark);
            if(job.equals("SHOPKEEPER")) hat(clothes);
            if(job.equals("MEDIC")) cross();
            if(job.equals("FISHER")) hat(clothes);
            if(job.equals("CARPENTER")) apron(dark);
            if(job.equals("COOK")) chefHat();
            if(job.equals("GUARD")) hat(dark);
        }

        void update(float tpf){
            phase+=tpf*speed;
            float x=center.x+(float)Math.cos(phase)*radius;
            float z=center.z+(float)Math.sin(phase)*radius;
            node.setLocalTranslation(x,center.y,z);

            float nx=center.x+(float)Math.cos(phase+0.05f)*radius;
            float nz=center.z+(float)Math.sin(phase+0.05f)*radius;
            node.lookAt(new Vector3f(nx,center.y,nz),Vector3f.UNIT_Y);
        }

        void hat(Material m){
            limb(m,new Vector3f(0,2.68f,0),0.48f,0.18f);
        }

        void apron(Material m){
            part(m,new Vector3f(0,1.30f,-0.31f),0.52f,0.75f,0.06f);
        }

        void cross(){
            Material white=mat(ColorRGBA.White,2f);
            part(white,new Vector3f(0,1.45f,-0.31f),0.18f,0.42f,0.06f);
            part(white,new Vector3f(0,1.45f,-0.31f),0.42f,0.18f,0.06f);
        }

        void chefHat(){
            Material white=mat(new ColorRGBA(0.95f,0.95f,0.90f,1f),2f);
            part(white,new Vector3f(0,2.65f,0),0.52f,0.24f,0.52f);
        }

        void limb(Material m,Vector3f p,float r,float h){
            Geometry g=new Geometry("Villager Limb",
                new Cylinder(8,8,r,h));
            g.setMaterial(m);
            g.setLocalTranslation(p);
            node.attachChild(g);
        }

        void part(Material m,Vector3f p,float w,float h,float d){
            Geometry g=new Geometry("Villager Part",
                new Box(w/2f,h/2f,d/2f));
            g.setMaterial(m);
            g.setLocalTranslation(p);
            node.attachChild(g);
        }

        ColorRGBA jobColor(String job){
            switch(job){
                case "FARMER": return new ColorRGBA(0.25f,0.48f,0.25f,1f);
                case "BLACKSMITH": return new ColorRGBA(0.24f,0.27f,0.30f,1f);
                case "SHOPKEEPER": return new ColorRGBA(0.28f,0.40f,0.62f,1f);
                case "MEDIC": return new ColorRGBA(0.78f,0.78f,0.72f,1f);
                case "FISHER": return new ColorRGBA(0.18f,0.38f,0.54f,1f);
                case "CARPENTER": return new ColorRGBA(0.52f,0.32f,0.16f,1f);
                case "COOK": return new ColorRGBA(0.72f,0.35f,0.20f,1f);
                default: return new ColorRGBA(0.32f,0.36f,0.42f,1f);
            }
        }

        Material mat(ColorRGBA c,float s){
            Material m=new Material(game.getAssetManager(),
                "Common/MatDefs/Light/Lighting.j3md");
            m.setBoolean("UseMaterialColors",true);
            m.setColor("Ambient",c.mult(0.55f));
            m.setColor("Diffuse",c);
            m.setColor("Specular",ColorRGBA.White);
            m.setFloat("Shininess",s);
            return m;
        }
    }
}