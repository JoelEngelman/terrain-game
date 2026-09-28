package world;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import world.terrain.TerrainSettings;
import world.village.Village;

public class World {

    private final SimpleApplication game;
    private final TerrainGenerator terrainGenerator;
    private final Village village;
    private Material terrainMaterial;

    public World(SimpleApplication game) {
        this.game=game;
        terrainGenerator=new TerrainGenerator();
        village=new Village(game);
    }

    public void create() {
        createMaterial();
        createTerrain();
        village.create();
    }

    public void update(float tpf) {
        if (terrainMaterial != null) {
            float current=terrainMaterial.getParam("Time") == null
                ? 0f
                : terrainMaterial.getParam("Time").getValue().getClass() == Float.class
                    ? (Float)terrainMaterial.getParam("Time").getValue()
                    : 0f;
            terrainMaterial.setFloat("Time",current+tpf);
        }
        village.update(tpf);
    }

    private void createMaterial() {
        terrainMaterial=new Material(
            game.getAssetManager(),
            "Shaders/Terrain/Terrain.j3md"
        );
        terrainMaterial.setColor(
            "BaseColor",
            new ColorRGBA(0.24f,0.58f,0.22f,1f)
        );
        terrainMaterial.setFloat("Time",0f);
    }

    private void createTerrain() {
        int minX=TerrainSettings.MIN_X;
        int maxX=TerrainSettings.MAX_X;
        int minZ=TerrainSettings.MIN_Z;
        int maxZ=TerrainSettings.MAX_Z;

        int width=maxX-minX+1;
        int depth=maxZ-minZ+1;

        float[] positions=new float[width*depth*3];
        float[] normals=new float[width*depth*3];

        int index=0;
        for(int z=minZ;z<=maxZ;z++) {
            for(int x=minX;x<=maxX;x++) {
                positions[index*3]=(float)x;
                positions[index*3+1]=terrainGenerator.getHeight(x,z)-0.5f;
                positions[index*3+2]=(float)z;
                index++;
            }
        }

        // Smooth normals from the height field.
        index=0;
        for(int z=minZ;z<=maxZ;z++) {
            for(int x=minX;x<=maxX;x++) {
                float left=terrainGenerator.getHeight(x-1,z);
                float right=terrainGenerator.getHeight(x+1,z);
                float down=terrainGenerator.getHeight(x,z-1);
                float up=terrainGenerator.getHeight(x,z+1);

                com.jme3.math.Vector3f normal=
                    new com.jme3.math.Vector3f(
                        left-right,
                        2f,
                        down-up
                    ).normalizeLocal();

                normals[index*3]=normal.x;
                normals[index*3+1]=normal.y;
                normals[index*3+2]=normal.z;
                index++;
            }
        }

        int quads=(width-1)*(depth-1);
        int[] indices=new int[quads*6];
        int i=0;

        for(int z=0;z<depth-1;z++) {
            for(int x=0;x<width-1;x++) {
                int a=z*width+x;
                int b=a+1;
                int c=a+width;
                int d=c+1;

                indices[i++]=a;
                indices[i++]=c;
                indices[i++]=b;
                indices[i++]=b;
                indices[i++]=c;
                indices[i++]=d;
            }
        }

        Mesh mesh=new Mesh();

        mesh.setBuffer(
            VertexBuffer.Type.Position,3,
            BufferUtils.createFloatBuffer(positions)
        );

        mesh.setBuffer(
            VertexBuffer.Type.Normal,3,
            BufferUtils.createFloatBuffer(normals)
        );

        mesh.setBuffer(
            VertexBuffer.Type.Index,3,
            BufferUtils.createIntBuffer(indices)
        );

        mesh.updateBound();
        mesh.updateCounts();

        Geometry terrain=new Geometry("Terrain",mesh);
        terrain.setMaterial(terrainMaterial);
        terrain.setShadowMode(
            com.jme3.renderer.queue.RenderQueue.ShadowMode.CastAndReceive
        );

        game.getRootNode().attachChild(terrain);
    }
}