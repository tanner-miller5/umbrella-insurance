export class GetNflGamesResponse {
    games?: Game[]
}

export class Game {
    gameId?: number;
    date?: string;
    gameDate?: number[];
    gameTime?: number[];
    timezone?: string;
    homeTeam?: {
        teamId?: number;
        teamName?: string;
    };
    awayTeam?: {    
        teamId?: number;
        teamName?: string;
    };
    homeScore?: {
        scoreId?: number;
        scoreValue?: number;
    };
    awayScore?: {
        scoreId?: number;
        scoreValue?: number;
    };
    sport?: {
        sportId?: number;
        sportName?: string;
    };
    status?: string;
    week?: {
        weekId: number | undefined;
        weekNumber: number | undefined;
        weekTitle: string | undefined;
        weekDateRange: string | undefined;
    };
    awayTeamRecord?: { 
        recordId?: number;
        wins?: number;
        ties?: number;
        losses?: number;
    };
    homeTeamRecord?: { 
        recordId?: number;
        wins?: number;
        ties?: number;
        losses?: number;
    };
    season?: {
        id: number | undefined;
        seasonName: string | undefined;
        startDate: string | undefined;
        endDate: string | undefined;
        schedule: {
            weeks: {
                weekId: number | undefined;
                weekNumber: number | undefined;
                weekTitle: string | undefined;
                weekDateRange: string | undefined;
            }
        }
        sport: {
            sportId: number | undefined;
            sportName: string | undefined;
        }
    }
    league?: {
        name: string | undefined;
    }
};