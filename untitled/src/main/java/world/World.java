package world;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

public class World {

    private final SimpleApplication game;
    private final TerrainGenerator terrainGenerator;

    private Material terrainMaterial;

    private static final int MIN_X = -60;
    private static final int MAX_X = 60;
    private static final int MIN_Z = -60;
    private static final int MAX_Z = 60;

    public World(SimpleApplication game) {
        this.game = game;
        this.terrainGenerator = new TerrainGenerator();
    }

    public void create() {

        createMaterial();
        createTerrain();
    }

    private void createMaterial() {

        terrainMaterial = new Material(
            game.getAssetManager(),
            "Shaders/Terrain/Terrain.j3md"
        );

        terrainMaterial.setColor(
            "BaseColor",
            new ColorRGBA(
                129f / 255f,
                255f / 255f,
                114f / 255f,
                1f
            )
        );
    }

    private void createTerrain() {

        int width = MAX_X - MIN_X + 1;
        int depth = MAX_Z - MIN_Z + 1;

        int vertexCount = width * depth;

        float[] positions =
            new float[vertexCount * 3];

        float[] normals =
            new float[vertexCount * 3];

        int quadCount =
            (width - 1) * (depth - 1);

        int[] indices =
            new int[quadCount * 6];

        // --------------------------------
        // Create vertices
        // --------------------------------

        for (int z = MIN_Z; z <= MAX_Z; z++) {

            for (int x = MIN_X; x <= MAX_X; x++) {

                int vertex =
                    getVertexIndex(
                        x,
                        z,
                        width
                    );

                int height =
                    terrainGenerator.getHeight(
                        x,
                        z
                    );

                int positionIndex =
                    vertex * 3;

                positions[positionIndex] =
                    x;

                // Keep the same surface height
                // as the old cube terrain.
                positions[positionIndex + 1] =
                    height - 0.5f;

                positions[positionIndex + 2] =
                    z;

                // Start with an upward normal.
                normals[positionIndex] = 0f;
                normals[positionIndex + 1] = 1f;
                normals[positionIndex + 2] = 0f;
            }
        }

        // --------------------------------
        // Create triangles
        // --------------------------------

        int index = 0;

        for (int z = MIN_Z; z < MAX_Z; z++) {

            for (int x = MIN_X; x < MAX_X; x++) {

                int topLeft =
                    getVertexIndex(
                        x,
                        z,
                        width
                    );

                int topRight =
                    getVertexIndex(
                        x + 1,
                        z,
                        width
                    );

                int bottomLeft =
                    getVertexIndex(
                        x,
                        z + 1,
                        width
                    );

                int bottomRight =
                    getVertexIndex(
                        x + 1,
                        z + 1,
                        width
                    );

                int h1 =
                    terrainGenerator.getHeight(
                        x,
                        z
                    );

                int h2 =
                    terrainGenerator.getHeight(
                        x + 1,
                        z
                    );

                int h3 =
                    terrainGenerator.getHeight(
                        x,
                        z + 1
                    );

                int h4 =
                    terrainGenerator.getHeight(
                        x + 1,
                        z + 1
                    );

                // Don't create triangles outside the island.
                if (
                    h1 <= 0 ||
                    h2 <= 0 ||
                    h3 <= 0 ||
                    h4 <= 0
                ) {
                    continue;
                }

                // First triangle.
                indices[index++] = topLeft;
                indices[index++] = bottomLeft;
                indices[index++] = topRight;

                // Second triangle.
                indices[index++] = topRight;
                indices[index++] = bottomLeft;
                indices[index++] = bottomRight;
            }
        }

        // --------------------------------
        // Calculate smooth normals
        // --------------------------------

        calculateNormals(
            positions,
            indices,
            normals,
            index
        );

        // --------------------------------
        // Build mesh
        // --------------------------------

        Mesh mesh = new Mesh();

        FloatBuffer positionBuffer =
            BufferUtils.createFloatBuffer(
                positions
            );

        FloatBuffer normalBuffer =
            BufferUtils.createFloatBuffer(
                normals
            );

        IntBuffer indexBuffer =
            BufferUtils.createIntBuffer(
                indices
            );

        mesh.setBuffer(
            VertexBuffer.Type.Position,
            3,
            positionBuffer
        );

        mesh.setBuffer(
            VertexBuffer.Type.Normal,
            3,
            normalBuffer
        );

        mesh.setBuffer(
            VertexBuffer.Type.Index,
            3,
            indexBuffer
        );

        mesh.updateBound();

        Geometry terrain =
            new Geometry(
                "Terrain",
                mesh
            );

        terrain.setMaterial(
            terrainMaterial
        );

        game.getRootNode().attachChild(
            terrain
        );
    }

    private int getVertexIndex(
        int x,
        int z,
        int width
    ) {

        return (z - MIN_Z) * width
            + (x - MIN_X);
    }

    private void calculateNormals(
        float[] positions,
        int[] indices,
        float[] normals,
        int indexCount
    ) {

        // Clear normals.
        for (int i = 0; i < normals.length; i++) {
            normals[i] = 0f;
        }

        for (int i = 0; i < indexCount; i += 3) {

            int a = indices[i] * 3;
            int b = indices[i + 1] * 3;
            int c = indices[i + 2] * 3;

            Vector3f p1 =
                new Vector3f(
                    positions[a],
                    positions[a + 1],
                    positions[a + 2]
                );

            Vector3f p2 =
                new Vector3f(
                    positions[b],
                    positions[b + 1],
                    positions[b + 2]
                );

            Vector3f p3 =
                new Vector3f(
                    positions[c],
                    positions[c + 1],
                    positions[c + 2]
                );

            Vector3f edge1 =
                p2.subtract(p1);

            Vector3f edge2 =
                p3.subtract(p1);

            Vector3f normal =
                edge1.cross(edge2).normalizeLocal();

            normals[a] += normal.x;
            normals[a + 1] += normal.y;
            normals[a + 2] += normal.z;

            normals[b] += normal.x;
            normals[b + 1] += normal.y;
            normals[b + 2] += normal.z;

            normals[c] += normal.x;
            normals[c + 1] += normal.y;
            normals[c + 2] += normal.z;
        }

        // Normalize all vertex normals.
        for (int i = 0; i < normals.length; i += 3) {

            Vector3f normal =
                new Vector3f(
                    normals[i],
                    normals[i + 1],
                    normals[i + 2]
                );

            if (normal.lengthSquared() > 0f) {

                normal.normalizeLocal();

                normals[i] =
                    normal.x;

                normals[i + 1] =
                    normal.y;

                normals[i + 2] =
                    normal.z;
            }
        }
    }
}