package kdtree;

import java.util.List;

public class KDTreePointSet implements PointSet {
    private Node root;

    /**
     * Instantiates a new KDTree with the given points.
     * @param points a non-null, non-empty list of points to include
     *               (makes a defensive copy of points, so changes to the list
     *               after construction don't affect the point set)
     * @ Source code for inspiration at https://www.geeksforgeeks.org/k-dimensional-tree/
     * and https://www.sanfoundry.com/java-program-find-nearest-neighbour-using-k-d-tree-search/
     * and nearest pseudocode kd trees slide
     */
    public KDTreePointSet(List<Point> points) {
        for (Point p : points) {
            insert(p);
        }
    }

    private void insert(Point p) {
        root = insert(root, p, 0);
    }

    private Node insert(Node node, Point p, int depth) {
        if (node == null) {
            return new Node(p, depth);
        }
        boolean goLeft = (node.depth % 2 == 0);
        double pPos = getPositionByPoint(p, goLeft);
        double nPos = getPositionByNode(node, goLeft);
        if (nPos < pPos) {
            node.right = insert(node.right, p, depth + 1);
        } else {
            node.left = insert(node.left, p, depth + 1);
        }
        return node;
    }

    private double getPositionByPoint(Point p, boolean goLeft) {
        if (goLeft) {
            return p.x();
        } else {
            return p.y();
        }
    }

    private double getPositionByNode(Node node, boolean goLeft) {
        if (goLeft) {
            return node.point.x();
        } else {
            return node.point.y();
        }
    }

    /**
     * Returns the point in this set closest to (x, y) in (usually) O(log N) time,
     * where N is the number of points in this set.
     */
    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Node closest = findClosest(root, target, root);
        return closest.point;
    }

    private Node findClosest(Node node, Point target, Node closest) {
        if (node == null) {
            return closest;
        }
        double nodeDistance = Point.distanceSquaredBetween(node.point.x(), target.x(), node.point.y(), target.y());
        double targetDistance = Point.distanceSquaredBetween(
                closest.point.x(), target.x(), closest.point.y(), target.y());
        if (nodeDistance < targetDistance) {
            closest = node;
        }
        boolean goLeft = (node.depth % 2 == 0);
        double targetPos = getPositionByPoint(target, goLeft);
        double nPos = getPositionByNode(node, goLeft);
        Node closerSide;
        Node fartherSide;
        if (nPos < targetPos) {
            closerSide = node.right;
            fartherSide = node.left;
        } else {
            closerSide = node.left;
            fartherSide = node.right;
        }
        closest = findClosest(closerSide, target, closest);
        Point p = getPoint(node, target, goLeft);
        double distanceP = Point.distanceSquaredBetween(p.x(), target.x(), p.y(), target.y());
        double distanceC = Point.distanceSquaredBetween(closest.point.x(), target.x(), closest.point.y(), target.y());
        if (distanceP < distanceC) {
            closest = findClosest(fartherSide, target, closest);
        }
        return closest;
    }

    private Point getPoint(Node n, Point target, boolean goLeft) {
        if (goLeft) {
            return new Point(n.point.x(), target.y());
        } else {
            return new Point(target.x(), n.point.y());
        }
    }
    private final class Node {
        Node left;
        Node right;
        int depth;
        Point point;

        private Node(Point point, int depth) {
            this.point = point;
            this.depth = depth;
            left = null;
            right = null;
        }
    }
}
