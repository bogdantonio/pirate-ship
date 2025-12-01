package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Doctor extends Pirate{
    public int doctorId;
    public int medicalAbility;
    public int healingSpeed;
    public int diagnosis;

    public Doctor(int pirateId, String name, String alias, Role role,
                int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                int doctorId, int medicalAbility, int healingSpeed, int diagnosis){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.doctorId = doctorId;
        this.medicalAbility = medicalAbility;
        this.healingSpeed = healingSpeed;
        this.diagnosis = diagnosis;
    }

    public int getDoctorId(){
        return doctorId;
    }

    public void setDoctorId(int doctorId){
        this.doctorId = doctorId;
    }

    public int getMedicalAbility(){
        return medicalAbility;
    }

    public void setMedicalAbility(int medicalAbility){
        this.medicalAbility = medicalAbility;
    }

    public int getHealingSpeed(){
        return healingSpeed;
    }

    public void setHealingSpeed(int healingSpeed){
        this.healingSpeed = healingSpeed;
    }

    public int getDiagnosis(){
        return diagnosis;
    }

    public void setDiagnosis(int diagnosis){
        this.diagnosis = diagnosis;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(doctorId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(medicalAbility < 10 || medicalAbility > 100){
            throw new InvalidDataException("Invalid data for medicalAbility: the value must be between 10 and 100!");
        }
        if(healingSpeed< 10 || healingSpeed > 100){
            throw new InvalidDataException("Invalid data for healingSpeed: the value must be between 10 and 100!");
        }
        if(diagnosis < 10 || diagnosis > 100){
            throw new InvalidDataException("Invalid data for diagnosis: the value must be between 10 and 100!");
        }
    }
}
