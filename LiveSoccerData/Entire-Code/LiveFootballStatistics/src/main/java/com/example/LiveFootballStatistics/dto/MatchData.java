package com.example.LiveFootballStatistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MatchData {
  private int success;
  private List<MatchResult> result;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  
  /**
   * Represents the result of a match.
   */
  public static class MatchResult {
    private String eventKey;
    private String eventDate;
    private String eventTime;
    private String eventHomeTeam;
    private String homeTeamKey;
    private String eventAwayTeam;
    private String awayTeamKey;
    private String eventHalftimeResult;
    private String eventFinalResult;
    private String eventFtResult;
    private String eventPenaltyResult;
    private String eventStatus;
    private String countryName;
    private String leagueName;
    private String leagueKey;
    private String leagueRound;
    private String leagueSeason;
    private String eventLive;
    private String eventStadium;
    private String eventReferee;
    private String eventCountryKey;
    private String leagueLogo;
    private String countryLogo;
    private String eventHomeFormation;
    private String eventAwayFormation;
    private String fkStageKey;
    private String stageName;

    @JsonProperty("goalscorers")
    private List<GoalScorer> goalScorers;
    private List<Card> cards;
    private List<Substitute> substitutes;
    private Lineups lineups;
    private List<Statistic> statistics;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * Represents a goal scorer in a soccer match.
   */
  public static class GoalScorer {
    private String time;
    private String homeScorer;
    private String score;
    private String awayScorer;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * Represents a card object.
   */
  public static class Card {
    private String time;
    private String homeFault;
    private String card;
    private String awayFault;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * A class representing a substitute player.
   */
  public static class Substitute {
    private String time;
    private Substitution homeScorer;
    private String score;
    private List<Substitution> awayScorer;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * Represents a substitution in a text.
   */
  public static class Substitution {
    private String in;
    private String out;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * A static nested class representing lineups.
   * This class can be used to store and manipulate lineups data.
   */
  public static class Lineups {
    private HomeTeam homeTeam;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * Represents the home team in a sports match.
   */
  public static class HomeTeam {
    private List<LineupPlayer> startingLineups;
    private List<LineupPlayer> substitutes;
    private List<LineupPlayer> coaches;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * Represents a player in a lineup.
   */
  public static class LineupPlayer {
    private String player;
    private String playerNumber;
    private String playerCountry;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder(toBuilder = true)
  @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
  /**
   * Represents a statistic.
   */
  public static class Statistic {
    private String type;
    private String home;
    private String away;
  }
}
