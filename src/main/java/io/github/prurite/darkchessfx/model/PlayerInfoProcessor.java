package io.github.prurite.darkchessfx.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;

public class PlayerInfoProcessor implements  PlayerInfoProcessorInterface {
    ArrayList<Player> players;
    Path path;
    public PlayerInfoProcessor(Path path) {
        players = new ArrayList<>();
        this.path = path;
        // read players information from path into players
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
            String line;
            while((line = reader.readLine()) != null) {
                String[] info = line.split(" ");
                Player player = new Player(info[0]);
                player.setGameCount(Integer.parseInt(info[1]));
                player.setScoredGameCount(Integer.parseInt(info[2]));
                player.setWinnedGameCount(Integer.parseInt(info[3]));
                player.setTotalGameTime(Double.parseDouble(info[4]));
                players.add(player);
            }
        } catch (Exception e) {}
    }
    public Player getPlayer(String name) {
        for(Player p : players) {
            if(p.getName().equals(name)) {
                return p;
            }
        }
        Player p = new Player(name);
        players.add(p);
        return p;
    }
    public void updateData() {
        // write players information from players into path
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
            for(Player p : players) {
                writer.write(p.getName() + " " + p.getGameCount() + " " + p.getScoredGameCount() + " " + p.getWinnedGameCount() + " " + p.getTotalGameTime() + "\n");
                writer.newLine();
            }
            writer.close();

        } catch(Exception e) {}
    }
}