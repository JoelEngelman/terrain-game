package world.village;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;

public class GrassField {

    private final SimpleApplication game;
    private final Node node=new Node("Living Grass");
    private final List<Node> blades=new ArrayList<>();
    private float time;

    public GrassField(SimpleApplication game){this.game=game;}

    public void create(){
        Material grass=material(
            new ColorRGBA(0.16f,0.48f,0.18f,1f)
        );

        int index=0;
        for(int x=48;x<=142;x+=6){
            for(int z=58;z<=128;z+=6){
                if((x+z)%3==0){
                    Node tuft=new Node("Grass Tuft");
                    float offset=(index%5)*0.12f;

                    Geometry blade=new Geometry(
                        "Grass Blade",
                        new Box(0.045f,0.55f,0.045f)
                    );
                    blade.setMaterial(grass);
                    blade.setLocalTranslation(0,0.55f,0);
                    tuft.attachChild(blade);

                    Geometry blade2=new Geometry(
                        "Grass Blade",
                        new Box(0.045f,0.48f,0.045f)
                    );
                    blade2.setMaterial(grass);
                    blade2.setLocalTranslation(0.13f,0.48f,0.04f);
                    tuft.attachChild(blade2);

                    tuft.setLocalTranslation(
                        x+offset,
                        3.12f,
                        z-offset
                    );

                    node.attachChild(tuft);
                    blades.add(tuft);
                    index++;
                }
            }
        }

        game.getRootNode().attachChild(node);
    }

    public void update(float tpf){
        time+=tpf;
        for(int i=0;i<blades.size();i++){
            Node tuft=blades.get(i);
            float sway=(float)Math.sin(time*2.0f+i*0.37f)*0.09f;
            tuft.setLocalRotation(
                new Quaternion().fromAngles(sway,0f,sway*0.55f)
            );
        }
    }

    private Material material(ColorRGBA color){
        Material m=new Material(
            game.getAssetManager(),
            "Common/MatDefs/Light/Lighting.j3md"
        );
        m.setBoolean("UseMaterialColors",true);
        m.setColor("Ambient",color.mult(0.55f));
        m.setColor("Diffuse",color);
        m.setColor("Specular",ColorRGBA.Black);
        m.setFloat("Shininess",2f);
        return m;
    }
}