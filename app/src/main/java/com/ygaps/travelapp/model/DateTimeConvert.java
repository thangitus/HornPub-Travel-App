package com.ygaps.travelapp.model;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeConvert {
   public static String MillisecondToDate(String milisecond) {
      @SuppressLint("SimpleDateFormat")
      DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      long milliSeconds = Long.parseLong(milisecond);
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(milliSeconds);
      return formatter.format(calendar.getTime());
   }
   public static String DateToMillisecond(String date) throws ParseException {
      @SuppressLint("SimpleDateFormat")
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date myDate = sdf.parse(date);
      long millis = myDate.getTime();
      return String.valueOf(millis);
   }
}
