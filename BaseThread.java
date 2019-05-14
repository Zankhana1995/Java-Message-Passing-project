/**
 * Base Thread having implementation for life cycle of Master and Friend.
 *
 * following is the pseudo code for it
 * Step 1 : Perform initial Task [Master and Friend class will be responsible for it]
 * Step 2 : Sleep for random time from 0 to 100 millisecond
 * Step 3 : perform processing Task
 *     [ Friend Thread : Check for the message and send reply if message received , Master Thread : No action required]
 * Step 4 : Check Thread is still with defined waiting time or not. if yes then stop thread else move to the step 2
 *
 */
abstract public class BaseThread implements Runnable{

    private  long lastUpdatedTime = System.currentTimeMillis();

    /**
     * Method Check Thread is still with defined waiting time or not
     * @return True if stop thread action required, False if need to wait for next replies if any
     */
    private boolean waitForNextMessage(){
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastUpdatedTime >= getWaitingTimeInMillis()){
            return  false;
        }
        return true;
    }

    @Override
    public void run() {
        startTask();
        try {
            do{
                Thread.sleep(Utility.getRandomMilliTime());
                process();
            }while(waitForNextMessage());
        } catch (InterruptedException e) {
            System.out.println("forcefully interrupted");
        }
        printByeMessage();
    }

    protected abstract int getWaitingTimeInMillis();

    protected abstract void startTask();

    protected abstract void process();

    protected void updateTime(){
        lastUpdatedTime = System.currentTimeMillis();
    }
    protected abstract void printByeMessage();
}
