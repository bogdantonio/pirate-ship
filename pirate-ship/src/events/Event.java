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

    public void printEvent(){
        if(eventType == EventType.ENEMY){
            enemyEvent.printPrompt();
            System.out.println("Enemy: " + enemyEvent.getEnemy().getName() + " | Faction: " +
                    enemyEvent.getEnemy().getFaction() + " | Power: " + enemyEvent.getEnemy().getPower());
        }
        else if (eventType == EventType.SUBCLASS){
            pirateSubclassEvent.printPrompt();
            System.out.println("Subclass: " + pirateSubclassEvent.getSubclass() + " | Required Stat 1: " + pirateSubclassEvent.getReqStat1()
                    + " | Required Stat 2: " + pirateSubclassEvent.getReqStat2()
                    + " | Required Stat 3: " + pirateSubclassEvent.getReqStat3());
        }
    }

    public void validateEventData() throws Exception {
        if(this.eventId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.enemyEvent != null){
            this.enemyEvent.validateEventData();
        }
        if(this.pirateSubclassEvent != null){
            this.pirateSubclassEvent.validateEventData();
        }
        if(this.enemyEvent == null && pirateSubclassEvent == null){
            throw new InvalidDataException("Invalid data: enemyEvent and pirateSubclassEvent can't be both null!");
        }
    }
}
