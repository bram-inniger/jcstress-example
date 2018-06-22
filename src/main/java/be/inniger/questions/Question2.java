package be.inniger.questions;

/**
 * What are the possible values of result (resultA and resultB) after both threads have ran their course?
 */
public class Question2 {

	private int a = 0;
	private int b = 0;

	public static void main(String... args) throws InterruptedException {
		Question2 question = new Question2();
		Result result = new Result();

		Thread thread1 = new Thread(() -> question.incrementA_AndAssignB(result));
		Thread thread2 = new Thread(() -> question.incrementB_AndAssignA(result));

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		System.out.println(result);
	}

	private void incrementA_AndAssignB(Result result) {
		a++;
		result.resultB = b;
	}

	private void incrementB_AndAssignA(Result result) {
		b++;
		result.resultA = a;
	}

	private static class Result {
		private int resultA;
		private int resultB;

		@Override
		public String toString() {
			return "Result{" +
					"resultA=" + resultA +
					", resultB=" + resultB +
					'}';
		}
	}
}
