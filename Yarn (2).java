package yarn;

public class Yarn implements YarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Strand[] items;
    private int size;
    private int uniqueSize;
    private final int MAX_SIZE = 100;


    // -----------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------


    Yarn () {
    	    size = 0;
    	    uniqueSize = 0;
    	    items = new Strand[MAX_SIZE];

    }

    Yarn (Yarn other) {
    	    size = other.size;
    	    uniqueSize = other.uniqueSize;
    	    items = new Strand[MAX_SIZE];
    	    for (int i = 0; i < other.uniqueSize; i++) {
    		    this.items[i] = new Strand(other.items[i].text, other.items[i].count);
    	    }
    }

    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------


    public boolean isEmpty () {
    	    return (size == 0) {
    }

    public int getSize () {
    	    return size;
    }

    public int getUniqueSize () {
    	    return uniqueSize;
    }

    public boolean insert (String toAdd) {
            int MAX_SIZE = 100;
    	    if (size >= MAX_SIZE) {
    	    	    return false;
    	    } else {
	    	    if (this.contains(toAdd)) {
	    	    	    for (int i = 0; i < uniqueSize; i++) {
	    	    	    	    if (items[i].text.equals(toAdd) ) {
	    	    	    	    	    items[i].count++;
	    	    	    	    	    size++;
	    	    	    	    }
		    	    	}
	    	    } else {
    	    	    	Strand add = new Strand(toAdd, 1);
    	    	    	items[uniqueSize] = add;
    	    	        uniqueSize++;
    	                size++;
	    	    }
    	    	return true;
    	    }
    }

    public int remove (String toRemove) {

    	    int output = 0;
    	    for (int i = 0; i < uniqueSize; i++) {
    	    	    if (items[i].text.equals(toRemove)) {
    	    	    	    items[i].count--;
    	    	    	    size--;
    	            output = items[i].count;
    	    	    	    if (items[i].count == 0) {
        	    	    	    this.shiftLeft(i);
        	    	    	    uniqueSize--;
    	    	    	    }
    	    	    }
    	    }
        return output;
    }

    public void removeAll (String toNuke) {

    	    for (int i = 0; i < uniqueSize; i++) {
    	    	    if (items[i].text.equals(toNuke)) {
    	    	    	    size -= items[i].count;
    	    	    	    items[i].count = 0;
    	    	    	    this.shiftLeft(i);
    	    	    	    uniqueSize--;
    	    	    }
    	    }
    }

    public int count (String toCount) {
    	    int counter = 0;
    	    for (int i = 0; i < uniqueSize; i++) {
    	    	    if (items[i].text.equals(toCount)) {
    	    	    	    counter = items[i].count;
    	    	    }
    	    }
    	    return counter;
    }

    public boolean contains (String toCheck) {
    	    Boolean result = false;
        for (int i = 0; i < uniqueSize; i++) {
        	    if (items[i].text.equals(toCheck)) {
        	    	    result = true;
        	    }
        }
        return result;
    }

    public String getNth (int n) {
    	    String output = "";
    	    int currentNum = 0;
    	    if (n < 0 || n > size) {
    	    	    throw new IndexOutOfBoundsException();
    	    }
    	    if (n == 0) {
    	    	    output = items[0].text;
    	    } else {
        	    for (int i = 0; i < uniqueSize; i++) {
    	    	        currentNum += items[i].count;
    	    	        if (n < currentNum) {
	    	        	    output = items[i].text;
	    	        	    break;
    	    	        }
    	        }
    	    }
        return output;
    }

    public String getMostCommon () {
	    int currentHighestCount = 0;
	    String currentHighestString = "";
    	    if (size == 0) {
    	    	    currentHighestString = null;
    	    }
    	    for (int i = 0; i < uniqueSize; i++) {
    	    	    int currentTestCount = items[i].count;
    	    	    if (currentTestCount > currentHighestCount) {
    	    	    	    currentHighestString = items[i].text;
    	    	    	    currentHighestCount = currentTestCount;
    	    	    }
    	    }
    	    return currentHighestString;
    }

    public void swap (Yarn other) {
    	    Yarn tempStore = new Yarn();

    	    tempStore.size = other.size;
    	    tempStore.uniqueSize = other.uniqueSize;
    	    tempStore.items = other.items;

    	    other.size = this.size;
    	    other.uniqueSize = this.uniqueSize;
    	    other.items = this.items;

    	    this.size = tempStore.size;
    	    this.uniqueSize = tempStore.uniqueSize;
    	    	this.items = tempStore.items;
    }

    public String toString () {
    	    String output = "";
    	    if (uniqueSize == 0) {
    	    	    output = "{  }";
    	    } else {
    	        output = "{ ";
    	        for (int i = 0; i < uniqueSize - 1; i++) {
    	    	        output += "\"" + items[i].text + "\": " + items[i].count + ", ";
    	        }
    	        output += "\"" + items[uniqueSize - 1].text + "\": " + items[uniqueSize - 1].count + " }";
    	    }
    	    return output;
    }

    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------

    public static Yarn knit (Yarn y1, Yarn y2) {
    	    Yarn knittedYarn = new Yarn(y1);
    	    for (int i = 0; i < knittedYarn.getUniqueSize(); i++ ) {
    	    	    for (int j = 0; j < y2.getUniqueSize(); j++) {
    	    	    	    if (knittedYarn.items[i].text == (y2.items[j].text)) {
    	    	    	    	    knittedYarn.items[i].count += y2.items[j].count;
    	    	    	    }
    	    	    }
    	    }
            // >> [AF] More repetition here -- time to use a helper method!
    	    for (int k = 0; k < y2.getUniqueSize(); k++) {
    	    	    if (! knittedYarn.contains(y2.items[k].text)) {
    	    	    	    knittedYarn.insert(y2.items[k].text);
    	    	    }
    	    }
    	    return knittedYarn;
    }

    public static Yarn tear (Yarn y1, Yarn y2) {
    	    Yarn tornYarn = new Yarn(y1);
	     for (int i = 0; i < y2.getUniqueSize(); i++) {
	    	     if (tornYarn.contains(y2.items[i].text)) {
	    	    	    	 for (int j = 0; j < y2.items[i].count; j++) {
		    	    	    	 tornYarn.remove(y2.items[i].text);
	    	    	    	 }
	    	    }
	    }
    	    return tornYarn;
    }

    // >> [AF] Nice!
    public static boolean sameYarn (Yarn y1, Yarn y2) {
    	    return (tear(y1, y2).isEmpty() && tear(y2, y1).isEmpty());
    }

    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    // Add your own here!

    private void shiftLeft (int index) {
    	    for (int i = index; i < uniqueSize - 1; i++) {
    	    	    items[i] = items[i + 1];
    	    }
    }
}

class Strand {
    String text;
    int count;

    Strand (String s, int c) {
        text = s;
        count = c;
    }
}

// >> [AF] >>> Style Quality: Good <<<
// Legend: [X] = Needs major improvement
//         [~] = Needs some improvement
//         [ ] = You done good!
// Your Checklist:
// [ ] Variables named to clearly indicate purpose
// [ ] Adequate documentation of code fragments requiring it
// [~] Kept code DRY (Didn't Repeat Yourself)
// [ ] Appropriate definition, and usage of, helper methods
// [X] Spacing and indents consistent and appropriate
