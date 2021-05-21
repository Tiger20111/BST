package tree.node;

import java.util.concurrent.locks.ReentrantLock;

public class Node {
    private int key;
    private int value;
    private Node rightChild;
    private Node leftChild;
    private Node father;
    private ReentrantLock locker;
    private boolean alive;
    private boolean isRoot;

    public Node(int key, int value, Node father) {
        this.key = key;
        this.value = value;
        this.father = father;
        this.locker = new ReentrantLock();
    };

    public Node() {
        this.isRoot = true;
    }

    public int getKey() {
        return key;
    }

    public boolean getAlive() {
        return alive;
    }

    public int getValue() {
        return value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setDeleted() {
        this.alive = false;
    }

    public Node getFather() {
        return father;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean lock() {
        return true;
    }

    public boolean unlock() {
        return true;
    }
}
