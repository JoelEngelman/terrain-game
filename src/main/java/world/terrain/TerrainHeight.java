package world.terrain;

public class TerrainHeight {

    private final TerrainIsland island =
        new TerrainIsland();

    public int getHeight(int x, int z) {

        if (!island.isLand(x, z)) {
            return 0;
        }

        float distance = (float) Math.sqrt(
            x * x + z * z
        );

        // Base land
        int height = 2;

        // Large central mountain
        float centerDistance = distance;

        if (centerDistance < 70) {

            float mountain =
                1f - (centerDistance / 70f);

            height += Math.round(
                mountain * 14f
            );
        }

        // North mountain range
        float northDistance = (float) Math.sqrt(
            x * x +
            (z - 65) * (z - 65)
        );

        if (northDistance < 45) {

            float mountain =
                1f - (northDistance / 45f);

            height += Math.round(
                mountain * 9f
            );
        }

        // South hills
        float southDistance = (float) Math.sqrt(
            x * x +
            (z + 65) * (z + 65)
        );

        if (southDistance < 45) {

            float hill =
                1f - (southDistance / 45f);

            height += Math.round(
                hill * 7f
            );
        }

        // East hills
        float eastDistance = (float) Math.sqrt(
            (x - 70) * (x - 70) +
            z * z
        );

        if (eastDistance < 45) {

            float hill =
                1f - (eastDistance / 45f);

            height += Math.round(
                hill * 8f
            );
        }

        // West hills
        float westDistance = (float) Math.sqrt(
            (x + 70) * (x + 70) +
            z * z
        );

        if (westDistance < 45) {

            float hill =
                1f - (westDistance / 45f);

            height += Math.round(
                hill * 8f
            );
        }

        return height;
    }
}