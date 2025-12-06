package events;

import pirate.InvalidDataException;

public class Event {
    private int eventId;
    private EventType eventType;
    private EnemyEvent enemyEvent;
    private PirateSubclassEvent pirateSubclassEvent;

    public Event(int eventId, EventType eventType, EnemyEvent enemyEvent, PirateSubclassEvent pirateSubclassEvent) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.enemyEvent = enemyEvent;
        this.pirateSubclassEvent = pirateSubclassEvent;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EnemyEvent getEnemyEvent() {
        return enemyEvent;
    }

    public void setEnemyEvent(EnemyEvent enemyEvent) {
        this.enemyEvent = enemyEvent;
    }

    public PirateSubclassEvent getPirateSubclassEvent() {
        return pirateSubclassEvent;
    }

    public void setPirateSubclassEvent(PirateSubclassEvent pirateSubclassEvent) {
        this.pirateSubclassEvent = pirateSubclassEvent;
    }

    public void validateEventData() throws Exception {
        if(this.eventId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        this.enemyEvent.validateEventData();
        this.pirateSubclassEvent.validateEventData();
    }
}
