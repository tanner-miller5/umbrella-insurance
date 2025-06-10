import { Game } from "../../../../games/v1/Game";
import { Player } from "../../../../people/players/v1/Player";

export class FootballPlayerStats {
    id?: number;
    game?: Game;
    player?: Player;
    //Defense
    totalTackles?: number;//TOT
    soloTackles?: number;//SOLO
    totalSacks?: number;//SACKS
    tacklesForLoss?: number;//TFL
    passesDefended?: number;//PD
    quarterbackHits?: number;//QB HTS
    defensiveTouchdowns?: number;//TD
    //Fumbles
    totalFumbles?: number;//FUM
    fumblesLost?: number;//LST
    fumblesRecovered?: number;//REC
    //Interceptions
    interceptions?: number;//INT
    interceptionYards?: number;//YDS
    interceptionTouchdowns?: number;//TD
    //Kicking
    numberOfFieldGoals?: number;//FG
    fieldGoalPercentage?: number;//PCT
    longestFieldGoal?: number;//LONG
    extraPoints?: number;//XP
    extraPointAttempts?: number;//XPA
    points?: number;//PTS
    //Kick Returns
    numberOfKickReturns?: number;//NO
    totalKickReturnYards?: number;//YDS
    numberOfYardsPerKickReturn?: number;//AVG
    longestKickReturnYards?: number;//LONG
    kickReturnTouchdowns?: number;//TD
    //Passing
    completions?: number;//CMP
    passingAttempts?: number;//ATT
    passingYards?: number;//YDS
    yardsPerPassAttempt?: number;//AVG
    passingTouchdowns?: number;//TD
    interceptionsThrown?: number;//INT
    numberOfTimesSacked?: number;//SACK
    sackedYardsLost?: number;//SYD
    passerRating?: number;//RTG
    adjustedQbr?: number;//QBR
    //Punting
    numberOfPunts?: number;//NO
    totalPuntYards?: number;//YDS
    puntYardsPerPunt?: number;//AVG
    touchbacks?: number;//TB
    in20?: number;//in 20
    longestPuntYards?: number;//LONG
    //Punt Returns
    numberOfPuntReturns?: number;//NO
    totalPuntReturnYards?: number;//YDS
    puntReturnYardsPerPuntReturn?: number;//AVG
    longestPuntReturnYards?: number;//LONG
    puntReturnTouchdowns?: number;//TD
    //Receiving
    totalReceptions?: number;//REC
    totalReceivingYards?: number;//YDS
    averageYardsPerReception?: number;//AVG
    receivingTouchdowns?: number;//TD
    longestReception?: number;//LNG
    receivingTargets?: number;//TGTS
    //Rushing
    rushingAttempts?: number;//CAR
    averages?: number;//AVG
    rushingTouchdowns?: number;//TD
    longestRun?: number;//LNG

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.game) {
                this.game = new Game(obj.game);
            }
            if(obj?.player) {
                this.player = new Player(obj.player);
            }
            if(obj?.totalTackles) {
                this.totalTackles = obj.totalTackles;
            }
            if(obj?.soloTackles) {
                this.soloTackles = obj.soloTackles;
            }
            if(obj?.totalSacks) {
                this.totalSacks = obj.totalSacks;
            }
            if(obj?.tacklesForLoss) {
                this.tacklesForLoss = obj.tacklesForLoss;
            }
            if(obj?.passesDefended) {
                this.passesDefended = obj.passesDefended;
            }
            if(obj?.quarterbackHits) {
                this.quarterbackHits = obj.quarterbackHits;
            }
            if(obj?.defensiveTouchdowns) {
                this.defensiveTouchdowns = obj.defensiveTouchdowns;
            }
            if(obj?.totalFumbles) {
                this.totalFumbles = obj.totalFumbles;
            }
            if(obj?.fumblesLost) {
                this.fumblesLost = obj.fumblesLost;
            }
            if(obj?.fumblesRecovered) {
                this.fumblesRecovered = obj.fumblesRecovered;
            }
            if(obj?.interceptions) {
                this.interceptions = obj.interceptions;
            }
            if(obj?.interceptionYards) {
                this.interceptionYards = obj.interceptionYards;
            }
            if(obj?.interceptionTouchdowns) {
                this.interceptionTouchdowns = obj.interceptionTouchdowns;
            }
            if(obj?.numberOfFieldGoals) {
                this.numberOfFieldGoals = obj.numberOfFieldGoals;
            }
            if(obj?.fieldGoalPercentage) {
                this.fieldGoalPercentage = obj.fieldGoalPercentage;
            }
            if(obj?.longestFieldGoal) {
                this.longestFieldGoal = obj.longestFieldGoal;
            }
            if(obj?.extraPoints) {
                this.extraPoints = obj.extraPoints;
            }
            if(obj?.extraPointAttempts) {
                this.extraPointAttempts = obj.extraPointAttempts;
            }
            if(obj?.points) {
                this.points = obj.points;
            }
            if(obj?.numberOfKickReturns) {
                this.numberOfKickReturns = obj.numberOfKickReturns;
            }
            if(obj?.totalKickReturnYards) {
                this.totalKickReturnYards = obj.totalKickReturnYards;
            }
            if(obj?.numberOfYardsPerKickReturn) {
                this.numberOfYardsPerKickReturn = obj.numberOfYardsPerKickReturn;
            }
            if(obj?.longestKickReturnYards) {
                this.longestKickReturnYards = obj.longestKickReturnYards;
            }
            if(obj?.kickReturnTouchdowns) {
                this.kickReturnTouchdowns = obj.kickReturnTouchdowns;
            }
            if(obj?.completions) {
                this.completions = obj.completions;
            }
            if(obj?.passingAttempts) {
                this.passingAttempts = obj.passingAttempts;
            }
            if(obj?.passingYards) {
                this.passingYards = obj.passingYards;
            }
            if(obj?.yardsPerPassAttempt) {
                this.yardsPerPassAttempt = obj.yardsPerPassAttempt;
            }
            if(obj?.passingTouchdowns) {
                this.passingTouchdowns = obj.passingTouchdowns;
            }
            if(obj?.interceptionsThrown) {
                this.interceptionsThrown = obj.interceptionsThrown;
            }
            if(obj?.numberOfTimesSacked) {
                this.numberOfTimesSacked = obj.numberOfTimesSacked;
            }
            if(obj?.sackedYardsLost) {
                this.sackedYardsLost = obj.sackedYardsLost;
            }
            if(obj?.passerRating) {
                this.passerRating = obj.passerRating;
            }
            if(obj?.adjustedQbr) {
                this.adjustedQbr = obj.adjustedQbr;
            }
            if(obj?.numberOfPunts) {
                this.numberOfPunts = obj.numberOfPunts;
            }
            if(obj?.totalPuntYards) {
                this.totalPuntYards = obj.totalPuntYards;
            }
            if(obj?.puntYardsPerPunt) {
                this.puntYardsPerPunt = obj.puntYardsPerPunt;
            }
            if(obj?.touchbacks) {
                this.touchbacks = obj.touchbacks;
            }
            if(obj?.in20) {
                this.in20 = obj.in20;
            }
            if(obj?.longestPuntYards) {
                this.longestPuntYards = obj.longestPuntYards;
            }
            if(obj?.numberOfPuntReturns) {
                this.numberOfPuntReturns = obj.numberOfPuntReturns;
            }
            if(obj?.totalPuntReturnYards) {
                this.totalPuntReturnYards = obj.totalPuntReturnYards;
            }
            if(obj?.puntReturnYardsPerPuntReturn) {
                this.puntReturnYardsPerPuntReturn = obj.puntReturnYardsPerPuntReturn;
            }
            if(obj?.longestPuntReturnYards) {
                this.longestPuntReturnYards = obj.longestPuntReturnYards;
            }
            if(obj?.puntReturnTouchdowns) {
                this.puntReturnTouchdowns = obj.puntReturnTouchdowns;
            }
            if(obj?.totalReceptions) {
                this.totalReceptions = obj.totalReceptions;
            }
            if(obj?.totalReceivingYards) {
                this.totalReceivingYards = obj.totalReceivingYards;
            }
            if(obj?.averageYardsPerReception) {
                this.averageYardsPerReception = obj.averageYardsPerReception;
            }
            if(obj?.receivingTouchdowns) {
                this.receivingTouchdowns = obj.receivingTouchdowns;
            }
            if(obj?.longestReception) {
                this.longestReception = obj.longestReception;
            }
            if(obj?.receivingTargets) {
                this.receivingTargets = obj.receivingTargets;
            }
            if(obj?.rushingAttempts) {
                this.rushingAttempts = obj.rushingAttempts;
            }
            if(obj?.averages) {
                this.averages = obj.averages;
            }
            if(obj?.rushingTouchdowns) {
                this.rushingTouchdowns = obj.rushingTouchdowns;
            }
            if(obj?.longestRun) {
                this.longestRun = obj.longestRun;
            }
        }
    }
}