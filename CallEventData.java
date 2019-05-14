import java.util.Arrays;

public class CallEventData {
    private String sender;
    private String[] receivers;

    public CallEventData(String sender, String[] receivers) {
        this.sender = sender;
        this.receivers = receivers;
    }

    public CallEventData() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        return sender +
                " : " + Arrays.toString(receivers) ;
    }
}
