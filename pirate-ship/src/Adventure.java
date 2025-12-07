import crew.Crew;
import events.Event;
import events.EventSet;
import events.EventType;
import events.PirateSubclassEvent;

import java.util.ArrayList;

import java.util.Scanner;

public class Adventure {
    private int adventureId;
    private EventSet eventSet;
    private Crew crew;
    private int successfulEvents;
    private int failedEvents;

    public Adventure(int adventureId, EventSet eventSet, Crew crew) {
        this.adventureId = adventureId;
        this.eventSet = eventSet;
        this.crew = crew;
    }

    public int getAdventureId() {
        return adventureId;
    }

    public void setAdventureId(int adventureId) {
        this.adventureId = adventureId;
    }

    public EventSet getEventSet() {
        return eventSet;
    }

    public void setEventSet(EventSet eventSet) {
        this.eventSet = eventSet;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public int getSuccessfulEvents() {
        return successfulEvents;
    }

    private void setSuccessfulEvents(int successfulEvents) {
        this.successfulEvents = successfulEvents;
    }

    public int getFailedEvents() {
        return failedEvents;
    }

    private void setFailedEvents(int failedEvents) {
        this.failedEvents = failedEvents;
    }

    private boolean eventPassed(int reqStat1, int reqStat2, int reqStat3, int stat1, int stat2, int stat3){
        if((reqStat1 < stat1 && reqStat2 < stat2)
                || (reqStat1 < stat1 && reqStat3 < stat3)
                || (reqStat3 < stat3 && reqStat2 < stat2))
        {
            System.out.println("Event passed successfully!");
            return true;
        }
        System.out.println("Event failed!");
        return false;
    }

    private boolean runEvent(Event event) throws Exception{
        if(event.getEventType() == EventType.ENEMY){
            event.getEnemyEvent().printPrompt();
            if(crew.getCrewPower() >= event.getEnemyEvent().getEnemy().getPower()){
                System.out.println("Enemy defeated!");
                return true;
            }
            else{
                System.out.println("Battle lost!");
                return false;
            }
        }

        if (event.getEventType() == EventType.SUBCLASS){
            event.getPirateSubclassEvent().printPrompt();

            PirateSubclassEvent pirateSubclassEvent = event.getPirateSubclassEvent();
            int reqStat1 = pirateSubclassEvent.getReqStat1();
            int reqStat2 = pirateSubclassEvent.getReqStat2();
            int reqStat3 = pirateSubclassEvent.getReqStat3();

            return switch (pirateSubclassEvent.getSubclass()) {
                case SECOND -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getSecond().getLeadership(), crew.getSecond().getTactics(), crew.getSecond().getMoraleBoost());
                case NAVIGATOR -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getNavigator().getMapReading(), crew.getNavigator().getNavigation(), crew.getNavigator().getWeatherPrediction());
                case DOCTOR -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getDoctor().getDiagnosis(), crew.getDoctor().getHealingSpeed(), crew.getDoctor().getMedicalAbility());
                case SNIPER -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getSniper().getAccuracy(), crew.getSniper().getCriticalChance(), crew.getSniper().getWeaponRange());
                case COOK -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getCook().getCooking(), crew.getCook().getMealQuality(), crew.getCook().getMoraleImpact());
                case SHIPWRIGHT -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getShipwright().getConstruction(), crew.getShipwright().getMaterials(), crew.getShipwright().getRepair());
                case MUSICIAN -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getMusician().getInspiration(), crew.getMusician().getBuffStrength(), crew.getMusician().getMusic());
                case HELMSMAN -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getHelmsman().getManeuvering(), crew.getHelmsman().getPrecision(), crew.getHelmsman().getStormRiding());
                case ARCHEOLOGIST -> eventPassed(reqStat1, reqStat2, reqStat3,
                        crew.getArcheologist().getDigging(), crew.getArcheologist().getArtifactKnowledge(), crew.getArcheologist().getTrapDetection());
            };
        }

        // this exception is meant as a replacement for the mandatory return in this method
        // I don't know under what conditions it is reachable since it should be impossible,
        // but might as well throw an exception to mark the fail of the method
        throw new Exception("runEvent fail!");
    }

    public void runAdventure() throws Exception {
        Scanner input = new Scanner(System.in);

        ArrayList<Event> eventsSet = eventSet.getEventSet();
        int successfulE = 0;
        int failedE = 0;

        for(Event event : eventsSet){
            String cmd = "";
            System.out.println("Commands: ");
            while(true){
                System.out.println("p: proceed to the next event!");
                System.out.println("q: abort the adventure!");
                cmd = input.next();

                if(cmd.equals("p") || cmd.equals("q")) break;

                System.out.println("Invalid command! Type 'p' or 'q'.");
            }

            if(cmd.equals("q")){
                System.out.println("\nAdventure aborted!");
                System.out.println("Successful events: " + successfulE);
                System.out.println("Failed events: " + failedE);
                return;
            }

            if(runEvent(event)){
                successfulE++;
            }
            else{
                failedE++;
                if(failedE >= 3){
                    System.out.println("\nLost at sea! Too many events failed!");
                    System.out.println("Successful events: " + successfulE);
                    System.out.println("Failed events: " + failedE);
                    return;
                }

            }
        }

        System.out.println("Adventure complete! You found the treasure");
        System.out.println("Successful events: " + successfulE);
        System.out.println("Failed events: " + failedE);
    }

}
