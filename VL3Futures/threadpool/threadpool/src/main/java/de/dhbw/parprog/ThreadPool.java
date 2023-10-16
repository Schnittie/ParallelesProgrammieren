package de.dhbw.parprog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class ThreadPool implements Callable <Integer> {
	public int doCalculation() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return 42;
	}

	public static void main(String[] args) {
		ExecutorService test = Executors.newFixedThreadPool(10);
		System.out.println("Calculation started");
		List<Future<Integer>> results= new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			results.add(test.submit(new ThreadPool()));
		}
		int result = 0;
		for (Future<Integer> integerFuture : results){
			try {
				result += integerFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
        }
		System.out.println("DAS ergebnis ist "+ result);
		test.shutdown();
	}

	@Override
	public Integer call() {
		return doCalculation();
	}
}
