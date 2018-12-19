package com.nutritionalStylist.healthyKitch.model;

import java.util.HashMap;
import java.util.List;

public class Mail {
    protected String[] toArray;
    protected String[] bccArray;
    protected String subject;
    protected String from;

    public Mail(){ }

    public Mail(String[] toArray, String[] bccArray, String from, String subject){
        this.toArray = toArray;
        this.bccArray = bccArray;
        this.subject = subject;
        this.from = from;
    }



    public String[] getToArray() { return toArray; }

    public void setToArray(String[] toArray) { this.toArray = toArray; }

    public String[] getBccArray() { return bccArray; }

    public void setBccArray(String[] bccArray) { this.bccArray = bccArray; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getFrom() { return from; }

    public void setFrom(String fromArray) { this.from = fromArray; }

    public HashMap<String, String> getModel(){
        HashMap<String, String> model = new HashMap();
        model.put("name", "Memorynotfound.com");
        model.put("location", "Belgium");
        model.put("signature", "https://memorynotfound.com");

        return model;
    }

}
