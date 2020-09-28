import java.util.*;

/**
 * Class which represents a node in the Bayesian network.
 *
 * @param <T>
 */
public class Node<T> {

    // indicates whether the node is the root node
    private boolean root;
    // the label/name of the node
    private String label;
    // this holds the probability table of the node. It will either be an array of size 2 (if root node) or 2x2
    private T data;
    // holds the parent of the node
    private Node<T> parent;
    // holds the nodes that would be relevant to it in a predictive query. It will be its ancestors and itself.
    private List<Node<T>> relevantNodes;
    // holds the node's ancestor nodes
    private List<Node<T>> ancestors;
    // holds the children of the node
    private List<Node<T>> children;


    /**
     * Method which prints all of the node's successors
     */
    public void printSuccessors() {
        if (children != null) {
            for (Node<T> node : children) {
                node.printSuccessors();
            }
        }
        if (parent != null) {
            System.out.printf(label + "<-----" + parent.getLabel() + "\n");
        } else {
            System.out.printf(label + "\n");
        }
    }

    /**
     * Method which populates the relevantNodes attribute.
     */
    public void relevantVariables() {
        relevantNodes = new ArrayList<>();
        Node<T> nodeParent = parent;
        while (nodeParent != null) {
            relevantNodes.add(nodeParent);
            nodeParent = nodeParent.getParent();
        }
    }

    /**
     * Method which populates the ancestors attribute.
     */
    public void ancestorNodes() {
        ancestors = new ArrayList<>();
        Node<T> nodeParent = parent;
        while (nodeParent != null) {
            ancestors.add(nodeParent);
            nodeParent = nodeParent.getParent();
        }
    }

    /**
     * Method which prints a node's relevant variables/nodes.
     */
    public void printRelevantNodes() {
        for (Node<T> node : relevantNodes) {
            System.out.println(node.getLabel());
        }
    }

    /**
     * Method which prints a node's ancestors.
     */
    public void printAncestors() {
        for (Node<T> node : ancestors) {
            System.out.println(node.getLabel());
        }
    }


    /** Getters and Setters **/

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public List<Node<T>> getAncestors() {
        return relevantNodes;
    }

    public void setAncestors(List<Node<T>> ancestors) {
        this.relevantNodes = ancestors;
    }

    public List<Node<T>> getRelevantNodes() {
        return relevantNodes;
    }

    public void setRelevantNodes(List<Node<T>> relevantNodes) {
        this.relevantNodes = relevantNodes;
    }
}
