package events;

import enemy.Enemy;
import pirate.InvalidDataException;

public class EnemyEvent implements EventInterface {
    private int enemyEventId;
    private String prompt;
    private Enemy enemy;

    public EnemyEvent(int enemyEventId, String prompt, Enemy enemy) {
        this.enemyEventId = enemyEventId;
        this.prompt = prompt;
        this.enemy = enemy;
    }

    public int getEnemyEventId() {
        return enemyEventId;
    }

    public void setEnemyEventId(int enemyEventId) {
        this.enemyEventId = enemyEventId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void printPrompt() {
        System.out.println(this.prompt);
    }

    @Override
    public void validateEventData() throws Exception {
        if(this.enemyEventId < 0){
            throw new InvalidDataException("Invalid data for id: null values not supported!");
        }

        if(this.prompt.length() < 32){
            throw new InvalidDataException("Invalid data for prompt: the length of the prompt must be at least 32 characters!");
        }
        this.enemy.validateEnemyData();
    }

}
