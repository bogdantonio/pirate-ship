package events;

import pirate.InvalidDataException;
import pirate.Role;

public class PirateSubclassEvent implements EventInterface {
    private int subclassEventId;
    private Role subclass;
    private String prompt;
    private int reqStat1;
    private int reqStat2;
    private int reqStat3;

    public PirateSubclassEvent(int subclassEventId, Role subclass, String prompt, int reqStat1, int reqStat2, int reqStat3) {
        this.subclassEventId = subclassEventId;
        this.subclass = subclass;
        this.prompt = prompt;
        this.reqStat1 = reqStat1;
        this.reqStat2 = reqStat2;
        this.reqStat3 = reqStat3;
    }

    public Role getSubclass() {
        return subclass;
    }

    public void setSubclass(Role subclass) {
        this.subclass = subclass;
    }

    public int getSubclassEventId() {
        return subclassEventId;
    }

    public void setSubclassEventId(int subclassEventId) {
        this.subclassEventId = subclassEventId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getReqStat1() {
        return reqStat1;
    }

    public void setReqStat1(int reqStat1) {
        this.reqStat1 = reqStat1;
    }

    public int getReqStat2() {
        return reqStat2;
    }

    public void setReqStat2(int reqStat2) {
        this.reqStat2 = reqStat2;
    }

    public int getReqStat3() {
        return reqStat3;
    }

    public void setReqStat3(int reqStat3) {
        this.reqStat3 = reqStat3;
    }

    @Override
    public void printPrompt() {
        System.out.println(this.prompt);
    }

    @Override
    public void validateEventData() throws Exception {
        if(this.subclassEventId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.prompt.length() < 32){
            throw new InvalidDataException("Invalid data for prompt: the length of the prompt must be at least 32 characters!");
        }

        if(this.reqStat1 < 10 || this.reqStat1 > 100){
            throw new InvalidDataException("Invalid data for reqStat1: the value must be between 10 and 100!");
        }
        if(this.reqStat2 < 10 || this.reqStat2 > 100){
            throw new InvalidDataException("Invalid data for reqStat2: the value must be between 10 and 100!");
        }
        if(this.reqStat3 < 10 || this.reqStat3 > 100){
            throw new InvalidDataException("Invalid data for reqStat3: the value must be between 10 and 100!");
        }
    }

}
