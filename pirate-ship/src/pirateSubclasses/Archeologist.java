package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Archeologist extends Pirate{
    public int archeologistId;
    public int artifactKnowledge;
    public int digging;
    public int trapDetection;

    public Archeologist(int pirateId, String name, String alias, Role role,
                  int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                  int archeologistId, int artifactKnowledge, int digging, int trapDetection){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.archeologistId = archeologistId;
        this.artifactKnowledge = artifactKnowledge;
        this.digging = digging;
        this.trapDetection = trapDetection;
    }

    public int getArcheologistId(){
        return archeologistId;
    }

    public void setArcheologistId(int archeologistId){
        this.archeologistId = archeologistId;
    }

    public int getArtifactKnowledge(){
        return artifactKnowledge;
    }

    public void setArtifactKnowledge(int artifactKnowledge){
        this.artifactKnowledge = artifactKnowledge;
    }

    public int getDigging(){
        return digging;
    }

    public void setDigging(int digging){
        this.digging = digging;
    }

    public int getTrapDetection(){
        return trapDetection;
    }

    public void setTrapDetection(int trapDetection){
        this.trapDetection = trapDetection;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(archeologistId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(artifactKnowledge < 10 || artifactKnowledge > 100){
            throw new InvalidDataException("Invalid data for artifactKnowledge: the value must be between 10 and 100!");
        }
        if(digging< 10 || digging > 100){
            throw new InvalidDataException("Invalid data for digging: the value must be between 10 and 100!");
        }
        if(trapDetection < 10 || trapDetection > 100){
            throw new InvalidDataException("Invalid data for trapDetection: the value must be between 10 and 100!");
        }
    }
}
