package lab9;

import lab9.UnresponsiveUI.MyThread;

/**
 * 
 * COMP 3021
 * 
This is a class that prints the maximum value of a given array of 90 elements

This is a single threaded version.

Create a multi-thread version with 3 threads:

one thread finds the max among the cells [0,29] 
another thread the max among the cells [30,59] 
another thread the max among the cells [60,89]

Compare the results of the three threads and print at console the max value.

 * 
 * @author valerio
 *
 */
public class FindMax {
	// this is an array of 90 elements
	// the max value of this array is 9999
	static int[] array = { 1, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2, 3, 4543,
			234, 3, 454, 1, 2, 3, 1, 9999, 34, 5, 6, 343, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3, 1, 34, 5, 6, 5, 63, 5, 34, 2, 78, 2, 3, 4, 5, 234, 678, 543, 45, 67, 43, 2,
			3, 4543, 234, 3, 454, 1, 2, 3 };

	public static void main(String[] args) {
		new FindMax().printMax();
	}
	public int max;

	public void printMax() {
		// this is a single threaded version
		//int max = findMax(0, array.length - 1);
		//System.out.println("the max value is " + max);

		Thread T1 = new Thread(new MyThread1(0,29));
		T1.start();
		try {
			T1.join();
		} catch (InterruptedException e) {
			
		}
		System.out.println("the max after the thread is " + max);
		System.out.println("the max value is " + max);

		
	}

	/**
	 * returns the max value in the array within a give range [begin,range]
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private class MyThread1 implements Runnable {
		private int start;
		private int end;
		MyThread1(int start, int end){
			this.start = start;
			this.end = end;
		}
		public void run(){
			Thread T2 = new Thread(new MyThread2(30,59));
			T2.start();
			try {
				T2.join();
				if(max < findMax(start, end))
				max = findMax(start, end);
				System.out.println("the max in thread 1 is " + max);
			}catch(InterruptedException ex)
			{
				
			}

		}
	}
	private class MyThread2 implements Runnable {
		private int start;
		private int end;
		MyThread2(int start, int end){
			this.start = start;
			this.end = end;
		}
		public void run(){
			Thread T3 = new Thread(new MyThread3(60,89));
			T3.start();
			try {
				T3.join();
				if(max < findMax(start, end))
				max = findMax(start, end);
				System.out.println("the max in thread 2 is " + max);
			}catch(InterruptedException ex)
			{
				
			}

		}
	}
	private class MyThread3 implements Runnable {
		private int start;
		private int end;
		MyThread3(int start, int end){
			this.start = start;
			this.end = end;
		}
		public void run(){
			max = findMax(start, end);
			System.out.println("the max in thread 3 is " + max);
		}
	}
	;
	private int findMax(int begin, int end) {
		// you should NOT change this function
		int max1 = array[begin];
		for (int i = begin + 1; i <= end; i++) {
			if (array[i] > max1) {
				max1 = array[i];
			}
		}
		return max1;
	}
}
