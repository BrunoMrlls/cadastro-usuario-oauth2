package br.com.bruno.ws.resources.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class StandartError implements Serializable {
    private static final long serialVersionUID = 1L;

    private long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandartError(long time, Integer httpStatus, String error, String message, String requestURI) {
        super();
        this.timestamp = time;
        this.status = httpStatus;
        this.error = error;
        this.message = message;
        this.path = requestURI;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
