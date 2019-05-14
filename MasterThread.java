import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class MasterThread extends BaseThread implements MessagingService {

    private List<CallEventData> callEventDataList;
    private static int WAIT_LIMIT_IN_MILLIS =  (int)(1.5 * 1000);
    private Map<String,Queue<Message>> messagingQueue = new HashMap<>();
    private Map<String,Queue<Message>> replyQueue = new HashMap<>();

    public MasterThread(List<CallEventData> callEventDataList) {
        this.callEventDataList = callEventDataList;
    }

    private void initiateFriendThread(){
        List<Thread> friendThreads = new ArrayList<>();
        for(CallEventData callEventData : callEventDataList){
            Queue<Message> queue = new LinkedBlockingDeque<Message>();
            Queue<Message> replyQueing = new LinkedBlockingDeque<Message>();
            messagingQueue.put(callEventData.getSender(), queue);
            replyQueue.put(callEventData.getSender(), replyQueing);
            FriendThread friendThread = new FriendThread(callEventData, this );
            Thread thread = new Thread(friendThread, callEventData.getSender() + " - Thread");
            friendThreads.add(thread);
        }
        for(Thread thread : friendThreads){
            thread.start();
        }
    }

    @Override
    protected int getWaitingTimeInMillis() {
        return WAIT_LIMIT_IN_MILLIS;
    }

    @Override
    protected void startTask() {

        System.out.println("** Calls to be made **");
        System.out.println("--------------------------------------------------------------------");
        for(CallEventData callEventData : callEventDataList){
            System.out.println(callEventData);
        }
        System.out.println("--------------------------------------------------------------------");

        initiateFriendThread();
    }

    @Override
    protected void process() {
        // No Action required
    }

    @Override
    protected void printByeMessage() {
        System.out.println("\nMaster has received no replies for 1.5 seconds, ending...");
    }

    @Override
    public void send(Message message) {
        messagingQueue.get(message.getReceiver()).add(message);
        updateTime();
    }

    @Override
    public void acknowledge(Message message) {
        replyQueue.get(message.getSender()).add(message);
        updateTime();
    }

    @Override
    public Message pollMessage(String receiverName){
        return  messagingQueue.get(receiverName).poll();
    }

    @Override
    public Message pollReply(String senderName){
        return replyQueue.get(senderName).poll();
    }

    @Override
    public void notifyAction(String notification){
        System.out.println(notification);
        updateTime();
    }
}