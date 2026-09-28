package world.village;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.shape.Sphere;

public class VillageEnvironment {

    private final SimpleApplication game;
    private final Node node = new Node("Village Environment");
    private float time;

    public VillageEnvironment(SimpleApplication game) {
        this.game = game;
    }

    public void create() {
        Material road = material(new ColorRGBA(0.32f,0.29f,0.25f,1f),4f);
        Material stone = material(new ColorRGBA(0.43f,0.45f,0.45f,1f),8f);
        Material wood = material(new ColorRGBA(0.28f,0.18f,0.12f,1f),10f);
        Material green = material(new ColorRGBA(0.12f,0.36f,0.15f,1f),3f);

        box(road,new Vector3f(0,3.08f,82),8f,0.12f,92f);
        box(road,new Vector3f(95,3.08f,95),82f,0.12f,7f);
        box(stone,new Vector3f(95,3.14f,95),25f,0.18f,25f);

        createFountain(stone);
        createStreetFurniture(stone,wood);
        createTrees(green,wood);
        createStall(new Vector3f(72,3,96),new ColorRGBA(0.18f,0.34f,0.52f,1f));
        createStall(new Vector3f(121,3,96),new ColorRGBA(0.48f,0.28f,0.14f,1f));

        game.getRootNode().attachChild(node);
    }

    private void createFountain(Material stone) {
        Geometry base = new Geometry("Fountain Base",
            new Cylinder(32,32,4.5f,0.45f,true));
        base.setMaterial(stone);
        base.setLocalTranslation(95,3.45f,95);
        node.attachChild(base);

        Material water = material(new ColorRGBA(0.20f,0.58f,0.78f,1f),72f);
        Geometry pool = new Geometry("Fountain Water",
            new Cylinder(32,32,3.8f,0.18f,true));
        pool.setMaterial(water);
        pool.setLocalTranslation(95,3.70f,95);
        node.attachChild(pool);

        Geometry pillar = new Geometry("Fountain Pillar",
            new Cylinder(16,16,0.55f,2.0f,true));
        pillar.setMaterial(stone);
        pillar.setLocalTranslation(95,4.6f,95);
        node.attachChild(pillar);
    }

    private void createStreetFurniture(Material stone,Material wood) {
        for(int i=0;i<8;i++) {
            float x=64+i*8;
            box(wood,new Vector3f(x,3.45f,74),2.8f,0.18f,0.65f);
            box(stone,new Vector3f(x,4.9f,80),0.16f,3.8f,0.16f);

            Geometry light=new Geometry("Street Light",
                new Sphere(12,12,0.28f));
            light.setMaterial(material(
                new ColorRGBA(1f,0.86f,0.55f,1f),100f));
            light.setLocalTranslation(x,6.85f,80);
            node.attachChild(light);
        }
    }

    private void createTrees(Material green,Material wood) {
        float[][] trees={
            {55,3,70},{61,3,126},{130,3,72},{137,3,124},
            {48,3,102},{143,3,99},{72,3,60},{121,3,60}
        };

        for(float[] p:trees) {
            Geometry trunk=new Geometry("Tree Trunk",
                new Cylinder(10,10,0.38f,3.2f));
            trunk.setMaterial(wood);
            trunk.setLocalTranslation(p[0],p[1]+1.6f,p[2]);
            node.attachChild(trunk);

            Geometry crown=new Geometry("Tree Crown",
                new Sphere(20,20,2.3f));
            crown.setMaterial(green);
            crown.setLocalTranslation(p[0],p[1]+4.0f,p[2]);
            crown.setLocalScale(1.15f,1f,1.15f);
            node.attachChild(crown);
        }
    }

    private void createStall(Vector3f p,ColorRGBA roofColor) {
        Material wood=material(new ColorRGBA(0.35f,0.23f,0.14f,1f),8f);
        Material roof=material(roofColor,12f);
        box(wood,new Vector3f(p.x,p.y+0.6f,p.z),3.5f,1.2f,2.2f);
        box(roof,new Vector3f(p.x,p.y+2f,p.z),4f,0.25f,2.7f);
    }

    private void box(Material m,Vector3f p,float w,float h,float d) {
        Geometry g=new Geometry("Village Detail",new Box(w/2f,h/2f,d/2f));
        g.setMaterial(m);
        g.setLocalTranslation(p);
        node.attachChild(g);
    }

    public void update(float tpf) {
        time += tpf;
        node.getChildren().forEach(s -> {
            if(s.getName().equals("Fountain Water")) {
                s.setLocalScale(1f + (float)Math.sin(time*2f)*0.02f,1f,1f);
            }
        });
    }

    private Material material(ColorRGBA c,float shininess) {
        Material m=new Material(game.getAssetManager(),
            "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors",true);
        m.setColor("Ambient",c.mult(0.55f));
        m.setColor("Diffuse",c);
        m.setColor("Specular",ColorRGBA.White);
        m.setFloat("Shininess",shininess);
        return m;
    }
}