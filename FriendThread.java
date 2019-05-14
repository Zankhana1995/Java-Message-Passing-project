public class FriendThread extends BaseThread {

    private static int WAIT_LIMIT_IN_MILLIS =  1 * 1000;

    private CallEventData callEventData;
    private MessagingService messagingService;

    public FriendThread(CallEventData callEventData, MessagingService messagingService) {
        this.callEventData = callEventData;
        this.messagingService = messagingService;
    }

    @Override
    protected int getWaitingTimeInMillis() {
        return WAIT_LIMIT_IN_MILLIS;
    }

    @Override
    protected void startTask() {
        for(String receiver : callEventData.getReceivers()){
            Message message = new Message(callEventData.getSender(), receiver, "");
            messagingService.send(message);
        }
    }

    @Override
    protected void process() {
        boolean isIdle = true;
        // Get message from queue
        Message message = messagingService.pollMessage(callEventData.getSender());
        if(message != null){
            isIdle = false;
            messagingService.acknowledge(message);
            messagingService.notifyAction(message.getReceiver() +" received intro message from "+ message.getSender()+" ["+ System.currentTimeMillis() +"]");
        }

        //Get reply from the Queue
        Message reply = messagingService.pollReply(callEventData.getSender());
        if(reply != null){
            isIdle = false;
            messagingService.notifyAction(reply.getSender() +" received reply message from "+ reply.getReceiver()+" ["+ System.currentTimeMillis() +"]");
        }
        if(!isIdle)
            updateTime();
    }

    @Override
    protected void printByeMessage() {
        System.out.println("\nProcess "+callEventData.getSender()+" has received no calls for 1 second, ending...");
    }
}
