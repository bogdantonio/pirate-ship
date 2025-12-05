package events;

import pirate.Pirate;

public abstract class PirateSubclassEvent {
    public String prompt;
    public Pirate crewMember;

    public PirateSubclassEvent(String prompt, Pirate crewMember) {
        this.prompt = prompt;
        this.crewMember = crewMember;
    }

    public abstract boolean getOutcome();
    public abstract void printPrompt();

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Pirate getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(Pirate crewMember) {
        this.crewMember = crewMember;
    }
}
