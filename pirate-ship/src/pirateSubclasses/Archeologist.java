package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Archeologist extends Pirate{
    private int archeologistId;
    private int artifactKnowledge;
    private int digging;
    private int trapDetection;

    public Archeologist(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                        int archeologistId, int trapDetection, int digging, int artifactKnowledge) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.archeologistId = archeologistId;
        this.trapDetection = trapDetection;
        this.digging = digging;
        this.artifactKnowledge = artifactKnowledge;
    }

    public int getArcheologistId() {
        return archeologistId;
    }

    public void setArcheologistId(int archeologistId) {
        this.archeologistId = archeologistId;
    }

    public int getArtifactKnowledge() {
        return artifactKnowledge;
    }

    public void setArtifactKnowledge(int artifactKnowledge) {
        this.artifactKnowledge = artifactKnowledge;
    }

    public int getDigging() {
        return digging;
    }

    public void setDigging(int digging) {
        this.digging = digging;
    }

    public int getTrapDetection() {
        return trapDetection;
    }

    public void setTrapDetection(int trapDetection) {
        this.trapDetection = trapDetection;
    }

    @Override
    public void printSubclassStats() {
        super.printPirateStats();
        System.out.println("Artifact Knowledge: " + artifactKnowledge);
        System.out.println("Digging: " + digging);
        System.out.println("Trap Detection: " + trapDetection);
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.archeologistId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        super.validatePirateData();

        if(this.artifactKnowledge < 10 || this.artifactKnowledge > 100){
            throw new InvalidDataException("Invalid data for artifactKnowledge: the value must be between 10 and 100!");
        }
        if(this.digging< 10 || this.digging > 100){
            throw new InvalidDataException("Invalid data for digging: the value must be between 10 and 100!");
        }
        if(this.trapDetection < 10 || this.trapDetection > 100){
            throw new InvalidDataException("Invalid data for trapDetection: the value must be between 10 and 100!");
        }
    }
}
