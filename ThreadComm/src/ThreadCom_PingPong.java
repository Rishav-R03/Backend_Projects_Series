public class ThreadCom_PingPong {
    private boolean pingTurn = true;

    public synchronized void ping() throws InterruptedException{
        for(int i = 1;i<=5;i++){
            while(!pingTurn){
                wait();
            }
            System.out.println("PING "+ i);
            pingTurn = false;
            notify(); // tell pong it's their turn
        }
    }

    public synchronized void pong() throws   InterruptedException{
        for(int i = 1;i<=5;i++){
            while(pingTurn){
                wait();
            }
            System.out.println("   Pong " + i);
            pingTurn = true;
            notify();// tell ping it's his turn
        }
    }

    public static void main(String [] args){
        ThreadCom_PingPong pingpong = new ThreadCom_PingPong();
        Thread pingThread = new Thread(()->{
            try{
                pingpong.ping();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Ping-1");

        Thread pongThread = new Thread(()->{
            try{
                pingpong.pong();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Pong-1");

        System.out.println("=== Ping-Pong Thread ===");
        pingThread.start();
        pongThread.start();
        try{
            pingThread.join();pongThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
