package sustech.edu.phantom.dboj.basicJudge;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PollingMessage {
    private Integer messageId;
    private String description;
    private Integer recordId;

    public static PollingMessage createErrorMessage(){
        PollingMessage pollingMessage=new PollingMessage();
        pollingMessage.messageId=2;
        pollingMessage.description="no such record";
        pollingMessage.recordId=-1;
        return pollingMessage;
    }
}
