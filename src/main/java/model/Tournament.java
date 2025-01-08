package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Tournament {
    private final ArrayList<Player> players;
    private ArrayList<Matchup> matchups;
    private final Random rand;
    private int teams;

    public Tournament() {
        players = new ArrayList<Player>();
        matchups = new ArrayList<Matchup>();
        rand = new Random();
    }

}