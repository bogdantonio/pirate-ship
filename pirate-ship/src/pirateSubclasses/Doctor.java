package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Doctor extends Pirate{
    private int doctorId;
    private int medicalAbility;
    private int healingSpeed;
    private int diagnosis;

    public Doctor(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                  int doctorId, int medicalAbility, int healingSpeed, int diagnosis) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.doctorId = doctorId;
        this.medicalAbility = medicalAbility;
        this.healingSpeed = healingSpeed;
        this.diagnosis = diagnosis;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getMedicalAbility() {
        return medicalAbility;
    }

    public void setMedicalAbility(int medicalAbility) {
        this.medicalAbility = medicalAbility;
    }

    public int getHealingSpeed() {
        return healingSpeed;
    }

    public void setHealingSpeed(int healingSpeed) {
        this.healingSpeed = healingSpeed;
    }

    public int getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(int diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.doctorId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.medicalAbility < 10 || this.medicalAbility > 100){
            throw new InvalidDataException("Invalid data for medicalAbility: the value must be between 10 and 100!");
        }
        if(this.healingSpeed< 10 || this.healingSpeed > 100){
            throw new InvalidDataException("Invalid data for healingSpeed: the value must be between 10 and 100!");
        }
        if(this.diagnosis < 10 || this.diagnosis > 100){
            throw new InvalidDataException("Invalid data for diagnosis: the value must be between 10 and 100!");
        }
    }
}
