package huskymaps.server.logic;

import huskymaps.params.RasterRequest;
import huskymaps.params.RasterResult;
import huskymaps.utils.Constants;
import kdtree.Point;

import java.util.Objects;

import static huskymaps.utils.Constants.LAT_PER_TILE;
import static huskymaps.utils.Constants.LON_PER_TILE;
import static huskymaps.utils.Constants.MIN_X_TILE_AT_DEPTH;
import static huskymaps.utils.Constants.MIN_Y_TILE_AT_DEPTH;
import static huskymaps.utils.Constants.MIN_ZOOM_LEVEL;
import static huskymaps.utils.Constants.ROOT_LRLAT;
import static huskymaps.utils.Constants.ROOT_LRLON;
import static huskymaps.utils.Constants.ROOT_ULLAT;
import static huskymaps.utils.Constants.ROOT_ULLON;

/** Application logic for the RasterAPIHandler. */
public class Rasterer {

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param request RasterRequest
     * @return RasterResult
     */
    public static RasterResult rasterizeMap(RasterRequest request) {
        double getUllat = request.ullat;
        double getUllon = request.ullon;
        double getLrlat = request.lrlat;
        double getLrlon = request.lrlon;
        Point[] array = checkEdge(getUllat, getUllon, getLrlat, getLrlon);
        int uX = (int) ((array[0].x() - Constants.ROOT_ULLON) / LON_PER_TILE[request.depth]);
        int uY = (int) (-1 * (array[0].y() - ROOT_ULLAT) / LAT_PER_TILE[request.depth]);
        int lX = (int) ((array[1].x() - ROOT_ULLON) / LON_PER_TILE[request.depth]);
        int lY = (int) (-1 * (array[1].y() - ROOT_ULLAT) / LAT_PER_TILE[request.depth]);

        Tile[][] grid = new Tile[lY - uY + 1][lX - uX + 1];
        for (int j = 0; j < lY - uY + 1; j++) {
            for (int i = 0; i < lX - uX + 1; i++) {
                grid[j][i] = new Tile(request.depth, i + uX, j + uY);
            }
        }

        return new RasterResult(grid);
    }
    private static Point[] checkEdge(double ullat, double ullon, double lrlat, double lrlon) {
        if (ullon < ROOT_ULLON) {
            ullon = ROOT_ULLON;
        }
        if (ullat > ROOT_ULLAT) {
            ullat = ROOT_ULLAT;
        }

        if (lrlon > ROOT_LRLON) {
            lrlon = ROOT_LRLON;
        }
        if (lrlat < ROOT_LRLAT) {
            lrlat = ROOT_LRLAT;
        }

        if (ullon > ROOT_LRLON){
            ullon = ROOT_LRLON;
        }

        if (ullat < ROOT_LRLAT){
            ullat = ROOT_LRLAT;
        }


        Point[] result = new Point[2];
        result[0] = new Point(ullon, ullat);
        result[1] = new Point(lrlon, lrlat);
        return result;
    }

    public static class Tile {
        public final int depth;
        public final int x;
        public final int y;

        public Tile(int depth, int x, int y) {
            this.depth = depth;
            this.x = x;
            this.y = y;
        }

        public Tile offset() {
            return new Tile(depth, x + 1, y + 1);
        }

        /**
         * Return the latitude of the upper-left corner of the given slippy map tile.
         * @return latitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lat() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyY = MIN_Y_TILE_AT_DEPTH[depth] + y;
            double latRad = Math.atan(Math.sinh(Math.PI * (1 - 2 * slippyY / n)));
            return Math.toDegrees(latRad);
        }

        /**
         * Return the longitude of the upper-left corner of the given slippy map tile.
         * @return longitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lon() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyX = MIN_X_TILE_AT_DEPTH[depth] + x;
            return slippyX / n * 360.0 - 180.0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tile tile = (Tile) o;
            return depth == tile.depth &&
                    x == tile.x &&
                    y == tile.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, x, y);
        }

        @Override
        public String toString() {
            return "d" + depth + "_x" + x + "_y" + y + ".jpg";
        }
    }
}
