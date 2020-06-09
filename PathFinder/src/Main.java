/**
 * Grid (in Adjacency List) and DFS (recursively) implementation
 * @author Domas
 * @lastUpdate 2020-06-07
 * TODO: DFS
 *
 *
 * TODO: create usable adjacency list with usable neighbours connections. Instead of adding a number add a link to that node
 * Currently represented in grid, which might be the way to go.
 *
 * TODO: test if bfs works to all directions and sizes. BONus task: optimize path if needed
 */

import java.util.*;

class Node {
    private int x;
    private int y;
    private int number;
    private List<Node> neighbours = new LinkedList<Node>();

    public Node(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public String getCoordinate() {
        return "(" + x + ", " + y + ")";
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public void setNeighbour(Node neighbour) {
        neighbours.add(neighbour);
    }

    public int getNumber() {
        return number;
    }

}

// Queue implementation using doubly linked list (provided by Java)
// Generic type data
// Source: https://www.youtube.com/watch?v=RBSGKlAvoiM&t=3510s
// Does not support null elements

class Queue <T> implements Iterable <T> {

    private LinkedList <T> list = new LinkedList <T> ();

    public Queue() {}

    public Queue(T firstElement) {
        enqueue(firstElement);
    }

    // Return the size of the queue
    public int size() {
        return list.size();
    }

    // Returns whether or not the queue is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    // Peek the element the front of the queue
    // The method throws an error if the queue is empty
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue Empty");
        }
        return list.peekFirst();
    }

    // Poll an element from the front of the queue (removes from the queue)
    // The method throws an error if the queue is empty
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue Empty");
        }
        return list.removeFirst();
    }

    // Add an element to the back of the queue
    public void enqueue(T element) {
        list.addLast(element);
    }

    // Return an iterator to allow the user to traverse
    // through the elements found inside the queue
    @Override public java.util.Iterator <T> iterator () {
        return list.iterator();
    }
}

/**
 * Represents the table, where all the algorithms should be performed.
 * Consists of Nodes, connected with each other.
 * Connections are saved in Grid and Adjacency List
 */
class Table {
    final static int ROW_SIZE = 12;
    final static int COLUMN_SIZE = 12;



    private ArrayList<ArrayList<Node>> grid;
    private List<Node> adjacencyList;

    public Table() {
        grid = setUpGrid();
        adjacencyList = setUpAdjacencyList();
    }

    /**
     * O(n^2)
     * @return
     */
    private ArrayList<ArrayList<Node>>setUpGrid(){

        ArrayList<ArrayList<Node>> newGrid = new ArrayList<ArrayList<Node>>();

        int counter = 0;
        for (int i = 0; i < COLUMN_SIZE; i++) {
            ArrayList<Node> newRow = new ArrayList<Node>();
            for(int j = 0; j < ROW_SIZE; j++) {
                Node newNode = new Node(i, j, counter);
                newRow.add(newNode);
                counter++;
            }
            newGrid.add(newRow);
        }

        for (int i = 0; i < COLUMN_SIZE; i++) {
            for(int j = 0; j < ROW_SIZE; j++) {
                setAllNeighbours(newGrid, null, newGrid.get(i).get(j), i, j);
            }
        }


        return newGrid;
    }

    //TODO: make nicer: ifs and method header

    /**
     * O(1)
     * @param newGrid
     * @param newList
     * @param newNode
     * @param x
     * @param y
     */
    private void setAllNeighbours(ArrayList<ArrayList<Node>> newGrid, LinkedList<Node> newList, Node newNode, int x, int y) {

        int index = newNode.getNumber();
        if (newGrid != null) {

            // Right neighbour
            if ((index % ROW_SIZE) + 1 < ROW_SIZE) {
                newNode.setNeighbour(newGrid.get(x).get(y + 1));
            }

            // Bottom neighbour
            if (index + ROW_SIZE < ROW_SIZE * COLUMN_SIZE) {
                newNode.setNeighbour(newGrid.get(x + 1).get(y));
            }

            // Left neighbour
            if ((index % ROW_SIZE) - 1 >= 0) {
                newNode.setNeighbour(newGrid.get(x).get(y - 1));
            }

            // Top neighbour
            if (index - ROW_SIZE > 0) {
                newNode.setNeighbour(newGrid.get(x - 1).get(y));
            }
            // think for list implementation
        } else if (newList != null) {

            // Right neighbour
            if ((index % ROW_SIZE) + 1 < ROW_SIZE) {
                newNode.setNeighbour(newList.get(index + 1));
            }

            // Bottom neighbour
            if (index + ROW_SIZE < ROW_SIZE * COLUMN_SIZE) {
                newNode.setNeighbour(newList.get(index + ROW_SIZE));
            }

            // Left neighbour
            if ((index % ROW_SIZE) - 1 >= 0) {
                newNode.setNeighbour(newList.get(index - 1));
            }

            // Top neighbour
            if (index - ROW_SIZE > 0) {
                newNode.setNeighbour(newList.get(index - ROW_SIZE));
            }
        }

    }

    /**
     * O(n)
     * @return
     */
    private LinkedList<Node> setUpAdjacencyList() {

        LinkedList<Node> newList = new LinkedList<Node>();
        int counter = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < ROW_SIZE * COLUMN_SIZE; i++) {
            Node newNode = new Node(x, y, counter);
            newList.add(newNode);
            if (x == 12) {
                y++;
                x = 0;
            }
            counter++;
        }

        x = 0;
        y = 0;
        for (int i = 0; i < ROW_SIZE * COLUMN_SIZE; i++) {
            setAllNeighbours(null, newList, newList.get(i), x, y);
            if (x == 12) {
                y++;
                x = 0;
            }
        }

        return newList;
    }

    public List<Node> getAdjacencyList() {
        return adjacencyList;
    }

    // Grid for now
    @Override
    public String toString() {
        String result = "";
        result += "--------------\n";
        for (int i = 0; i < COLUMN_SIZE; i++) {
            result += "|";
            for(int j = 0; j < ROW_SIZE; j++) {
                result += grid.get(i).get(j).getNumber() + " ";
            }
            result += "|\n";
        }
        result += "--------------\n";
        return result;
    }

    public String toStringCoordinatesGrid() {
        String result = "";
        for (int i = 0; i < COLUMN_SIZE; i++) {
            for(int j = 0; j < ROW_SIZE; j++) {
                result += grid.get(i).get(j).getCoordinate() + " ";
            }
            result += "\n";
        }
        return result;
    }


    public String toStringNeighboursGrid() {
        String result = "";
        for (int i = 0; i < COLUMN_SIZE; i++) {
            for(int j = 0; j < ROW_SIZE; j++) {
                result += grid.get(i).get(j).getNumber() + " [";
                for (Node neighbour : grid.get(i).get(j).getNeighbours()) {
                    result += neighbour.getNumber() + " ";
                }
                result = result.substring(0, result.length() - 1);
                result += "], ";

            }
            result += "\n";
        }
        return result;
    }

    public String toStringNeighboursAdjacencyList() {
        String result = "";
        int counter = 0;
        for (int i = 0; i < COLUMN_SIZE * ROW_SIZE; i++) {
            result += adjacencyList.get(i).getNumber() + " [";
            for (Node neighbour : adjacencyList.get(i).getNeighbours()) {
                result += neighbour.getNumber() + " ";
            }
            result = result.substring(0, result.length() - 1);
            result += "], ";
            if (counter == ROW_SIZE - 1) {
                counter = 0;
                result += "\n";
            } else {
                counter++;
            }

        }

        return result;
    }



    // s = start node, e = end node, and 0 <= e, s < n
    public LinkedList<Node> bfs(Node start, Node end) {

        // Do a BFS starting at node s
        LinkedList<Node> prev = solve(start);


        // Return reconstructed path from s -> e
        return reconstructPath(start, end, prev);
    }

    public LinkedList<Node> solve(Node start) {
        Table table = new Table();
        LinkedList<Node> graph = (LinkedList<Node>) table.getAdjacencyList(); // unweighted graph

        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(start);

        int numberOfNodes = ROW_SIZE * COLUMN_SIZE;
        boolean[] visitedNodes = new boolean[numberOfNodes];
        visitedNodes[start.getNumber()] = true;

        LinkedList<Node> prev = new LinkedList<Node>();
        for (int i = 0; i < numberOfNodes; i++) {
            prev.add(null);
        }

        while (!queue.isEmpty()) {
            System.out.println("Queue size:" + queue.size());
            Node node = queue.dequeue();

            List<Node> neighbours = graph.get(node.getNumber()).getNeighbours();

            for (Node next : neighbours) {
                if (!visitedNodes[next.getNumber()]) {
                    queue.enqueue(next);
                    visitedNodes[next.getNumber()] = true;

                    System.out.println("Next:"+ next.getNumber() + "Node: " + node.getNumber());


                    prev.set(next.getNumber(), node);
                }
            }
        }

        return prev;
    }

    //TODO: make work (need reverse)
    public LinkedList<Node> reconstructPath(Node start, Node end, LinkedList<Node> prev) {

        System.out.println("Prev");
        for (Node node : prev) {
            if (node != null) {
                System.out.println(node.getNumber());
            } else {
                System.out.println(node);
            }

        }

        // Reconstruct path going backwards from end
        LinkedList<Node> path = new LinkedList<Node>();
        for (Node at = end; at != null; at = prev.get(at.getNumber())) {
            System.out.println("at: " + at.getNumber());
            path.add(at);
        }

//        System.out.println("path");
//        for (Node node : path) {
//           System.out.println(node.getNumber());
//        }


        /*
        LinkedList<Node> reverse = new LinkedList<Node>();
        for (int i = path.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            reverse.add(path.get(i));
        }
        // Return the reversed arraylist



        // If start and end are connected return the path
        if (reverse.get(0) == start) {
            return reverse;
        } else {
            return new LinkedList<Node>();
        }
        */

        return path;



    }

}

public class Main {

    final static int ROW_SIZE = 12;
    final static int COLUMN_SIZE = 12;

    public static void main(String[] args) {

        //BFS implementation
        int numberOfNodes = ROW_SIZE * COLUMN_SIZE; //n number of nodes
        Table table = new Table();
        LinkedList<Node> graph = (LinkedList<Node>) table.getAdjacencyList(); // unweighted graph
        System.out.println(table.toString());
        System.out.println(table.toStringNeighboursAdjacencyList());
        LinkedList<Node> bfsResult = table.bfs(graph.get(30), graph.get(100));

        System.out.println("Result");
        for(Node node : bfsResult) {
            System.out.println(node.getNumber() + " ");
        }

        /*

        // DFS implementation below:
        int numberOfNodes = ROW_SIZE * COLUMN_SIZE;

        Table table = new Table();
        LinkedList<Table.Node> graph = (LinkedList<Table.Node>) table.getAdjacencyList();

        /*
        System.out.println(table.toString());
        System.out.println(table.toStringNeighboursGrid());
        System.out.println(table.toStringNeighboursAdjacencyList());


        boolean[] visitedNodes = new boolean[numberOfNodes];



        // Start DFS at node zero
        int startNode = 0;
        dfs(startNode, visitedNodes, graph);

    }

    void dfs(int at, boolean[] visitedNodes, LinkedList<Table.Node> graph) {

        // If already visited, return
        if (visitedNodes[at]) {
            return;
        }

        // If not, continue
        visitedNodes[at] = true;

        LinkedList<Table.Node> neighbours = (LinkedList<Table.Node>) graph.get(at).getNeighbours();
        for(Table.Node next : neighbours) {
            dfs(next.getNumber(), visitedNodes, graph);
        }

        */
    }











}
