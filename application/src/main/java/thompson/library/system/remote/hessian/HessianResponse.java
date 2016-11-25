package thompson.library.system.remote.hessian;

/**
 * Created by jonathanthompson on 11/24/16.
 */
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
