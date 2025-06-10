package com.umbrella.insurance.core.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "football_player_stats", schema = "public")
public class FootballPlayerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "football_player_stats_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name = "total_tackles")
    private Long totalTackles;

    @Column(name = "solo_tackles")
    private Long soloTackles;

    @Column(name = "total_sacks")
    private Long totalSacks;

    @Column(name = "tackles_for_loss")
    private Long tacklesForLoss;

    @Column(name = "passes_defended")
    private Long passesDefended;

    @Column(name = "quarterback_hits")
    private Long quarterbackHits;

    @Column(name = "defensive_touchdowns")
    private Long defensiveTouchdowns;

    @Column(name = "total_fumbles")
    private Long totalFumbles;

    @Column(name = "fumbles_lost")
    private Long fumblesLost;

    @Column(name = "fumbles_recovered")
    private Long fumblesRecovered;

    @Column(name = "interceptions")
    private Long interceptions;

    @Column(name = "interception_yards")
    private Long interceptionYards;

    @Column(name = "interception_touchdowns")
    private Long interceptionTouchdowns;

    @Column(name = "number_of_field_goals")
    private Long numberOfFieldGoals;

    @Column(name = "field_goal_percentage")
    private Long fieldGoalPercentage;

    @Column(name = "longest_field_goal")
    private Long longestFieldGoal;

    @Column(name = "extra_points")
    private Long extraPoints;

    @Column(name = "extra_point_attempts")
    private Long extraPointAttempts;

    @Column(name = "points")
    private Long points;

    @Column(name = "number_of_kick_returns")
    private Long numberOfKickReturns;

    @Column(name = "total_kick_return_yards")
    private Long totalKickReturnYards;

    @Column(name = "number_of_yards_per_kick_return")
    private Double numberOfYardsPerKickReturn;

    @Column(name = "longest_kick_return_yards")
    private Long longestKickReturnYards;

    @Column(name = "kick_return_touchdowns")
    private Long kickReturnTouchdowns;

    @Column(name = "completions")
    private Long completions;

    @Column(name = "passing_attempts")
    private Long passingAttempts;

    @Column(name = "passing_yards")
    private Long passingYards;

    @Column(name = "yards_per_pass_attempt")
    private Double yardsPerPassAttempt;

    @Column(name = "passing_touchdowns")
    private Long passingTouchdowns;

    @Column(name = "interceptions_thrown")
    private Long interceptionsThrown;

    @Column(name = "number_of_times_sacked")
    private Long numberOfTimesSacked;

    @Column(name = "sacked_yards_lost")
    private Long sackedYardsLost;

    @Column(name = "passer_rating")
    private Double passerRating;

    @Column(name = "adjusted_qbr")
    private Double adjustedQbr;

    @Column(name = "number_of_punts")
    private Long numberOfPunts;

    @Column(name = "total_punt_yards")
    private Long totalPuntYards;

    @Column(name = "punt_yards_per_punt")
    private Double puntYardsPerPunt;

    @Column(name = "touchbacks")
    private Long touchbacks;

    @Column(name = "in20")
    private Long in20;

    @Column(name = "longest_punt_yards")
    private Long longestPuntYards;

    @Column(name = "number_of_punt_returns")
    private Long numberOfPuntReturns;

    @Column(name = "total_punt_return_yards")
    private Long totalPuntReturnYards;

    @Column(name = "punt_return_yards_per_punt_return")
    private Double puntReturnYardsPerPuntReturn;

    @Column(name = "longest_punt_return_yards")
    private Long longestPuntReturnYards;

    @Column(name = "punt_return_touchdowns")
    private Long puntReturnTouchdowns;

    @Column(name = "total_receptions")
    private Long totalReceptions;

    @Column(name = "total_receiving_yards")
    private Long totalReceivingYards;

    @Column(name = "average_yards_per_reception")
    private Double averageYardsPerReception;

    @Column(name = "receiving_touchdowns")
    private Long receivingTouchdowns;

    @Column(name = "longest_reception")
    private Long longestReception;

    @Column(name = "receiving_targets")
    private Long receivingTargets;

    @Column(name = "rushing_attempts")
    private Long rushingAttempts;

    @Column(name = "averages")
    private Double averages;

    @Column(name = "rushing_touchdowns")
    private Long rushingTouchdowns;

    @Column(name = "longest_run")
    private Long longestRun;

}