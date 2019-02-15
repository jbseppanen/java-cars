package com.lambdaschool.javacars;

import lombok.Data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CarLog implements Serializable {

    private final String text;
    private final String formattedDate;
    public CarLog(String text) {
        this.text = text;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        formattedDate = dateFormat.format(date);
    }
}