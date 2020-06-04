package nl.tudelft.context.cg2.client.model.datastructures;

import java.util.ArrayList;

public class PlayerFactory {
    ArrayList<Player> players = new ArrayList<>();

    public Player createPlayer(String name) {
        Player found = null;

        for (Player player: players) {
            if (player.getName().equals(name)) {
                found = player;
            }
        }

        if (found != null) {
            return found;
        }
        found = new Player(name);
        players.add(found);
        return found;
    }
}
