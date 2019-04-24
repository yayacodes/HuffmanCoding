
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.*;

public class HuffmanCoding{

    static BufferedWriter writer = null;

    public static void main(String args[]) throws IOException {
        try {
            writer = new BufferedWriter(new FileWriter("outfile.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Character, Integer> myMap = new HashMap();


        Scanner in = new Scanner(new FileReader("infile.dat"));
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        String outString = sb.toString().replaceAll("[^a-zA-Z0-9]", "");
        for (char c : outString.toCharArray()) {
            if (null == myMap.get(c)) {
                myMap.put(c, 1);
            } else {
                myMap.put(c, (myMap.get(c) + 1));
            }
        }


        PriorityQueue<Node> queue = initQueue(myMap, -1);

        // Frequency
        int totalChars = outString.length();

        while (queue.size() > 0) {

            Node current = queue.poll();
            writer.append(current.c + " " + ((double) current.frequency / (double) totalChars) * 100.0 + "%" + "\n");

        }

        writer.append("\n");

        // Tree
        queue = initQueue(myMap, 1);
        formTree(queue);

        writer.close();

    }

    static PriorityQueue<Node> initQueue(Map<Character, Integer> map, int order){
        PriorityQueue<Node> queue = new PriorityQueue<Node>(map.size(), new HuffmanComparator(order));


        for (char c : map.keySet()) {

            Node n = new Node();

            n.c = c;
            n.frequency = map.get(c);

            n.right = null;
            n.left = null;

            queue.add(n);
        }

        return queue;
    }


    static void printTree(Node root, String s) {

        if (null == root.left && null == root.right) {
            try {
                writer.append(root.c + " " + s + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (null != root && null != root.left)
            printTree(root.left, s + "0");
        if (null != root && null != root.right)
            printTree(root.right, s + "1");
    }


    static void formTree(PriorityQueue<Node> Queue) {
        Node root = null;

        while (Queue.size() > 1) {
            Node min1 = Queue.poll();
//            Queue.poll();

            Node min2 = Queue.poll();
//            Queue.poll();

            Node newNode = new Node();

            newNode.frequency = min1.frequency + min2.frequency;

            newNode.c = '!';

            newNode.left = min1;
            newNode.right = min2;

            root = newNode;

            Queue.add(newNode);
        }
        printTree(root, "");

    }


    /**
     * Node Class
     **/
    static class Node {
        int frequency;
        char c;

        Node left;
        Node right;
    }

    /**
     * HuffmanComparator
     **/
    static class HuffmanComparator implements Comparator<Node> {
        int i = 1;
        public HuffmanComparator(int i){
            this.i = i;
        }
        public int compare(Node x, Node y) {
            return (x.frequency - y.frequency) * i;
        }
    }

}
