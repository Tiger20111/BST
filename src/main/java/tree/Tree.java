package tree;

import tree.node.Node;

class Trio {
    Node gprev;
    Node prev;
    Node curr;
}

public class Tree {
    private Node root;
    public Tree() {
        this.root = new Node();
    }

    public boolean insert(int key, int value) {
        Trio trio = searchFromRoot(key);
        return insertWithTrio(trio, key, value);
    }

    private boolean insertWithTrio(Trio trio, int key, int value) {
        if (trio.curr == null) {
            return lockToInsertNull(trio, key, value);
        }
        if (trio.curr.getKey() == key) {
            return false;
        }
        trio.curr = trio.prev;
        Trio newTrio = search(trio, key);
        return insertWithTrio(trio, key, value);
    }

    private boolean lockToInsertNull(Trio trio, int key, int value) {
        trio.prev.lock();
        if (trio.curr.getAlive()) {
            return setToChild(trio, key, value);
        } else {
            trio.curr = getAliveFather(trio.prev);
            Trio newTrio = search(trio, key);
            return insertWithTrio(newTrio, key, value);
        }
    }

    private Node getAliveFather(Node node) {
        while (!node.getAlive()) {
            node = node.getFather();
        }
        return node;
    }

    private boolean setToChild(Trio trio, int key, int value) {
        if (trio.prev.getKey() < key) {
            if (trio.prev.getRightChild() == null) {
                Node newCurr = new Node(key, value, trio.prev);
                trio.prev.setRightChild(newCurr);
                return true;
            } else {
                trio.curr = trio.prev.getRightChild();
                trio.prev.unlock();
                Trio newTrio = search(trio, key);
                return insertWithTrio(newTrio, key, value);
            }
        } else {
            if (trio.prev.getLeftChild() == null) {
                Node newCurr = new Node(key, value, trio.prev);
                trio.prev.setLeftChild(newCurr);
                return true;
            } else {
                trio.curr = trio.prev.getLeftChild();
                trio.prev.unlock();
                Trio newTrio = search(trio, key);
                return insertWithTrio(newTrio, key, value);
            }
        }
    }

    public boolean remove(int key) {
        Trio trio = searchFromRoot(key);
        return removeWithTrio(trio, key);
    }

    private boolean removeWithTrio(Trio trio, int key) {
        trio.curr.lock();
        trio.prev.lock();
        if (!trio.prev.isRoot()) {
            trio.gprev.lock();
        }
        if (!trio.gprev.getAlive() || !trio.prev.getAlive() || !trio.curr.getAlive()) {
            if (trio.gprev != null) {
                trio.gprev.unlock();
            }
            trio.prev.unlock();
            trio.curr.unlock();
            remove(key);
        }
        
    }

    public boolean contains(int key) {
        Trio trio = searchFromRoot(key);
        return trio.curr != null;
    }

    public Trio searchFromRoot(int key) {
        Trio trio = new Trio();
        trio.gprev = null;
        trio.prev = null;
        trio.curr = root;
        return search(trio, key);
    }

    private Trio search(Trio trio, int key) {
        while (trio.curr != null && trio.curr.getKey() != key) {
            trio.gprev = trio.prev;
            trio.prev = trio.curr;
            if (trio.curr.getKey() < key) {
                trio.curr = trio.curr.getRightChild();
            } else {
                trio.curr = trio.curr.getLeftChild();
            }
        }
        return trio;
    }
}
