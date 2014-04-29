package gapchenko.llttz.stores;

/**
 * @author artem
 * Created on 4/23/14.
 */
public class TwoDimTreeNode {
    private TwoDimTreeNode left;
    private TwoDimTreeNode right;
    private Location data;

    public TwoDimTreeNode(Location data) {
        this.data = data;
    }

    public TwoDimTreeNode getLeft() {
        return left;
    }

    public void setLeft(TwoDimTreeNode left) {
        this.left = left;
    }

    public TwoDimTreeNode getRight() {
        return right;
    }

    public void setRight(TwoDimTreeNode right) {
        this.right = right;
    }

    public Location getData() {
        return data;
    }
}