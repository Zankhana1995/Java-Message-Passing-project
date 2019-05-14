public interface MessagingService {

    void send(Message message);

    void acknowledge(Message message);

    Message pollMessage(String receiverName);

    Message pollReply(String senderName);

    void notifyAction(String notification);
}
