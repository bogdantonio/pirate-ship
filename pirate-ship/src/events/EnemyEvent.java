package events;

import enemy.Enemy;
import crew.Crew;

public class EnemyEvent {
    public String prompt;
    private Enemy enemy;

    public EnemyEvent(Enemy enemy, Crew crew, String prompt) {
        this.enemy = enemy;
        this.prompt = prompt;
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

}
