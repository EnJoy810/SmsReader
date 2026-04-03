package com.example.smsreader;

/**
 * 短信数据封装类
 */
public class SmsInfo {
    private String address;  // 发送号码
    private String date;     // 发送时间
    private String body;     // 短信内容

    public SmsInfo(String address, String date, String body) {
        this.address = address;
        this.date = date;
        this.body = body;
    }

    public String getAddress() { return address; }
    public String getDate()    { return date; }
    public String getBody()    { return body; }
}
