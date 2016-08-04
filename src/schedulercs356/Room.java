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
    //private Schedule schedule;
    private String description;
    
    
    public Room(int maxOccupancy, String description){
        this.setMaxOccupancy(maxOccupancy);
        this.setDescription(description);
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
    
}
