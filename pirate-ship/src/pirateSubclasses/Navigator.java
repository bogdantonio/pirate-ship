package pirateSubclasses;
import pirate.Pirate;
import pirate.PirateStatSet;
import pirate.Role;
import pirate.InvalidDataException;

public class Navigator extends Pirate{
    private int navigatorId;
    private int navigation;
    private int weatherPrediction;
    private int mapReading;

    public Navigator(int pirateId, Role role, String alias, String name, String sex, PirateStatSet pirateStatSet,
                     int navigatorId, int navigation, int weatherPrediction, int mapReading) {
        super(pirateId, role, alias, name, sex, pirateStatSet);
        this.navigatorId = navigatorId;
        this.navigation = navigation;
        this.weatherPrediction = weatherPrediction;
        this.mapReading = mapReading;
    }

    public int getNavigatorId() {
        return navigatorId;
    }

    public void setNavigatorId(int navigatorId) {
        this.navigatorId = navigatorId;
    }

    public int getNavigation() {
        return navigation;
    }

    public void setNavigation(int navigation) {
        this.navigation = navigation;
    }

    public int getWeatherPrediction() {
        return weatherPrediction;
    }

    public void setWeatherPrediction(int weatherPrediction) {
        this.weatherPrediction = weatherPrediction;
    }

    public int getMapReading() {
        return mapReading;
    }

    public void setMapReading(int mapReading) {
        this.mapReading = mapReading;
    }

    @Override
    public void validateSubclassData() throws Exception {
        if(this.navigatorId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }
        super.validatePirateData();

        if(this.navigation < 10 || this.navigation > 100){
            throw new InvalidDataException("Invalid data for navigation: the value must be between 10 and 100!");
        }
        if(this.weatherPrediction< 10 || this.weatherPrediction > 100){
            throw new InvalidDataException("Invalid data for weatherPrediction: the value must be between 10 and 100!");
        }
        if(this.mapReading < 10 || this.mapReading > 100){
            throw new InvalidDataException("Invalid data for mapReading: the value must be between 10 and 100!");
        }
    }

}
