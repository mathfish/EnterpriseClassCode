package thompson.client;

public class HessianResponse {
    private final long code;
    private final String content;

    public HessianResponse(long code, String content){
        this.code = code;
        this.content = content;
    }

    public long getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }
}
