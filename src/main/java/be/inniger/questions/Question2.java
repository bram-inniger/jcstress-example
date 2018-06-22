package be.inniger.questions;

/**
 * What are the possible values of result (resultA and resultB) after both threads have ran their course?
 */
public class Question2 {

	private boolean a = false;
	private boolean b = false;

	public static void main(String... args) throws InterruptedException {
		Question2 question = new Question2();
		Result result = new Result();

		Thread thread1 = new Thread(() -> question.setA_AndAssignB(result));
		Thread thread2 = new Thread(() -> question.setB_AndAssignA(result));

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		System.out.println(result);
	}

	private void setA_AndAssignB(Result result) {
		a = true;
		result.resultB = b;
	}

	private void setB_AndAssignA(Result result) {
		b = true;
		result.resultA = a;
	}

	private static class Result {
		private boolean resultA;
		private boolean resultB;

		@Override
		public String toString() {
			return "Result{" +
					"resultA=" + resultA +
					", resultB=" + resultB +
					'}';
		}
	}
}
