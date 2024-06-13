import java.util.Random;

public class PartB {
    
    public static Random rand = new Random();

    public static String randABC() {
		int num = rand.nextInt(3); // 0 1 or 2
		if (num == 0)
			return "A";
		else if (num == 1)
			return "B";
		else
			return "C";
	}

    // keeping track of repeats of A's B's and C's
	public static boolean isRepeat(int[] count) {
		for (int l : count) {
			if (l > 1)
				return true; 
		}
		return false;
	}

	public static boolean containsLetter(String l, String[] array) {
		for (String r : array) {
			if (r == null) // empty array
				return false;
			else if (r.equals(l))
				return true; // contains duplicate
		}
		return false; // no duplicate
	}

	public static String[] randResources() {
		int strlen = rand.nextInt(3) + 1; // how many resources
		String[] reStrings = new String[strlen]; // initilaize array w/ capacity

		String abc;
		boolean isValid = false; // loop variable

		// assign random resources to array, NO DUPLICATES
		for (int i = 0; i < reStrings.length; i++) {
			do {
				abc = randABC(); // assign a random resource
				isValid = !containsLetter(abc, reStrings); // check if valid

				if (isValid) {
					reStrings[i] = abc; // add letter to String[]
				}

			} while (!isValid);

		}
		return reStrings; // return String[]
	}

	// note: this method completes when the cycle finishes
	public static SinglyLinkedList<String[]> modifiedList(SinglyLinkedList<String[]> list) {
		

		// compare current.resources to next.resources

		String[] cur = list.removeFirst(); 
		int[] tracker = { 0, 0, 0 }; 
		boolean repeat = true;
		
		do {
			String[] next = list.first();

			boolean Empty = true; //see if tracker has been reset
			for(int i = 0; i<tracker.length; i++){
				if(tracker[i] != 0){
					Empty = false;
				}
			}
			if(Empty == true){ //need to recount after reset
				for (String e : cur) {// track each element of FIRST program
					if (e.equals("A"))
						tracker[0]++;
					else if (e.equals("B"))
						tracker[1]++;
					else if (e.equals("C"))
						tracker[2]++;
				}
			}
			for (String e : next) {// track each element of SECOND program
				if (e.equals("A"))
					tracker[0]++;
				else if (e.equals("B"))
					tracker[1]++;
				else if (e.equals("C"))
					tracker[2]++;
			}

			repeat = isRepeat(tracker);

			// if counter > 1 for any resource, complete cycle
			if (repeat) {
				list.addLast(randResources());// add 2 processes
				list.addLast(randResources());
				break;
			}
			list.removeFirst();
            // set new current (walk over to next one)
			cur = next;

		} while (!repeat);


		return list;

	}

	public static void GenerateProcesses() { // print out statements
		// initial 20 processes
		SinglyLinkedList<String[]> list = new SinglyLinkedList<String[]>();
		for (int i = 0; i < 20; i++) {
			String[] objRes = randResources();
			// add resource to the list
			list.addLast(objRes);
		}

		int group = 1;
		int cyclesPerGroup = 100; 

		for (; group <= 10 ; group++) {

			for(int i = 0; i < cyclesPerGroup; i++){
				list = modifiedList(list); // modify List EACH CYCLE
			}
			int length = list.size(); // count programs left after 100 cycles

			// report processes left (length of list)
			System.out.println("Length of processes at cycle " + (group*cyclesPerGroup) + ": " + length + "\n");

			// report final
			if (group == 10) {
				System.out.println("We have a total of " + length + " left.");
			}
		}

	}

    public static void main(String[] args) {

		PartB.GenerateProcesses();
	}
}
