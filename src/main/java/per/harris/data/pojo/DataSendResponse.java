package per.harris.data.pojo;

public class DataSendResponse {

    private boolean success;

    private String message;

    public DataSendResponse(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess()
    {
        return success;
    }
}
