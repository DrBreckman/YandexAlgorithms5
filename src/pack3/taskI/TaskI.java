package pack3.taskI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskI {
    public static void main(String[] args){
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String tmpStr;
            while((tmpStr = reader.readLine()) != null)
                if (tmpStr.matches(gameRegex))
                    GamesData.registerMatch(reader, tmpStr);
                else if (tmpStr.matches(totalGoalsForTeamRegex)){
                    Matcher matcher = Pattern.compile(totalGoalsForTeamRegex).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getTotalGoalForTeam(matcher.group(1)));
                } else if (tmpStr.matches(meanGoalsForTeamRegex)) {
                    Matcher matcher = Pattern.compile(meanGoalsForTeamRegex).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getMeanGoalsForTeam(matcher.group(1)));
                } else if (tmpStr.matches(totalGoalsForPlayerRegex)) {
                    Matcher matcher = Pattern.compile(totalGoalsForPlayerRegex).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getTotalGoalsForPlayer(matcher.group(1)));
                } else if (tmpStr.matches(meanGoalsForPlayerRegex)) {
                    Matcher matcher = Pattern.compile(meanGoalsForPlayerRegex).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getMeanGoalsForPlayer(matcher.group(1)));
                } else if (tmpStr.matches(scoreOpenRegex)) {
                    Matcher matcher = Pattern.compile(scoreOpenRegex).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getScoreOpen(matcher.group(1)));
                } else if (tmpStr.matches(goalsOnMinuteRegex)) {
                    Matcher matcher = Pattern.compile(goalsOnMinuteRegex).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getGoalsOnMinute(matcher.group(2), Integer.parseInt(matcher.group(1))));
                } else if (tmpStr.matches(goalsOnFirstMinute)) {
                    Matcher matcher = Pattern.compile(goalsOnFirstMinute).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getGoalsOnFirstMinute(Integer.parseInt(matcher.group(1)), matcher.group(2)));
                } else if (tmpStr.matches(goalsOnLastMinute)) {
                    Matcher matcher = Pattern.compile(goalsOnLastMinute).matcher(tmpStr);
                    if (matcher.find())
                        System.out.println(GamesData.getGoalsOnLastMinute(Integer.parseInt(matcher.group(1)), matcher.group(2)));
                }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static class GamesData {
        public static final Map<String, Team> teams = new HashMap<>();
        public static final Map<String, Player> players = new HashMap<>();
        private static Player lastOpenScorePlayer = null;
        private static int lastOpenScoreMinute = Integer.MAX_VALUE;

        public static int getTotalGoalForTeam(String teamName){
            return teams.containsKey(teamName) ? teams.get(teamName).getTotalGoals() : 0;
        }
        public static double getMeanGoalsForTeam(String teamName){
            return teams.containsKey(teamName) ? teams.get(teamName).getMeanGoals() : 0;
        }
        public static int getTotalGoalsForPlayer(String playerName){
            return players.containsKey(playerName) ? players.get(playerName).getTotalGoals() : 0;
        }
        public static double getMeanGoalsForPlayer(String playerName){
            return players.containsKey(playerName) ? players.get(playerName).getMeanGoals() : 0;
        }
        public static int getScoreOpen(String name){
            return teams.containsKey(name) ? teams.get(name).getScoreOpen() : players.containsKey(name) ? players.get(name).getScoreOpen() : 0;
        }
        public static int getGoalsOnMinute(String name, int minute){
            return players.containsKey(name) ? players.get(name).getGoalsOnMinute(minute) : 0;
        }
        public static int getGoalsOnFirstMinute(int minute, String name){
            return players.containsKey(name) ? players.get(name).getGoalsFirstMinute(minute) : 0;
        }
        public static int getGoalsOnLastMinute(int minute, String name){
            return players.containsKey(name) ? players.get(name).getGoalsLastMinute(minute) : 0;
        }
        public static void registerMatch(BufferedReader reader, String matchInfo) throws IOException {
            lastOpenScorePlayer = null;
            lastOpenScoreMinute = Integer.MAX_VALUE;
            Matcher matcher = Pattern.compile(gameRegex).matcher(matchInfo);
            if (matcher.find()) {
                registerTeam(reader, matcher.group(1), Integer.parseInt(matcher.group(3)));
                registerTeam(reader, matcher.group(2), Integer.parseInt(matcher.group(4)));
            }
            if (lastOpenScorePlayer != null)
                lastOpenScorePlayer.openScore++;
        }

        private static void registerTeam(BufferedReader reader, String name, int goals) throws IOException {
            teams.putIfAbsent(name, new Team());
            Team team = teams.get(name);
            team.gamesPlayed++;
            for(int i = 0; i < goals; i++){
                Matcher goalMatcher = Pattern.compile(goalRegex).matcher(reader.readLine());
                if (goalMatcher.find())
                    registerGoal(team, goalMatcher.group(1), Integer.parseInt(goalMatcher.group(2)));
            }
        }

        private static void registerGoal(Team team, String name, int minute){
            players.putIfAbsent(name, new Player(name, team));
            Player p = players.get(name);
            p.goals.putIfAbsent(minute, 0);
            p.goals.merge(minute, 1, Integer::sum);
            if (minute < lastOpenScoreMinute){
                lastOpenScoreMinute = minute;
                lastOpenScorePlayer = p;
            }
        }

    }

    public static class Team {
        private final Map<String, Player> teamPlayers = new HashMap<>();
        public int gamesPlayed = 0;

        public int getTotalGoals(){
            return teamPlayers.values().stream()
                    .map(Player::getTotalGoals)
                    .reduce(Integer::sum)
                    .orElse(0);
        }
        public double getMeanGoals(){
            return getTotalGoals() / (double) gamesPlayed;
        }
        public int getScoreOpen(){
            return teamPlayers.values().stream()
                    .map(Player::getScoreOpen)
                    .reduce(Integer::sum)
                    .orElse(0);
        }
    }

    public static class Player {
        public final Team team;
        public final Map<Integer, Integer> goals = new HashMap<>();
        public int openScore = 0;
        public Player(String name, Team team){
            this.team = team;
            team.teamPlayers.putIfAbsent(name, this);
        }
        public int getTotalGoals(){
            return goals.values().stream()
                    .reduce(Integer::sum)
                    .orElse(0);
        }
        public double getMeanGoals(){
            return getTotalGoals() / (double) team.gamesPlayed;
        }
        public int getScoreOpen(){
            return openScore;
        }
        public int getGoalsOnMinute(int minute){
            return goals.getOrDefault(minute, 0);
        }
        public int getGoalsFirstMinute(int minute){
            return goals.entrySet().stream()
                    .filter(x -> x.getKey() <= minute)
                    .map(Map.Entry::getValue)
                    .reduce(Integer::sum)
                    .orElse(0);
        }
        public int getGoalsLastMinute(int minute){
            return goals.entrySet().stream()
                    .filter(x -> x.getKey() >= 91 - minute)
                    .map(Map.Entry::getValue)
                    .reduce(Integer::sum)
                    .orElse(0);
        }

    }

    public static String input = "input.txt";
    public static String gameRegex = "(.+) - (.+) (\\d+):(\\d+)";
    public static String goalRegex = "(.+) (\\d+)'";
    public static String totalGoalsForTeamRegex = "Total goals for (.+)";
    public static String meanGoalsForTeamRegex = "Mean goals per game for (.+)";
    public static String totalGoalsForPlayerRegex = "Total goals by (.+)";
    public static String meanGoalsForPlayerRegex = "Mean goals per game by (.+)";
    public static String scoreOpenRegex = "Score opens by (.+)";
    public static String goalsOnMinuteRegex = "Goals on minute (\\d+) by (.+)";
    public static String goalsOnFirstMinute = "Goals on first (\\d+) minutes by (.+)";
    public static String goalsOnLastMinute = "Goals on last (\\d+) minutes by (.+)";
}
