package models.TCP;

import enums.Requests;

public class Request {
    private Requests requestType;
    private String requestMessage;
    public Request(Requests requestType, String requestMessage) {
        this.requestType = requestType;
        this.requestMessage = requestMessage;
    }

    public Request(){

    }
    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public Requests getRequestType() {
        return requestType;
    }

    public void setRequestType(Requests requestType) {
        this.requestType = requestType;
    }
}