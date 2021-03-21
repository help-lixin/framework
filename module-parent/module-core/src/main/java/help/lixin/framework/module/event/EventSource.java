package help.lixin.framework.module.event;

import java.io.Serializable;

public class EventSource implements Serializable {
    private String path;
    private String name;
    private String source;
    private Long time;

    public EventSource() {
    }

    public EventSource(String path,String name, String source, Long time) {
        this.path = path;
        this.name = name;
        this.source = source;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
