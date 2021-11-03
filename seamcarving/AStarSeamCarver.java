
package seamcarving;

import astar.AStarGraph;
import astar.AStarSolver;
import astar.WeightedEdge;
import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException();
        }
        return Math.sqrt(getXGradientSquare(x, y) + getYGradientSquare(x, y));
    }

    private int getXGradientSquare(int x, int y) {
        Color right = null;
        Color left = null;

        if (x == 0){
            left = picture.get(width() - 1, y);
        }

        if (x == width() - 1){
            right = picture.get(0, y);
        }

        if (left == null){
            left = picture.get(x - 1, y);
        }

        if (right == null){
            right = picture.get(x + 1, y);
        }

        int rDiff = Math.abs(right.getRed() - left.getRed());
        int gDiff = Math.abs(right.getGreen() - left.getGreen());
        int bDiff = Math.abs(right.getBlue() - left.getBlue());
        return rDiff * rDiff + gDiff * gDiff + bDiff * bDiff;
    }



    private int getYGradientSquare(int x, int y) {
        Color above = null;
        Color below = null;
        if (y == 0){
            above = picture.get(x, height() - 1);
        }

        if (y == height() - 1){
            below = picture.get(x, 0);
        }

        if (above == null){
            above = picture.get(x, y - 1);
        }

        if (below == null){
            below = picture.get(x, y + 1);
        }

        int rDiff = Math.abs(below.getRed() - above.getRed());
        int gDiff = Math.abs(below.getGreen() - above.getGreen());
        int bDiff = Math.abs(below.getBlue() - above.getBlue());
        return rDiff * rDiff + gDiff * gDiff + bDiff * bDiff;
    }

    public int[] findHorizontalSeam() {
        SeamCarverGraph g = new SeamCarverGraph(1);
        AStarSolver p = new AStarSolver(g, g.start, g.end, 100);
        int[] horizontalSeam = new int[p.solution().size() - 2];

        postProcessSeams(horizontalSeam, p.solution(), false);
        return horizontalSeam;

    }

    public int[] findVerticalSeam() {
        SeamCarverGraph g = new SeamCarverGraph(0);
        AStarSolver p = new AStarSolver(g, g.start, g.end, 100);
        int[] verticalSeam = null;
        if (p.solution().size() > 0){
            verticalSeam = new int[p.solution().size() - 2];
        } else {
            return null;
        }

        postProcessSeams(verticalSeam, p.solution(), true);
        return verticalSeam;
    }

    private void postProcessSeams(int[] seamResult, List<IntegerPoint> graphResult, boolean isVertical){
        for (int i = 1; i < graphResult.size() - 1; i++){
            if (isVertical) {
                seamResult[i - 1] = graphResult.get(i).getX();
            } else {
                seamResult[i - 1] = graphResult.get(i).getY();
            }
        }
    }

    private class SeamCarverGraph implements AStarGraph<IntegerPoint> {
        private final int vertical = 0;
        private final int horizontal = 1;
        private int isVertical;
        private IntegerPoint start = new IntegerPoint(-10, -20);
        private IntegerPoint end = new IntegerPoint(-30, -40);

        public SeamCarverGraph(int direction) {
            if (direction == vertical) {
                isVertical = vertical;
            } else {
                isVertical = horizontal;
            }
        }

        private void startAndEndNeighbors(List<WeightedEdge<IntegerPoint>> result, IntegerPoint from, int mode){
            int y = -1;
            int x = -1;
            if (vertical == isVertical){
                y = mode == 0 ? 0 : width() - 1;
                for (int i = 0; i < width(); i++){
                    result.add(new WeightedEdge<IntegerPoint>(from, new IntegerPoint(i, y),
                            energy(i, y)));
                }
            }
            else {
                x = mode == 0 ? 0 : height() - 1;
                for (int i = 0; i < height(); i++){
                    result.add(new WeightedEdge<IntegerPoint>(from, new IntegerPoint(x, i),
                            energy(x, i)));
                }
            }


        }
        @Override
        public List<WeightedEdge<IntegerPoint>> neighbors(IntegerPoint v) {
            List<WeightedEdge<IntegerPoint>> result = new ArrayList<>();
            if (v.equals(start)) {
                startAndEndNeighbors(result, start, 0);
                return result;
            }
            else if (v.equals(end)){
                startAndEndNeighbors(result, end, 1);
                return result;
            }

            if (!v.equals(start) && !v.equals(end)){
                for (int i = 0; i < 3; i++) {
                    if (isVertical == vertical) {
                        IntegerPoint a = new IntegerPoint(v.getX() + 1 - i, v.getY() + 1);
                        if (v.getY() == height() - 1){
                            result.add(new WeightedEdge<>(v, end, 0));
                            return result;
                        }

                        if (a.getX() < width() && a.getX() >= 0) {
                            result.add(new WeightedEdge<>(v, a, energy(a.getX(), a.getY())));
                        }


                    } else {
                        IntegerPoint b = new IntegerPoint(v.getX() + 1, v.getY() + 1 - i);

                        if (v.getX() == width() - 1){
                            result.add(new WeightedEdge<>(v, end, 0));
                            return result;
                        }

                        if (b.getY() < height() && b.getY() >= 0) {
                            result.add(new WeightedEdge<>(v, b, energy(b.getX(), b.getY())));
                        }
                    }
                }
            }

            return result;
        }

        @Override
        public double estimatedDistanceToGoal(IntegerPoint s, IntegerPoint goal) {
            return 0;
        }



    }
}
