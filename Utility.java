import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {

    public static CallEventData parseLog(String log){
        log = log.trim().replace(".","");
        String[] tokens = log.substring(1,log.length() -1).split(",", -1);
        String[] receivers = new String[tokens.length -1];
        CallEventData callEventData = new CallEventData(removeSpecialChars(tokens[0]), receivers);
        for(int i = 0; i < receivers.length ; i++){
            receivers[i] = removeSpecialChars(tokens[i+1]);
        }
        return  callEventData;
    }

    private static String removeSpecialChars(String data){
        return data.trim().replace("[","").replace("]","");
    }

    public static List<CallEventData> readCallLogs(String fileName){

        List<CallEventData> callEventDataList = new ArrayList<>();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){
            String line = null;
            while((line = fileReader.readLine()) != null){
                if(line.trim().length() == 0)
                    continue;
                callEventDataList.add(parseLog(line));
            }

        }catch (IOException ioe){
            System.out.println("Error occurred while reading file");
        }
        return  callEventDataList;
    }

    public  static int getRandomMilliTime(){
        return new Random().nextInt(100) + 1;
    }
}
