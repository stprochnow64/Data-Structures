package linked_yarn;

import java.util.NoSuchElementException;

import yarn.Yarn;

public class LinkedYarn implements LinkedYarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Node head;
    private int size, uniqueSize, modCount;


    // -----------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------
    LinkedYarn () {
    	    head = null;
    	    size = 0;
    	    uniqueSize = 0;
    	    modCount = 0;
    }

    LinkedYarn (LinkedYarn other) {
        this.head = new Node(other.head.text, other.head.count);
        this.size = other.size;
        this.uniqueSize = other.uniqueSize;
        this.modCount = other.modCount;
        for (Node n = other.head.next; n != null; n = n.next) {
        	     head.next = new Node(n.text, n.count);
        }


    }


    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
    	    return (size == 0);
    }

    public int getSize () {
    	    return size;
    }

    public int getUniqueSize () {
    	    return uniqueSize;
    }

    public void insert (String toAdd) {

        if (isEmpty()) {
            head = new Node(toAdd, 1);
            uniqueSize ++;
        }
        else if (contains(toAdd)) {
            find(toAdd).count++;
        }
        else {
            Node tempNode = findTail();
            tempNode.next = new Node (toAdd, 1);
            tempNode.next.prev = tempNode;
            uniqueSize ++;
        }
        size++;
        modCount++;
    }

    public int remove (String toRemove) {
        if (this.isEmpty()){
            throw new IllegalStateException();
        }
        if (!this.contains(toRemove)){
            return 0;
        }
        Node nodeToRemove = find(toRemove);
        if (nodeToRemove.count == 1) {
            removeAll(toRemove);
            return 0;
        } else {
            nodeToRemove.count--;
            size--;
            modCount++;
            return nodeToRemove.count;
        }
    }

    public void removeAll (String toNuke) {
        if (contains(toNuke)) {
            Node n = find(toNuke);
            size -= n.count;
            uniqueSize--;
            modCount++;
            n.count = 0;

            if (n == head) {
                head = n.next;
                if (head == null) {return;}
                head.prev = null;
            }
            if (n.next != null) {
                n.next.prev = n.prev;
            }
            if (n.prev != null) {
                n.prev.next = n.next;
            }
        }
    }

    public int count (String toCount) {
    	    int counter = 0;
        if (this.contains(toCount)) {
            Node n = find(toCount);
        	    	    counter = n.count;
        	}
        return counter;
    }

    public boolean contains (String toCheck) {
    	    boolean output = false;
    	    Node n = find(toCheck);
    	    if (n != null) {
    	    	    	output = true;
    	    	}
    	    return output;
    }

    public String getMostCommon () {
        int currentHighestCount = 0;
        String currentHighestString = "";
    	    if (size == 0) {
    	    	    currentHighestString = null;
    	    }
    	    for (Node n = this.head; n != null; n = n.next) {
    	    	    int currentTestCount = n.count;
    	    	    if (currentTestCount > currentHighestCount) {
    	    	    	    currentHighestString = n.text;
    	    	    	    currentHighestCount = currentTestCount;
    	    	    }
    	    }
    	    return currentHighestString;
    	}

    public void swap (LinkedYarn other) {
        Node tempHead = other.head;
        int tempSize = other.size;
        int uniqueTempSize = other.uniqueSize;
        int tempModCount = other.modCount;

        other.head = this.head;
        other.size = this.size;
        other.uniqueSize = this.uniqueSize;
        other.modCount = this.modCount + 1;

        this.head = tempHead;
        this.size = tempSize;
        this.uniqueSize = uniqueTempSize;
        this.modCount = tempModCount + 1;
    }

    public LinkedYarn.Iterator getIterator () {
    	    if (this.isEmpty()) {
    	    	    throw new IllegalStateException();
    	    }
    	    return new Iterator(this);
    }


    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------

    public static LinkedYarn knit (LinkedYarn y1, LinkedYarn y2) {
        LinkedYarn knittedYarn = new LinkedYarn(y1);
        for (Node n = y2.head; n != null; n = n.next) {
            for (int x = 0; x < n.count; x++) {
                knittedYarn.insert(n.text);
            }
        }
        return knittedYarn;
    }

    public static LinkedYarn tear (LinkedYarn y1, LinkedYarn y2) {
        LinkedYarn tornYarn = new LinkedYarn();
        Node y1Current = y1.head;
        while (y1Current != null){
            if (!y2.contains(y1Current.text)){
                for (int i = 0; i < y1Current.count; i++){
                    tornYarn.insert(y1Current.text);
                }
            } else {
                if (y1Current.count > y2.count(y1Current.text)){
                    for (int i = 0; i < y1Current.count - y2.count(y1Current.text); i++){
                        tornYarn.insert(y1Current.text);
                    }
                }
            }
            y1Current = y1Current.next;
        }
        return tornYarn;
    }

    public static boolean sameYarn (LinkedYarn y1, LinkedYarn y2) {
        return (tear(y1, y2).isEmpty() && tear(y2, y1).isEmpty());
    }

    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------

    private Node find (String toFind) {
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        Node current = head;
        while (current != null){
            if (current.text.equals(toFind)){
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private Node findTail() {
        for (Node n = head; n != null; n = n.next) {
            if (n.next == null) {
                return n;
            }
        }
        return null;
    }

    public String toString() {
        String output = "";
        for (Node n = head; n != null; n = n.next) {
            output += n.text + ":" + n.count + ",";
        }
        return output;
    }


    // -----------------------------------------------------------
    // Inner Classes
    // -----------------------------------------------------------

    public class Iterator implements LinkedYarnIteratorInterface {
        LinkedYarn owner;
        Node current;
        int itModCount;
        private int currentNumInNode;

        Iterator (LinkedYarn y) {
        	    owner = y;
        	    current = y.head;
        	    itModCount = y.modCount;
        	    currentNumInNode = 1;
        }

        public boolean hasNext () {
        	    return (currentNumInNode < current.count || current.next != null);
        }

        public boolean hasPrev () {
        	    return (currentNumInNode >1 || current != head);
        }

        public boolean isValid () {
        	    return (owner.modCount == this.itModCount);
        }

        public String getString () {
            if (!isValid()) {
                return null;
            }
        	    return current.text;
        }

        public void next () {
        	    if (!this.isValid()) {
        	    	    throw new IllegalStateException();
        	    } else if (!this.hasNext()) {
        	    	    throw new NoSuchElementException();
        	    }
        	    if (currentNumInNode < current.count) {
        	    	    currentNumInNode++;
        	    	} else {
        	    	    current = current.next;
        	    	    currentNumInNode = 1;
    	    	    }
        }

        public void prev () {
            if (!isValid()) {
                throw new IllegalStateException();
            }
            if (!hasPrev()) {
                throw new NoSuchElementException();
            }
            if (currentNumInNode > 1) {
                currentNumInNode--;
            } else {
                current = current.prev;
                currentNumInNode = current.count;
            }
        }

        public void replaceAll (String toReplaceWith) {
        	    if (!this.isValid()) {
        	    	    throw new IllegalStateException();
        	    }
        	    if (owner.contains(toReplaceWith)) {
        	        int toAdd = owner.count(toReplaceWith);
        	        current.count += toAdd;
        	        owner.remove(toReplaceWith);
        	    }
        	    current.text = toReplaceWith;
        	    owner.modCount++;
        	    itModCount++;
        }
    }


    class Node {
        Node next, prev;
        String text;
        int count;

        Node (String t, int c) {
            text = t;
            count = c;
        }
    }
