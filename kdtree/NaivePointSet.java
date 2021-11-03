package kdtree;

import java.util.List;

/**
 * Naive nearest neighbor implementation using a linear scan.
 */
public class NaivePointSet implements PointSet {
    private List<Point> points;

    /**
     * Instantiates a new NaivePointSet with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     */
    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    /**
     * Returns the point in this set closest to (x, y) in O(N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        double min = Double.POSITIVE_INFINITY;
        Point closest = null;
        for (Point p2 : points) {
            double distance = Point.distanceSquaredBetween(x, p2.x(), y, p2.y());
            if (distance < min) {
                min = distance;
                closest = p2;
            }
        }
        return closest;
    }
}
