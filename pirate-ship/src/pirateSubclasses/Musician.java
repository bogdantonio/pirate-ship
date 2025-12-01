package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Musician extends Pirate{
    public int musicianId;
    public int music;
    public int inspiration;
    public int buffStrength;

    public Musician(int pirateId, String name, String alias, Role role,
                  int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                  int musicianId, int music, int inspiration, int buffStrength){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.musicianId = musicianId;
        this.music = music;
        this.inspiration = inspiration;
        this.buffStrength = buffStrength;
    }

    public int getMusicianId(){
        return musicianId;
    }

    public void setMusicianId(int musicianId){
        this.musicianId = musicianId;
    }

    public int getMusic(){
        return music;
    }

    public void setMusic(int music){
        this.music = music;
    }

    public int getInspiration(){
        return inspiration;
    }

    public void setInspiration(int inspiration){
        this.inspiration = inspiration;
    }

    public int getBuffStrength(){
        return buffStrength;
    }

    public void setBuffStrength(int buffStrength){
        this.buffStrength = buffStrength;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(musicianId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(music < 10 || music > 100){
            throw new InvalidDataException("Invalid data for music: the value must be between 10 and 100!");
        }
        if(inspiration< 10 || inspiration > 100){
            throw new InvalidDataException("Invalid data for inspiration: the value must be between 10 and 100!");
        }
        if(buffStrength < 10 || buffStrength > 100){
            throw new InvalidDataException("Invalid data for buffStrength: the value must be between 10 and 100!");
        }
    }
}
