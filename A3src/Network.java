import java.util.ArrayList;

/**
 * Class which creates the representation of the Bayesian network.
 */
public class Network {


    /**
     * Method which creates the representation of the fire alarm Bayesian network, as found in BNpart2.xml
     * @return
     */
    public Node fireAlarm() {
        Node root = new Node();
        root.setLabel("fire");
        root.setData(new double[] {0.01, 0.99});
        root.setRoot(true);

        Node smoke = new Node();
        smoke.setLabel("smoke");
        smoke.setData(new double[][] {{0.9, 0.1}, {0.01, 0.99}});
        smoke.setParent(root);

        Node alarm = new Node();
        alarm.setLabel("alarm");
        alarm.setData(new double[][] {{0.5, 0.5}, {0.85, 0.15}});
        alarm.setParent(root);

        Node leaving = new Node();
        leaving.setLabel("leaving");
        leaving.setData(new double[][] {{0.88, 0.12}, {0.0, 1.0}});
        leaving.setParent(alarm);

        Node report = new Node();
        report.setLabel("report");
        report.setData(new double[][] {{0.75, 0.25}, {0.01, 0.99}});
        report.setParent(leaving);

        // Assigning the relationships
        ArrayList<Node> rootChildren = new ArrayList();
        rootChildren.add(alarm);
        rootChildren.add(smoke);
        root.setChildren(rootChildren);

        ArrayList<Node> alarmChildren = new ArrayList();
        alarmChildren.add(leaving);
        alarm.setChildren(alarmChildren);

        ArrayList<Node> leavingChildren = new ArrayList();
        leavingChildren.add(report);
        leaving.setChildren(leavingChildren);


        // Set the relevant variables
        root.relevantVariables();
        smoke.relevantVariables();
        alarm.relevantVariables();
        leaving.relevantVariables();
        report.relevantVariables();

        // Set ancestors
        root.ancestorNodes();
        smoke.ancestorNodes();
        alarm.ancestorNodes();
        leaving.ancestorNodes();
        report.ancestorNodes();
        return root;


    }

    /**
     * Method which returns a part of the bn1 network.
     * @return the root node of the Bayesian Network.
     */
    public Node bn1() {

        // Creating the nodes with their data
        Node root = new Node();
        root.setLabel("Day");
        root.setData(new double[] {0.78, 0.22});
        root.setRoot(true);

        Node takeCar = new Node();
        takeCar.setLabel("Take Car");
        takeCar.setData(new double[][] {{0.6, 0.4}, {0.3, 0.7}});
        takeCar.setParent(root);

        Node trafficFlow = new Node();
        trafficFlow.setLabel("Traffic Flow");
        trafficFlow.setData(new double[][] {{1.0, 0}, {0.4, 0.6}});
        trafficFlow.setParent(takeCar);



        // Assigning the relationships
        ArrayList<Node> rootChildren = new ArrayList();
        rootChildren.add(takeCar);
        root.setChildren(rootChildren);

        ArrayList<Node> takeCarChildren = new ArrayList();
        takeCarChildren.add(trafficFlow);
        takeCar.setChildren(takeCarChildren);

        // Set the relevant variables
        root.relevantVariables();
        takeCar.relevantVariables();
        trafficFlow.relevantVariables();

        // Set ancestors
        root.ancestorNodes();
        takeCar.ancestorNodes();
        trafficFlow.ancestorNodes();
        return root;

    }



}
