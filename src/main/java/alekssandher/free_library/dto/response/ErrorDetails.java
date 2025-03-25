package alekssandher.free_library.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "status", "title", "detail", "instance", "timesatamp"})
public abstract class ErrorDetails {
    @JsonProperty("status")
    private final int status;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("detail")
    private final String detail;

    @JsonProperty("type")
    private final String type;

    @JsonProperty("instance")
    private final String instance;

    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    protected ErrorDetails(int status, String title, String detail, String type, String instance) {
        this.status = status;
        this.title = title;
        this.detail = detail;
        this.type = type;
        this.instance = instance;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getType() {
        return type;
    }

    public String getInstance() {
        return instance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}