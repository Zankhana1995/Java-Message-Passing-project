import java.util.List;

public class Exchange {

    public static void main(String[] args){
        List<CallEventData> callEventDataList = Utility.readCallLogs("calls.txt");
        MasterThread masterThread = new MasterThread(callEventDataList);
        Thread thread = new Thread(masterThread, "Master Thread");
        thread.start();
    }
}
