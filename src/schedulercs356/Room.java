/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulercs356;

/**
 *
 * @author Michael Wallace
 * 
 * Needs:
 *      Schedule
 */
public class Room {
    private int maxOccupancy;
    private String description;
    private Schedule schedule;
    private Integer roomNumber;
    
    
    public Room(int maxOccupancy, String description, Schedule schedule, Integer roomNumber){
        this.setMaxOccupancy(maxOccupancy);
        this.setDescription(description);
        this.setSchedule(schedule);
        this.setRoomNumber(roomNumber);
    }
    
    public void setSchedule(Schedule schedule){
        this.schedule = schedule;
    }
    
    public void addSchedule(Schedule.miniSchedule schedule){
        schedule.setOwnerID(roomNumber);
        this.schedule.addNewSchedule(schedule);
    }
    
    public Schedule getSchedule(){
        return this.schedule;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
    
}
