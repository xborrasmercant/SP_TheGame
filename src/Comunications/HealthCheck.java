package Comunications;

public class HealthCheck implements Runnable {

    private boolean working = true;
    private Channel channel;
    private long timeOut;
    private boolean killSocket = false;

    public HealthCheck(Channel channel, long timeOut) {
        this.channel = channel;
        this.timeOut = timeOut;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            while (working) {
                long currentTime = System.currentTimeMillis();
                long timeLastMessage = channel.getReceivedTime();
                long deltaTime = currentTime - timeLastMessage;
    
                if (deltaTime > timeOut) {
                    try {
                        if (killSocket) {
                            System.out.println("[HC] Health check stopped, killing socket...");
                            channel.downChannel();
                            stopExecution();
                        }
                        
                        System.out.println("[HC] Last message sent: " + deltaTime);
                        killSocket = true;
                        channel.sendPing();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopExecution() {
        System.err.println("[HC] Stopping Health Check connection...");
        working = false;
    }

    // Getters And Setters
    public long getTimeOut() {
        return timeOut;
    }

    public boolean isWorking() {
        return working;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isKillSocket() {
        return killSocket;
    }

    public void setKillSocket(boolean killSocket) {
        this.killSocket = killSocket;
    }
}
