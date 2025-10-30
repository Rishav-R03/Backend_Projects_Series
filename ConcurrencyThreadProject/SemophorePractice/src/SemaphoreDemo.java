private static final Semaphore CONNECTION_LIMITER = new Semaphore(3);

static class UserTask implements Runnable {
    private final String userName;

    public UserTask(String name) {
        this.userName = name;
    }

    @Override
    public void run() {
        IO.println(userName + " is waiting to connect...");
        try {
            CONNECTION_LIMITER.acquire();
            IO.println("✅" + userName + " Connected and is working.");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            CONNECTION_LIMITER.release();
            IO.println("❌" + userName + " Disconnected. Permits available" + CONNECTION_LIMITER.availablePermits());
        }
    }
}

void main() {
    IO.println("Starting 10 user tasks. Max concurrent connections 3");
    for (int i = 1; i <= 10; i++) {
        new Thread(new UserTask("User " + i)).start();
    }
}
