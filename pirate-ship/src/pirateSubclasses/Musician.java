package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Musician extends Pirate{
    private int musicianId;
    private int music;
    private int inspiration;
    private int buffStrength;

    public Musician(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                    int musicianId, int music, int inspiration, int buffStrength) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.musicianId = musicianId;
        this.music = music;
        this.inspiration = inspiration;
        this.buffStrength = buffStrength;
    }

    public int getMusicianId() {
        return musicianId;
    }

    public void setMusicianId(int musicianId) {
        this.musicianId = musicianId;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getInspiration() {
        return inspiration;
    }

    public void setInspiration(int inspiration) {
        this.inspiration = inspiration;
    }

    public int getBuffStrength() {
        return buffStrength;
    }

    public void setBuffStrength(int buffStrength) {
        this.buffStrength = buffStrength;
    }

    @Override
    public void printSubclassStats() {
        super.printPirateStats();
        System.out.println("Music: " + music);
        System.out.println("Inspiration: " + inspiration);
        System.out.println("Buff Strength: " + buffStrength);
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.musicianId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        super.validatePirateData();

        if(this.music < 10 || this.music > 100){
            throw new InvalidDataException("Invalid data for music: the value must be between 10 and 100!");
        }
        if(this.inspiration< 10 || this.inspiration > 100){
            throw new InvalidDataException("Invalid data for inspiration: the value must be between 10 and 100!");
        }
        if(this.buffStrength < 10 || this.buffStrength > 100){
            throw new InvalidDataException("Invalid data for buffStrength: the value must be between 10 and 100!");
        }
    }
}
