package events;

import pirate.InvalidDataException;

import java.util.ArrayList;

public class EventSet {
    private int eventSetId;
    private ArrayList<Event> eventSet;

    public EventSet(int eventSetId, ArrayList<Event> eventSet) {
        this.eventSetId = eventSetId;
        this.eventSet = eventSet;
    }

    public int getEventSetId() {
        return eventSetId;
    }

    public void setEventSetId(int eventSetId) {
        this.eventSetId = eventSetId;
    }

    public ArrayList<Event> getEventSet() {
        return eventSet;
    }

    public void setEventSet(ArrayList<Event> eventSet) {
        this.eventSet = eventSet;
    }

    public void validateEventSetData() throws Exception {
        if(this.eventSetId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.eventSet == null){
            throw new InvalidDataException("Event set can't be null!");
        }
        if(this.eventSet.isEmpty()){
            throw new InvalidDataException("Event list cannot be empty!");
        }

        for(Event event: eventSet){
            if(event == null){
                throw new InvalidDataException("Event list contains a null event!");
            }
            event.validateEventData();
        }
    }
}
