
public class SinglyLinkedList<E> implements Cloneable {
    // ---------------- nested Node class ----------------
    /**
     * Node of a singly linked list, which stores a reference to its
     * element and to the subsequent node in the list (or null if this
     * is the last node).
     */
    protected static class Node<E> {

        /** The element stored at this node */
        private E element; // reference to the element stored at this node

        /** A reference to the subsequent node in the list */
        private Node<E> next; // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods
        /**
         * Returns the element stored at the node.
         * 
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         * 
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        // Modifier methods
        /**
         * Sets the node's next reference to point to Node n.
         * 
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }

    } // ----------- end of nested Node class -----------

    // instance variables of the SinglyLinkedList
    /** The head node of the list */
    protected Node<E> head = null; // head node of the list (or null if empty)

    /** The last node of the list */
    private Node<E> tail = null; // last node of the list (or null if empty)

    /** Number of nodes in the list */
    private int size = 0; // number of nodes in the list

    /** Constructs an initially empty list. */
    public SinglyLinkedList() {
    } // constructs an initially empty list

    // access methods
    /**
     * Returns the number of elements in the linked list.
     * 
     * @return number of elements in the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Tests whether the linked list is empty.
     * 
     * @return true if the linked list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns (but does not remove) the first element of the list
     * 
     * @return element at the front of the list (or null if empty)
     */
    public E first() { // returns (but does not remove) the first element
        if (isEmpty())
            return null;
        return head.getElement();
    }

    /**
     * Returns (but does not remove) the last element of the list.
     * 
     * @return element at the end of the list (or null if empty)
     */
    public E last() { // returns (but does not remove) the last element
        if (isEmpty())
            return null;
        return tail.getElement();
    }

    // update methods
    /**
     * Adds an element to the front of the list.
     * 
     * @param e the new element to add
     */
    public void addFirst(E e) { // adds element e to the front of the list
        head = new Node<E>(e, head); // create and link a new node
        if (size == 0)
            tail = head; // special case: new node becomes tail also
        size++;
    }

    /**
     * Adds an element to the end of the list.
     * 
     * @param e the new element to add
     */
    public void addLast(E e) { // adds element e to the end of the list
        Node<E> newest = new Node<E>(e, null); // node will eventually be the tail
        if (isEmpty())
            head = newest; // special case: previously empty list
        else
            tail.setNext(newest); // new node after existing tail
        tail = newest; // new node becomes the tail
        size++;
    }

    /**
     * Removes and returns the first element of the list.
     * 
     * @return the removed element (or null if empty)
     */
    public E removeFirst() { // removes and returns the first element
        if (isEmpty())
            return null; // nothing to remove
        E answer = head.getElement();
        head = head.getNext(); // will become null if list had only one node
        size--;
        if (size == 0)
            tail = null; // special case as list is now empty
        return answer;
    }

    @SuppressWarnings({ "unchecked" })
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        SinglyLinkedList<E> other = (SinglyLinkedList<E>) o; // use nonparameterized type
        if (size != other.size)
            return false;
        Node<E> walkA = head; // traverse the primary list
        Node<E> walkB = other.head; // traverse the secondary list
        while (walkA != null) {
            if (!walkA.getElement().equals(walkB.getElement()))
                return false; // mismatch
            walkA = walkA.getNext();
            walkB = walkB.getNext();
        }
        return true; // if we reach this, everything matched successfully
    }

    @SuppressWarnings({ "unchecked" })
    public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
        // always use inherited Object.clone() to create the initial copy
        SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
        if (size > 0) { // we need independent chain of nodes
            other.head = new Node<E>(head.getElement(), null);
            Node<E> walk = head.getNext(); // walk through remainder of original list
            Node<E> otherTail = other.head; // remember most recently created node
            while (walk != null) { // make a new node storing same element
                Node<E> newest = new Node<>(walk.getElement(), null);
                otherTail.setNext(newest); // link previous node to this one
                otherTail = newest;
                walk = walk.getNext();
            }
        }
        return other;
    }

    public int hashCode() {
        int h = 0;
        for (Node<E> walk = head; walk != null; walk = walk.getNext()) {
            h ^= walk.getElement().hashCode(); // bitwise exclusive-or with element's code
            h = (h << 5) | (h >>> 27); // 5-bit cyclic shift of composite code
        }
        return h;
    }

    /**
     * Produces a string representation of the contents of the list.
     * This exists for debugging purposes only.
     */
    public String toString() {
        System.out.println("In my class");
        StringBuilder sb = new StringBuilder("(");
        Node<E> walk = head;
        while (walk != null) {
            sb.append(walk.getElement());
            if (walk != tail)
                sb.append(", ");
            walk = walk.getNext();
        }
        sb.append(")");
        return sb.toString();
    }

    // methods LAUREN AND JOYCE added
    public void Swap(int a, int b) throws Exception { // two positions to swap
        if (size() < 3) {
            throw new Exception();
        }
        // change the one before (a and b are not header or tail)
        Node<E> node1 = head; // before A
        Node<E> node2 = head; // after
        Node<E> node3 = head; // before B
        Node<E> node4 = head; // after
        Node<E> nodeA = head;
        Node<E> nodeB = head;

        // get Nodes A and B
        for (int n = 0; n < a - 1; n++) { // if head is first element...
            nodeA = nodeA.getNext();
        }
        for (int n = 0; n < b - 1; n++) {
            nodeB = nodeB.getNext();
        }

        // get previous and after Nodes
        for (int n = 0; n < a - 2; n++) { // go to the node before a
            node1 = node1.getNext();
        }
        node1.setNext(nodeB); // points to B

        for (int n = 0; n < a + 2; n++) { // node after a
            node2 = node2.getNext();
        }
        node2.setNext(nodeB);

        for (int n = 0; n < b - 2; n++) { // node before b
            node3 = node3.getNext();
        }
        node3.setNext(nodeA); // points to A

        for (int n = 0; n < b + 2; n++) { // node after b
            node4 = node4.getNext();
        }

        // B points to... A points to
        nodeA.setNext(node4);
        nodeB.setNext(node2);

    }

    public void RemoveAtEnd(int number) { // removes that number of nodes
        // from the end of the list

        for (int n = 0; n < number; n++) {
            Node<E> nextN = head;
            for (int c = 0; c < size - 2; c++) {
                nextN = nextN.getNext();
            }
            tail = nextN; // SET NEW TAIL, bye bye old tail
            tail.setNext(null);
            size -= 1; // decrease size (bc u removed it)

        }

    }

}
