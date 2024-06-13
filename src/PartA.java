import java.util.Scanner;

public class PartA {
    public static Scanner scn = new Scanner(System.in);

	//create list
	public static SinglyLinkedList<String[]> oneLineList(String line) {

		// make String array, splitting at semicolon
		String[] processes = line.split(";");
		String[][] resources = new String[processes.length][];

		// add resources into array
		for (int i = 0; i < processes.length; i++) {
			int start = 0;
			int end = 0;
			for (int j = 0; j < processes[i].length(); j++) {
				if (processes[i].charAt(j) == '(') {
					start = j + 1;
				}
				if (processes[i].charAt(j) == ')') {
					end = j; // substring is exclusive at end
				}
			}
			String resString = processes[i].substring(start, end);

			String[] resources1 = resString.split(",");

			resources[i] = resources1; // add array of resources to that process
		}

		// now make a list, passing the String[] as the element
		SinglyLinkedList<String[]> list = new SinglyLinkedList<String[]>();
		// add nodes
		for (int i = 0; i < processes.length; i++) {
			list.addLast(resources[i]);
		}

		return list;

	} 

	// keeping track of repeats of A's B's and C's
	public static boolean isRepeat(int[] count) {
		for (int l : count) {
			if (l > 1)
				return true; // there is a repeat
		}
		return false;
	}

	// determine which processes can run in same cycle
	public static int countCycles(SinglyLinkedList<String[]> list) {
		int counter = 1;
		String[] curElem = list.removeFirst(); // element(s) of first node
		int[] tracker = { 0, 0, 0 }; // use ONE array to track elements

		do {
			String[] nextElem = list.removeFirst(); // element of second node

			// compare curElem resources to nextElem resources
			// do a counter array to track A's B's and C's

			boolean Empty = true; //see if tracker has been reset
			for(int i = 0; i<tracker.length; i++){
				if(tracker[i] != 0){
					Empty = false;
				}
			}
			if(Empty == true){ //need to recount after reset
				for (String e : curElem) {// track each element of FIRST program
					if (e.equals("A"))
						tracker[0]++;
					else if (e.equals("B"))
						tracker[1]++;
					else if (e.equals("C"))
						tracker[2]++;
				}
			}
			for (String e : nextElem) {// track each element of SECOND program
				if (e.equals("A"))
					tracker[0]++;
				else if (e.equals("B"))
					tracker[1]++;
				else if (e.equals("C"))
					tracker[2]++;
			}

			// if counter > 1 for any resource, start new cycle
			if (isRepeat(tracker)) {
				counter++;     
				tracker = new int[] { 0, 0, 0 }; // reset tracker
			}
			// set new current (walk over to next one)
			curElem = nextElem;

		} while (!list.isEmpty());

		return counter;
	}

    public static void main(String[] args) { //reading from console, not file

        System.out.println("Input the processes you want to run: ");
        String line = scn.nextLine();

		// make list the line
		SinglyLinkedList<String[]> list = PartA.oneLineList(line);

		// calculate/print cycles required
		int cycles = PartA.countCycles(list);
		System.out.println("Total required cycles: " + cycles + "\n");
		
	}
}
