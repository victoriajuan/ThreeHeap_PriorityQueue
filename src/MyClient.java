import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyClient {
	
	private static ArrayList<Double> answer = new ArrayList<>();
	private static ThreeHeap heap = new ThreeHeap();
	
	/**
	 * add 100 random number to heap and comparison answer list
	 */
	private static void addNumToAnswer() {
		Random random = new Random();
		for(int i = 0; i < 1000; i++) {
			Double d = random.nextDouble();
			answer.add(d);
		}
	}
	
	private static void addNumToHeap() {
		for(Double d : answer) {
			heap.insert(d);
		}
	}
	
	/**
	 * test insert method and deleteMin
	 */
	private static void testInsertAndDelete() {
		addNumToAnswer();
		addNumToHeap();
		// sort answer list for comparison
		Collections.sort(answer);
		// use deleteMin
		ArrayList<Double> myAnswer = new ArrayList<>();
		while(!heap.isEmpty()) {
			myAnswer.add(heap.deleteMin());
		}
		// success if heap delete the smallest value every time call the method, false otherwise
		boolean success = true;
		for(int i = 0; i < answer.size(); i++) {
			if(!answer.get(i).equals(myAnswer.get(i))) {
				success = false;
			}
		}
		success = heap.isEmpty();
		System.out.println("insert and deleteMin is " + success);
	}
	
	/**
	 * test buildQueue by Floyd's method
	 */
	public static void testFloyd() {
		// make a new list
		answer.clear();
		addNumToAnswer();
		// use Floyd's algorithm to build heap
		heap.buildQueue(answer);
		
		boolean success = true;
		ArrayList<Double> myAnswer = new ArrayList<>();
		//add sorted values from heap's deleteMin
		while(!heap.isEmpty()) {
			myAnswer.add(heap.deleteMin());
		}
		// sort answer for comparison
		Collections.sort(answer);
	
		for(int i = 0; i < answer.size(); i++) {
			if(!answer.get(i).equals(myAnswer.get(i))) {
				success = false;
			}
		}
		success = heap.isEmpty();
		System.out.println("buildQueue is " + success);
	}
	
	public static void main(String[] args) {
		testInsertAndDelete();
		testFloyd();
	}
}
