package models.TCP;

import enums.Responses;

public class Response {
    private Responses responseStatus;
    private String responseMessage;
    private String responseData;
    public Response(Responses responseStatus, String responseMessage,String responseData) {
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
        this.responseData = responseData;
    }
    public Response(){

    }
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Responses getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Responses responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
