package kdtree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreePointSetTest {
    @Test
    public void randomTest() {
        int seed = 42;
        Random rand = new Random(seed);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();
            points.add(new Point(x, y));
        }
        NaivePointSet test1= new NaivePointSet(points);
        KDTreePointSet test2 = new KDTreePointSet(points);
        double a = rand.nextDouble();
        double b = rand.nextDouble();
        assertEquals(test1.nearest(a, b), test2.nearest(a, b));
    }

}
