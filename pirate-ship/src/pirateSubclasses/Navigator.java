package pirateSubclasses;
import pirate.Pirate;
import pirate.Role;
import pirate.InvalidDataException;

public class Navigator extends Pirate{
    public int navigatorId;
    public int navigation;
    public int weatherPrediction;
    public int mapReading;

    public Navigator(int pirateId, String name, String alias, Role role,
                  int strength, int agility, int endurance,int intelligence, int charisma, int willpower,
                  int navigatorId, int navigation, int weatherPrediction, int mapReading){

        super(pirateId, name, alias, role, strength, agility, endurance, intelligence, charisma, willpower);
        this.navigatorId = navigatorId;
        this.navigation = navigation;
        this.weatherPrediction = weatherPrediction;
        this.mapReading = mapReading;
    }

    public int getNavigatorId(){
        return navigatorId;
    }

    public void setNavigatorId(int navigatorId){
        this.navigatorId = navigatorId;
    }

    public int getNavigation(){
        return navigation;
    }

    public void setNavigation(int navigation){
        this.navigation = navigation;
    }

    public int getWeatherPrediction(){
        return weatherPrediction;
    }

    public void setWeatherPrediction(int weatherPrediction){
        this.weatherPrediction = weatherPrediction;
    }

    public int getMapReading(){
        return mapReading;
    }

    public void setMapReading(int mapReading){
        this.mapReading = mapReading;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(navigatorId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(navigation < 10 || navigation > 100){
            throw new InvalidDataException("Invalid data for navigation: the value must be between 10 and 100!");
        }
        if(weatherPrediction< 10 || weatherPrediction > 100){
            throw new InvalidDataException("Invalid data for weatherPrediction: the value must be between 10 and 100!");
        }
        if(mapReading < 10 || mapReading > 100){
            throw new InvalidDataException("Invalid data for mapReading: the value must be between 10 and 100!");
        }
    }

}
