/*
 * The MIT License
 *
 * Copyright 2016 MAGarcia.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package schedulercs356.entity;

import java.time.LocalDateTime;

/**
 * He's a rootin' tootin' time parsin' local formatin' time parser.
 * @author MAGarcia
 */
public final class TimeParser {
  /**
   * Time at the end of 12.
   */
  public static final int TIME_HOUR = 12;
  public static final int TIME_MINUTE = 60;
  public static final int TIME_ONE_DIGIT = 10;
  
  /**
   * Parse the Date to a reasonable format. This is not a fully developed 
   * clock, so the format is based on your Operating System.
   * @param date 
   */
  public static String parseDateToDisplay(LocalDateTime date) {   
      String strParsed;
      int hour = date.getHour();
      String minuteString = "00";
      String secondString = "00";
      String timeDay = "AM";
      
      if (hour > TIME_HOUR) {
        hour = hour - TIME_HOUR;
        timeDay = "PM";
      } else if (hour <= 0) {
        hour = TIME_HOUR;
      }
      
      int minute = date.getMinute();
      if (minute < TIME_ONE_DIGIT) {
        minuteString = "0" + minute;
      } else {
        minuteString = String.valueOf(minute);
      }
      
      int second = date.getSecond();
      
      if (second < TIME_ONE_DIGIT) {
        secondString = "0" + second;
      } else {
        secondString = String.valueOf(second);
      }
      
      strParsed = date.getDayOfWeek() + " "
              + date.getMonth() + " " + date.getDayOfMonth() + ", "
              + date.getYear() + "\n " + hour + ":" + minuteString 
              + ":" + secondString + " " + timeDay;
      
      return strParsed;
  }
}
