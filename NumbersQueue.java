import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

class NumbersQueue{

	private volatile Queue<Integer> awaitingNumbers = new LinkedList<Integer>();
	private Queue<FibResult> results = new LinkedList<FibResult>();

	private volatile int numPendingQueries = 0;

	public void addNewNumber(int num){

		if (num<1 || num>45){
			System.out.println("Number out of range. Try again");
			return;
		}

		awaitingNumbers.add(num);
		numPendingQueries++;
		// notify();
	}


	public  void computeNumber(){

		boolean non = false;
		synchronized(this){
			non = awaitingNumbers.size()!=0;
		}

		if (non){
			int toCalculate = 0;
			synchronized(this){
				if (awaitingNumbers.size()!=0)
				toCalculate = awaitingNumbers.remove();
			}

			FibResult res = FibResult.getInstance(toCalculate);

			if (!res.getContinuationValue()){
				results.add(res);
			}else{
				long startTime = System.currentTimeMillis();
				int fib = calculateFib(toCalculate);
				long endTime = System.currentTimeMillis();

				long timeTaken  = endTime - startTime;
				res.updateInstance(toCalculate, fib, timeTaken);
				results.add(res);
			}
			// notifyAll();
			numPendingQueries--;
		}
	}

	private int calculateFib(int num){

		if (num<=2){
			return num;
		}

		return calculateFib(num-1) + calculateFib(num-2);
	}

	public void allResults(){

		while (results.size()!=0){
			System.out.println(results.remove());
		}

	}

}

class FibResult{

	private int number;
	private int fibNumber;
	private long computationTime;

	private static HashMap<Integer, FibResult> allNumbers = new HashMap<Integer, FibResult>(); 

	private FibResult(int number, int fibNumber, long computationTime){
		this.number = number;
		this.fibNumber = fibNumber;
		this.computationTime = computationTime;
	}

	public static FibResult getInstance(int num){

		if (allNumbers.containsKey(num)){
			return allNumbers.get(num);
		}

		FibResult f = new FibResult(num, -1, -1);
		allNumbers.put(num, f);
		return f;
	}

	public boolean getContinuationValue(){
		if (this.computationTime == -1){
			return true;
		}return false;
	}


	public FibResult updateInstance(int number, int fibNumber, long computationTime){
		if (allNumbers.containsKey(number)){
			FibResult f = allNumbers.get(number);
			f.computationTime = computationTime;
			f.fibNumber = fibNumber;
			return f;
		}

		return null;
	}

	@Override
	public String toString(){
		return "Number: "+number+"\nFibbonaci: "+fibNumber+"\nComputation Time: "+computationTime+" ms\n";
	}
}