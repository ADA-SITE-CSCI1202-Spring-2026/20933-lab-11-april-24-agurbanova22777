public class DynamicScaling {
    public static void main(String[] args) throws InterruptedException {
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Available cores: " + coreCount);

        // Measure time with 1 thread
        measurePerformance(1);

        // Measure time with maximum threads
        measurePerformance(coreCount);
    }

    private static void measurePerformance(int threadCount) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new MathTask());
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with " + threadCount + " threads: " + (endTime - startTime) + "ms");
    }

    static class MathTask implements Runnable {
        @Override
        public void run() {
            long sum = 0;
            for (int i = 0; i < 10_000_000; i++) {
                for (int j = 0; j < 10; j++) {
                    sum += i * i * i + i * j;
                }
            }
        }
    }
}
