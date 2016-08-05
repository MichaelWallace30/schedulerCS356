/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 *
 * @author Michael Wallace
 */
public class Schedule {
    
    public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    Integer ownerID;
    
    
    public class miniSchedule{
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private Integer ownerID;
 

        public miniSchedule(LocalDateTime start, LocalDateTime end, Integer ownerID){
            this.startDateTime = start;
            this.endDateTime = end;
            this.ownerID = ownerID;
        }
        
        public miniSchedule(LocalDateTime start, LocalDateTime end){
            this.startDateTime = start;
            this.endDateTime = end;
            this.ownerID = 0;
        }

        public LocalDateTime getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public LocalDateTime getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Integer getOwnerID() {
            return ownerID;
        }

        public void setOwnerID(Integer ownerID) {
            this.ownerID = ownerID;
        }
    }
    private LinkedList<miniSchedule> scheduleList;

    public Schedule(Integer ownerID){
        this.ownerID = ownerID;        
    }
    
    public void addNewSchedule(LocalDateTime startTime, LocalDateTime endTime){
        
        scheduleList.add(new miniSchedule(startTime,endTime,this.ownerID));
    }
    
    public void addNewSchedule(miniSchedule mini){
        scheduleList.add(mini);
    }
    
    public LinkedList<miniSchedule> getAvailability(){
        return scheduleList;
    }
    
         
}
