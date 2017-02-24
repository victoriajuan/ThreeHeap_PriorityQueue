import java.util.List;

/**
 * implement a priority queue using a three children heap
 * @author victoriajuan
 * @since 01/27/2017
 *
 */
public class ThreeHeap implements PriorityQueue{
	private double[] heap;
	private int size;
	
	/**
	 * constructor
	 */
	public ThreeHeap() {
		this.heap = new double[10];
		this.size = 0;
	}

	/**
	 * check if the priority is empty
	 */
	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * return the queue's size
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * find the minimum node/value in the queue
	 */
	@Override
	public double findMin() {
		return (heap[0]);
	}

	/**
	 * insert a value into the priority queue
	 * 
	 *  @param x
	 * 		insert value
	 */
	@Override
	public void insert(double x) {
		easyInsert(x, false);
	}
	
	/**
	 * insert a value into the priority queue
	 * 
	 * @param x
	 * 		insert value
	 * @param easy
	 * 		Determine what kind of insert function should be using
	 */
	private void easyInsert(double x, boolean easy) {
		//resize the array when it's full
		if(size >= heap.length){
			double[] temp = new double[heap.length * 2];
			for(int i = 0; i < heap.length; i++){
				temp[i] = heap[i];
			}
			heap = temp;
		}
		//insert into the heap
		heap[size] = x;
		if(!easy) {
			percolateUp(size);
		}
		size++;
	}
	
	
	
	/**
	 * delete the minimum number form the priority queue
	 * 
	 * @return root
	 * 		the smallest value in the queue
	 */
	@Override
	public double deleteMin() {
		//when the heap is empty throw exception from provided code
		if(size == 0){
			throw new EmptyHeapException();
		}
		//store the current minimum root
		double root = heap[0];
		//maintain the heap structure
		heap[0] = heap[size-1];
		heap[size-1] = 0.0;
		
		percolateDown(0);
		size--;
		return root;
	}
	

	/**
	 * Implement Floyd's Method to build from a empty heap runs in O(N) time.
	 */
	@Override
	public void buildQueue(List<Double> list) {
		for(double d : list) {
			easyInsert(d, true);
		}
		for(int i = (size+1)/2; i >= 0; i--){
			percolateDown(i);
		}
	}

	/**
	 * set the priority queue to empty
	 */
	@Override
	public void makeEmpty() {
		this.heap = new double[10];
		this.size = 0;
		
	}
	
	/**
	 * override the object class
	 */
	@Override
	public String toString(){
		StringBuilder sBuilder = new StringBuilder();
	    for(int i = 0; i < heap.length; i++){
	    	sBuilder.append(heap[i]);
	    	sBuilder.append(" ");
	    }
	    return sBuilder.toString();
	}

	/**
	 * 
	 * @param childIndex
	 * 		the index of the child node in heap
	 * @return 
	 * 		the index of the parent node from known child in heap
	 */
	private int parentIndex(int childIndex){
		return ((childIndex-1) / 3);
	}

	/**
	 * 
	 * @param parentIndex
	 * 		the index of parent node in heap
	 * @return
	 * 		the index array of the child nodes from known parent in heap
	 */
	private int[] childrenIndex(int parentIndex) {
		//three children heap structure
		int[] childrenIndexArray = new int[3];
		for(int i = 1; i < 4; i++){
			//when it's out of the bound
			if((3*parentIndex+i) > heap.length-1){
				childrenIndexArray[i-1] = -1;
			}else{
				childrenIndexArray[i-1] = 3*parentIndex+i;
			}
		}
		return childrenIndexArray;
	}
	
	/**
	 * 
	 * @param childrenIndex
	 * 		a array of children's index
	 * @return
	 * 		find the minimum index in a array of children's index
	 */
	private int findMinChild(int[] childrenIndex){
		int min;
		//when the heap is not empty, minimum value is the root, otherwise return -1
		if(childrenIndex[0] != -1 && heap[childrenIndex[0]] != 0.0){
			min = childrenIndex[0];
		}else {
			return -1;
		}
		//swap the values by comparing
		for(int i = 0; i < childrenIndex.length; i++){
			if(childrenIndex[i] != -1 && heap[childrenIndex[i]] != 0.0 && heap[childrenIndex[i]] < heap[min]){
				min = childrenIndex[i];
			}
		}
		return min;
	}
	
	/**
	 * Percolate up the node newly inserted at size
	 * 
	 * @param index
	 * 		size the index to percolate up the node
	 */
	private void percolateUp(int index) {
		//store the current node/value
		int parent = parentIndex(index);
		int child = index;
		//percolate up the node until meet the heap structure
		while(heap[parent] > heap[child]){
			double temp = heap[parent];
			//swap the child node/value and parent node/value
			heap[parent] = heap[child];
			heap[child] = temp;
			child = parent;
			parent = parentIndex(parent);
		}
	}
	
	/**
	 * Percolate down the node at the given index
	 * 
	 * @param index 
	 */
	private void percolateDown(int index) {
		while(true){
			int min = findMinChild(childrenIndex(index));
			if(min == -1 || heap[index] <= (heap[min])){
				break;
			}
			//swap the node/value with minimum node/value
			double temp = heap[index];
			heap[index] = heap[min];
			heap[min] = temp;
			index = min;
		}
	}
}
