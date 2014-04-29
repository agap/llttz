package gapchenko.llttz.stores;

import java.util.*;

/**
 * Simple implementation of k-d tree which allows to perform search of nearest neighbour in O(ln) time in the best case.
 * @see <a href="http://en.wikipedia.org/wiki/K-d_tree">k-d tree</a>
 * @author artemgapchenko
 * Created on 22.04.14.
 */
public class TimeZoneTreeStore extends TimeZoneStore {
    private TwoDimTreeNode root;

    @Override
    public void insert(Location loc) {
        TwoDimTreeNode node = new TwoDimTreeNode(loc);

        if (root == null) {
            root = node;
            return;
        }

        int layer = 0;
        TwoDimTreeNode current = root;

        while (true) {
            double[] currentCoordinates = current.getData().getCoordinates();

            if (loc.getCoordinates()[layer] < currentCoordinates[layer]) {
                if (current.getLeft() == null) {
                    current.setLeft(node);
                    return;
                } else {
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() == null) {
                    current.setRight(node);
                    return;
                } else {
                    current = current.getRight();
                }
            }

            layer = (layer + 1) % 2;
        }
    }

    @Override
    public TimeZone nearestTimeZone(final Location searchLocation) {
        LinkedList<TwoDimTreeNode> nodeStack = new LinkedList<>();
        Set<TwoDimTreeNode> attendedNodes = new HashSet<>();

        Location bestGuess = traverseTheTree(nodeStack, root, searchLocation, attendedNodes);
        double bestDistance = centralAngle(searchLocation, bestGuess);

        while (!nodeStack.isEmpty()) {
            int layer = (nodeStack.size() - 1) % 2;
            TwoDimTreeNode lastNode = nodeStack.pollLast();
            Location newBest = null;

            if (hypersphereAndHyperplaneIntersection(
                    searchLocation.getCoordinates()[layer],
                    lastNode.getData().getCoordinates()[layer],
                    bestDistance)
                ) {
                if (lastNode.getLeft() != null && !attendedNodes.contains(lastNode.getLeft())) {
                    nodeStack.add(lastNode);
                    newBest = traverseTheTree(nodeStack, lastNode.getLeft(), searchLocation, attendedNodes);
                } else if (lastNode.getRight() != null && !attendedNodes.contains(lastNode.getRight())) {
                    nodeStack.add(lastNode);
                    newBest = traverseTheTree(nodeStack, lastNode.getRight(), searchLocation, attendedNodes);
                }
            }

            if (newBest != null) {
                double newBestDistance = centralAngle(searchLocation, newBest);

                if (newBestDistance < bestDistance) {
                    bestDistance = newBestDistance;
                    bestGuess = newBest;
                }
            }
        }

        return TimeZone.getTimeZone(bestGuess.getZone());
    }

    private boolean hypersphereAndHyperplaneIntersection(final double coord1, final double coord2, final double bestDistance) {
        final double diff = Math.abs(coord1 - coord2);
        return (diff < 180.0) ? (diff < bestDistance) : ((360.0 - diff) < bestDistance);
    }

    private Location traverseTheTree(final List<TwoDimTreeNode> stack, final TwoDimTreeNode traverseFrom,
                                     final Location searchLocation, final Set<TwoDimTreeNode> attendedNodes) {
        TwoDimTreeNode current = traverseFrom;
        stack.add(current);
        attendedNodes.add(current);

        Location bestGuess  = traverseFrom.getData();
        double bestDistance = centralAngle(searchLocation, bestGuess);
        int compareBy = (stack.size() - 1) % 2;

        double[] currentCoordinates;
        double newDistance;

        while (true) {
            currentCoordinates = current.getData().getCoordinates();

            if (searchLocation.getCoordinates()[compareBy] < currentCoordinates[compareBy]) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }

            if (current != null) {
                stack.add(current);
                attendedNodes.add(current);
                compareBy = (compareBy + 1) % 2;

                newDistance = centralAngle(searchLocation, current.getData());
                if (newDistance < bestDistance) {
                    bestDistance = newDistance;
                    bestGuess = current.getData();
                }
            } else {
                return bestGuess;
            }
        }
    }
}
