// Time Complexity : O(1) for get, put
// Space Complexity : O(capacity)
// Did this code successfully run on Leetcode : Yes

// Your code here along with comments explaining your approach
import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    // LinkedList Node
    static class Node {
        int key, value;
        Node prev, next;

        Node() {}

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    int capacity;

    //Map to hold Key-Node pairs
    Map<Integer, Node> cacheMap = new HashMap<>();

    //Doubly Linked List holding values in order of access
    Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(!cacheMap.containsKey(key)) {
            return -1;
        }
        // get the node from the map
        Node accessedNode = cacheMap.get(key);
        // remove it from its current position
        remove(accessedNode);
        // move it to the front of the list (i.e. just after head)
        moveToFront(accessedNode);
        // return its value
        return accessedNode.value;
    }

    public void put(int key, int value) {
        if(cacheMap.containsKey(key)) {
            // get the node from the map
            Node accessedNode = cacheMap.get(key);
            // remove it from its current position
            remove(accessedNode);
            // move it to the front of the list (i.e. just after head)
            moveToFront(accessedNode);
            // update its value
            accessedNode.value = value;
        } else {
            if(cacheMap.size() < capacity) {
                //create a new node
                Node createdNode = new Node(key, value);
                // move it to the front of the list (i.e. just after head)
                moveToFront(createdNode);
                //put it in the map
                cacheMap.put(key, createdNode);
            } else {
                // if capacity exceeded
                // get the last node (LRU)
                Node evictedNode = tail.prev;
                // remove the node
                remove(evictedNode);
                // remove it from the map
                cacheMap.remove(evictedNode.key);
                evictedNode = null;
                // proceed with putting the new value
                // now that we have created space
                put(key, value);
            }
        }
    }

    private void moveToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}