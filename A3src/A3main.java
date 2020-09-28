import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;


/**
 * Class which contains the main method. It prompts the user to enter a predictive query and it uses the variable
 * elimination algorithm to give an answer.
 */
public class A3main {


    // used to create a representation of the Bayesian network
    public static Network networkGenerator;
    // holds all the nodes of a Bayesian network
    public static ArrayList<Node> nodeList;
    // node that is queried
    public static Node queryNode;
    // node for which an observation is made
    public static Node evidenceNode;
    // attribute holding whether the value of the observation was true or false
    public static boolean observation;


    /**
     * Method which prints out the nodes of the Bayesian network to be queried.
     */
    public static void printList() {
        for (Node node : nodeList) {
            System.out.println(node.getLabel());
        }
    }

    /**
     * Method which populates the nodeList attribute
     *
     * @param node root node of the network.
     */
    public static void populateList(Node node) {
        if (node.getChildren() != null) {
            for (int i = 0; i < node.getChildren().size(); i++) {
                populateList((Node) (node.getChildren().get(i)));
            }
        }
        nodeList.add(node);
    }

    /**
     * Method which prints out the nodes in a list passed as a parameter
     *
     * @param list
     */
    public static void printNodeList(ArrayList<Node> list) {
        System.out.println("Nodes relevant to query:");
        for (Node node : list) {
            System.out.println(node.getLabel());
        }
        System.out.println();
    }

    /**
     * Method which iterates the nodes of the Bayesian network and prints out its ancestors
     */
    public static void printAncestors() {
        for (Node node : nodeList) {
            System.out.println("Ancestors of node: " + node.getLabel());
            node.printAncestors();
        }
    }

    /**
     * Method which prompts the user and returns the name of the random variable the user wishes to query.
     *
     * @return String which holds the name of the random variable the user wishes to query.
     */
    public static String promptUserQuery() {
        System.out.println("Variables are: ");
        printList();
        System.out.println("Which one would you like to query?");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = br.readLine();
            return s;
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Method which prompts the user and returns the name of the random variable the user wishes to use as an
     * observation.
     *
     * @return String which holds the name of the random variable the user uses as an observation.
     */
    public static String promptUserEvidence() {
        System.out.println("Variables that can be used for evidence: ");
        queryNode.printAncestors();
        System.out.println("Which one would you like to use for evidence?");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = br.readLine();
            return s;
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Method which prompts the user to enter the value of the observed variable.
     *
     * @return String which holds the value of the observed variable.
     */
    public static String promptUserObservation() {
        System.out.println("Enter the value of the observed random variable: " + evidenceNode.getLabel());
        System.out.println("T or F");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = br.readLine();
            return s;
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }


    /**
     * Method which prompts the user to enter the value of the observed variable.
     *
     * @return String which holds the value of the observed variable.
     */
    public static String promptUserContinue() {
        System.out.println("Continue? y/n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = br.readLine();
            return s;
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    /**
     * Method which checks that the user input matches the name of random variable that can be queried.
     *
     * @param userIn the user input - the name of the random variable the user wishes to query
     * @param list   a list of the nodes that may be queried
     * @return true if the user input matches a random variable in the Bayesian network
     */
    public static boolean validateUserInputQuery(String userIn, ArrayList<Node> list) {
        for (Node node : list) {
            if (userIn.equals(node.getLabel())) {
                queryNode = node;
                return true;
            }
        }
        return false;
    }

    /**
     * Method which checks that the user input matches the name of random variable that can be used as observation.
     *
     * @param userIn the user input - the name of the random variable the user wishes to observe
     * @param list   a list of the nodes that may be observed
     * @return true if the user input matches a random variable in the Bayesian network
     */
    public static boolean validateUserInputEvidence(String userIn, ArrayList<Node> list) {
        for (Node node : list) {
            if (userIn.equals(node.getLabel())) {
                evidenceNode = node;
                return true;
            }
        }
        return false;
    }


    /**
     * Method which checks that the value of the observed variable is either true or false.
     *
     * @param userIn the user input - the value of the observed variable
     * @return true if it's either "T" or "F".
     */
    public static boolean validateUserInputObservation(String userIn) {
        if (userIn.equals("T")) {
            observation = true;
            return true;
        } else if (userIn.equals("F")) {
            observation = false;
            return true;
        }
        return false;
    }

    /**
     * Method which prints the probability table of a node
     *
     * @param array representing the probability table of a node
     */
    public static void printValues(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.println(array[i][j]);
            }

        }
    }

    /**
     * Method which carries out the joining and marginalising process of the variable elimination algorithm.
     *
     * @param relevantNodes holds the list of nodes on which the process must take place.
     * @return an array of size 2 holding the probability table of the results.
     */
    public static double[] joinAndMarginalise(ArrayList<Node> relevantNodes) {
        if (relevantNodes.size() == 1) {
            return (double[]) relevantNodes.get(0).getData();
        } else {
            double[] values = (double[]) relevantNodes.get(0).getData();
            for (int i = 0; i < relevantNodes.size() - 1; i++) {
                double[][] childValues = (double[][]) relevantNodes.get(i + 1).getData();
                childValues[0][0] = childValues[0][0] * values[0];
                childValues[0][1] = childValues[0][1] * values[0];
                childValues[1][0] = childValues[1][0] * values[1];
                childValues[1][1] = childValues[1][1] * values[1];
                double[] newChild = new double[2];
                newChild[0] = childValues[0][0] + childValues[1][0];
                newChild[1] = childValues[0][1] + childValues[1][1];
                values = newChild;
            }
            return values;
        }
    }

    /**
     * Method which applies the joining and marginalising process on the observed variable and its children.
     *
     * @param list - list holding the nodes that are relevant to the query.
     */
    public static void applyEvidence(ArrayList<Node> list, boolean observation) {
        ArrayList<Node> evidenceChildren = (ArrayList) evidenceNode.getChildren();
        for (Node child : evidenceChildren) {
            double[][] childValues = (double[][]) child.getData();
            double[] newChild = new double[2];
            if (observation) {
                newChild[0] = childValues[0][0];
                newChild[1] = childValues[0][1];
            } else {
                newChild[0] = childValues[1][0];
                newChild[1] = childValues[1][1];
            }
            child.setData(newChild);
        }
        ArrayList<Node> ancestorNodes = (ArrayList) evidenceNode.getAncestors();
        for (Node ancestorNode : ancestorNodes) {
            if (list.contains(ancestorNode)) {
                list.remove(ancestorNode);
            }
        }
        list.remove(evidenceNode);
    }


    /**
     * Main method of the class. Instantiates the necessary classes and provides the flow of the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        networkGenerator = new Network();
        System.out.println("Welcome to the Bayesian network inference agent.");
        System.out.println("We will be considering the network found in the file BN2part2.xml");
        System.out.println();
        while (true) {
            nodeList = new ArrayList<>();
            Node rootNode = networkGenerator.fireAlarm();
            populateList(rootNode);
            // prompt user to enter random variable to be queried
            String userIn = promptUserQuery();
            if (userIn != null) {
                // validate user input
                while (!validateUserInputQuery(userIn, nodeList)) {
                    System.out.println("Invalid variable name.");
                    // prompt user again
                    userIn = promptUserQuery();
                }
            }
            // if the node to be queried is the root, just print its probability table
            if (queryNode.isRoot()) {
                double[] results = (double[]) queryNode.getData();
                System.out.println("T: " + Double.toString(results[0]) + " F: " + Double.toString(results[1]));
                userIn = promptUserContinue();
                if (!userIn.equals("y")) {
                    break;
                }
                continue;
            }
            // if the node to be queried is not the root node
            else {
                // construct the list containing the nodes relevant to the query
                ArrayList<Node> relevantNodes = (ArrayList) queryNode.getAncestors();
                relevantNodes.add(0, queryNode);
                Collections.reverse(relevantNodes);
                printNodeList(relevantNodes);
                // prompt the user to enter the name of the random variable for which an observation has been made
                userIn = promptUserEvidence();
                if (userIn != null) {
                    // validate user input
                    while (!validateUserInputEvidence(userIn, (ArrayList) queryNode.getAncestors())) {
                        System.out.println("Invalid variable name.");
                        // if user input is invalid, prompt user again
                        userIn = promptUserEvidence();
                    }
                }
                // prompt user to enter the value of the observed variable (T/F)
                userIn = promptUserObservation();
                if (userIn != null) {
                    while (!validateUserInputObservation(userIn)) {
                        System.out.println("Invalid value for observation.");
                        // if user input is invalid (not T or F) prompt user again
                        userIn = promptUserObservation();
                    }
                }
                // join and marginalise probability tables from node down to observed variable
                applyEvidence(relevantNodes, observation);
                double[] results = joinAndMarginalise(relevantNodes);
                System.out.println("T: " + Double.toString(results[0]) + " F: " + Double.toString(results[1]));
                // prompt user to enter whether they want to continue with another query
                userIn = promptUserContinue();
                if (!userIn.equals("y")) {
                    break;
                }
            }
        }
    }
}
