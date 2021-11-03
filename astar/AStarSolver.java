package astar;

import edu.princeton.cs.algs4.Stopwatch;
import heap.ArrayHeapMinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome result;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, WeightedEdge<Vertex>> edgeTo;
    private ArrayHeapMinPQ<Vertex> fringe;
    private double solutionWeight;
    private double timeUsed;
    private List<Vertex> solution;
    private HashMap<Vertex, Boolean> visited;
    private int numOfStates;


    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        fringe = new ArrayHeapMinPQ<>();
        solution = new ArrayList<>();
        visited = new HashMap<>();
        Stopwatch sw = new Stopwatch();
        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        double min = input.estimatedDistanceToGoal(start, end);
        fringe.add(start, min);

        if (start.equals(end)) {
            solutionWeight = 0.0;
            solution = List.of(start);
            result = SolverOutcome.SOLVED;
            numOfStates = 0;
            timeUsed = 0.0;
            edgeTo.put(start, null);
            distTo.put(start, 0.0);
            return;
        }
        while (!fringe.isEmpty()) {
            Vertex v = fringe.removeSmallest();
            if (v.equals(end)) {
                while (!v.equals(start)) {
                    solution.add(v);
                    v = edgeTo.get(v).from();
                }
                solution.add(start);
                Collections.reverse(solution);
                timeUsed = sw.elapsedTime();
                result = SolverOutcome.SOLVED;
                solutionWeight = distTo.get(end);
                return;
            }
            visited.put(v, true);
            numOfStates++;
            for (WeightedEdge<Vertex> e: input.neighbors(v)) {
                if (!visited.containsKey(e.to())) {
                    distTo.put(e.to(), Double.POSITIVE_INFINITY);
                    visited.put(e.to(), false);
                }
            }
            for (WeightedEdge<Vertex> e: input.neighbors(v)) {
                if (!visited.get(e.to())) {
                    double h = input.estimatedDistanceToGoal(e.to(), end);
                    double distanceFrom = distTo.get(e.from()) + e.weight();
                    if (distanceFrom < distTo.get(e.to())) {
                        distTo.put(e.to(), distanceFrom);
                        if (fringe.contains(e.to())) {
                            fringe.changePriority(e.to(), h + distanceFrom);
                        } else {
                            fringe.add(e.to(), h + distanceFrom);
                        }
                        edgeTo.put(e.to(), e);
                    }
                }
            }
            if (timeout <= sw.elapsedTime()) {
                result = SolverOutcome.TIMEOUT;
                solutionWeight = 0.0;
                return;
            }
        }
        result = SolverOutcome.UNSOLVABLE;
        solution = List.of();
        solutionWeight = 0.0;
    }

    @Override
    public SolverOutcome outcome() {
        return result;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return numOfStates;
    }

    @Override
    public double explorationTime() {
        return timeUsed;
    }
}
