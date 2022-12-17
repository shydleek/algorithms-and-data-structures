package by.bsu.algorithms.lab2.tree;

import by.bsu.algorithms.util.Counter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class BinarySearchTree {
    private int size;
    private int nodeCount;
    private Node root;

    public void insert(int data) {
        size++;
        root = doInsert(root, data);
    }

    public int getSize() {
        return size;
    }

    public int getTreeHeight() {
        return root.height;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public int getKMinimalNodeValue(int k) {
        if (k > nodeCount || k <= 0) {
            throw new IllegalArgumentException("Illegal value of k " + k + ". Tree nodeCount is " + nodeCount);
        }
        return getKMinimalNode(root, k, new Counter()).data;
    }

    public int getKMinimalKey(int k) {
        if (k > size || k <= 0) {
            throw new IllegalArgumentException("Illegal value of k " + k + ". Tree size is " + size);
        }
        return getKMinimalKey(root, k, new Counter()).data;
    }

    public void printTree(PrintOrder order) { // вывод дерева на консоль для заданного порядка вывода
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        if (order == PrintOrder.ASCENDING) {
            ascendingOrder(root, joiner);
        }
        else {
            descendingOrder(root, joiner);
        }
        System.out.println(joiner);
    }

    public void printLevelNodeRepresentation() {
        HashMap<Integer, ArrayList<Node>> levelRepresentation = new HashMap<>();
        createRepresentation(levelRepresentation, root, 0);
        levelRepresentation.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .forEach(e -> {
                    int height = e.getKey();
                    int[] nodesWithSameHeight = e.getValue()
                            .stream()
                            .mapToInt(node -> node.data)
                            .toArray();
                    System.out.println(height + ": " + Arrays.toString(nodesWithSameHeight));
                });
    }

    private Node doInsert(Node current, int data) {
        if (current == null) {
            nodeCount++;
            return new Node(data);
        }
        else {
            if (data > current.data) {
                current.right = doInsert(current.right, data);
            }
            else if (data < current.data) {
                current.left = doInsert(current.left, data);
            }
            else {
                current.count++;
            }
        }
        return balance(current);
    }

    private Node leftRotate(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        fixHeight(node);
        fixHeight(newRoot);
        return newRoot;
    }

    private Node rightRotate(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        fixHeight(node);
        fixHeight(newRoot);
        return newRoot;
    }

    private Node balance(Node node) {
        fixHeight(node);
        if (balanceFactor(node) == 2) {
            if (balanceFactor(node.right) < 0) {
                node.right = rightRotate(node.right);
            }
            return leftRotate(node);
        }
        if (balanceFactor(node) == -2) {
            if (balanceFactor(node.left) > 0) {
                node.left = leftRotate(node.left);
            }
            return rightRotate(node);
        }
        return node;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private void fixHeight(Node node) {
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        node.height = Math.max(leftHeight, rightHeight) + 1;
    }

    private int balanceFactor(Node node) {
        return height(node.right) - height(node.left);
    }

    private Node getKMinimalKey(Node currentNode, int needK, Counter currentK) { // возвращает катое минимальное значение в дереве
        if (currentNode == null) {
            return null;
        }
        Node answer = getKMinimalKey(currentNode.left, needK, currentK);
        if (answer != null) {
            return answer;
        }
        if (currentK.addAndGet(currentNode.count) >= needK) {
            return currentNode;
        }
        return getKMinimalKey(currentNode.right, needK, currentK);
    }

    private Node getKMinimalNode(Node currentNode, int needK, Counter currentK) {
        if (currentNode == null) {
            return null;
        }
        Node answer = getKMinimalNode(currentNode.left, needK, currentK);
        if (answer != null) {
            return answer;
        }
        if (currentK.addAndGet(1) >= needK) {
            return currentNode;
        }
        return getKMinimalNode(currentNode.right, needK, currentK);
    }

    private void ascendingOrder(Node node, StringJoiner joiner) {
        if (node == null) {
            return;
        }
        ascendingOrder(node.left, joiner);
        for (int i = 0; i < node.count; i++) {
            joiner.add(Integer.toString(node.data));
        }
        ascendingOrder(node.right, joiner);
    }

    private void descendingOrder(Node node, StringJoiner joiner) {
        if (node == null) {
            return;
        }
        descendingOrder(node.right, joiner);
        for (int i = 0; i < node.count; i++) {
            joiner.add(Integer.toString(node.data));
        }
        descendingOrder(node.left, joiner);
    }

    private void createRepresentation(HashMap<Integer, ArrayList<Node>> leverRepresentation, Node node, int deep) {
        if (node != null) {
            ArrayList<Node> nodesWithSameHeight = leverRepresentation.getOrDefault(deep, new ArrayList<>());
            nodesWithSameHeight.add(node);
            leverRepresentation.putIfAbsent(deep, nodesWithSameHeight);
            createRepresentation(leverRepresentation, node.left, deep + 1);
            createRepresentation(leverRepresentation, node.right, deep + 1);
        }
    }

    private static class Node {
        Node left;
        Node right;
        int data;
        int height;
        int count;

        Node(int data) {
            this.data = data;
            count = 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (data != node.data) return false;
            if (height != node.height) return false;
            if (count != node.count) return false;
            if (left != null ? !left.equals(node.left) : node.left != null) return false;
            return right != null ? right.equals(node.right) : node.right == null;
        }

        @Override
        public int hashCode() {
            int result = left != null ? left.hashCode() : 0;
            result = 31 * result + (right != null ? right.hashCode() : 0);
            result = 31 * result + data;
            result = 31 * result + height;
            result = 31 * result + count;
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("left=").append(left);
            sb.append(", right=").append(right);
            sb.append(", data=").append(data);
            sb.append(", height=").append(height);
            sb.append(", count=").append(count);
            sb.append('}');
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinarySearchTree tree = (BinarySearchTree) o;

        if (size != tree.size) return false;
        if (nodeCount != tree.nodeCount) return false;
        return root != null ? root.equals(tree.root) : tree.root == null;
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + nodeCount;
        result = 31 * result + (root != null ? root.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BinarySearchTree{");
        sb.append("size=").append(size);
        sb.append(", nodeCount=").append(nodeCount);
        sb.append(", root=").append(root);
        sb.append('}');
        return sb.toString();
    }
}