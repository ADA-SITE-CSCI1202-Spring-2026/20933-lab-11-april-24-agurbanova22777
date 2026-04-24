public class CoordinationExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Thread producer = new Thread(new Producer(resource));
        Thread consumer = new Thread(new Consumer(resource));

        producer.start();
        consumer.start();
    }
}

class SharedResource {
    private int value;
    private boolean bChanged = false;

    public synchronized int get() throws InterruptedException {
        while (!bChanged) {
            wait();
        }
        bChanged = false;
        notify();
        return value;
    }

    public synchronized void set(int value) {
        this.value = value;
        bChanged = true;
        notify();
    }
}

class Producer implements Runnable {
    private final SharedResource resource;

    public Producer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                resource.set(i);
                System.out.println("Produced: " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private final SharedResource resource;

    public Consumer(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                int value = resource.get();
                System.out.println("Consumed: " + value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
