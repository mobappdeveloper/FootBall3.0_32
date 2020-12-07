package com.footballio.model.dashboard;

import java.util.List;

public class MyResponse {
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return msg;
    }



    public String getMsg() {
        return msg;
    }

    public String getFilelocation() {
        return filelocation;
    }

    public String getSize() {
        return size;
    }

    public List<MyResponse> getData() {
        return data;
    }



    private String status;
    private List<MyResponse> data;
    private String filelocation;
    private String size;
    private String msg;
}
