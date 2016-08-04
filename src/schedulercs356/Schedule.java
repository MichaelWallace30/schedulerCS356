/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.ArrayList;
/**
 *
 * @author Michael Wallace
 */
public class Schedule {
      
    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private LinkedList<LocalDateTime> startDateTimeList;
    private LinkedList<LocalDateTime> endDateTimeList;
    private ArrayList<Integer> indexArray;
    
    public Schedule(){
        startDateTimeList = new LinkedList();
        endDateTimeList = new LinkedList();
        indexArray = new ArrayList();        
    }
    
    
    
    //add by dateTimeList.add(int index, E elment)
    //removeFirstOccurrence or remove(int index)
    
}
