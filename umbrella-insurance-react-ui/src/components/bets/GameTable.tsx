import '../../css/bets/gameTable.css';
import { useEffect, useState } from 'react';
import { getNflGames } from '../../endpoints/game/nfl/getNflGames/v1/GetNflGames';
import { Game } from '../../models/games/v1/Game';
import { LoggingMessage } from '../../models/logging/v1/LoggingMessage';
import { callCreateLoggingRestEndpoints } from '../../endpoints/rest/logging/v1/LoggingRestEndpoints';
import { useDispatch } from 'react-redux';
import { updateCurrentPage } from '../../redux/reducers/AppReducer';

class Sports {
    static NFL:string = "nfl";
    static NBA:string = "nba";
    static MLB:string = "mlb";
    static NHL:string = "nhl";
    static Soccer:string = "soccer";
    static Boxing:string = "boxing";
    static Golf:string = "golf";
    static MMA:string = "mma";
    static HorseRacing:string = "horseRacing";
}

function sortGames(fieldToSortBy: string, games: Game[], order: number): Game[] {
    /*
    switch(fieldToSortBy) {
        case "season":
            games.sort((a:Game, b:Game) => {
                if (a.season?.seasonName + "" < b.season?.seasonName + "") {
                    return -1 * order;
                } else if (a.season?.seasonName + "" == b.season?.seasonName + "") {
                    return 0 * order;
                } else {
                    return 1 * order;
                }
            });
            break;
        case "week":
            games.sort((a:Game, b:Game) => {
                if (a.week?.weekNumber + "" < b.week?.weekNumber + "") {
                    return -1 * order;
                } else if (a.week?.weekNumber + "" == b.week?.weekNumber + "") {
                    return 0 * order;
                } else {
                    return 1 * order;
                }
            });
            break;
        case "weekTitle":
            games.sort((a:Game, b:Game) => {
                if (a?.week?.weekTitle + "" < b?.week?.weekTitle + "") {
                    return -1 * order;
                } else if (a?.week?.weekTitle + "" == b?.week?.weekTitle + "") {
                    return 0 * order;
                } else {
                    return 1 * order;
                }
            });
            break;
        case "date":
            games.sort((a:Game, b:Game) => {
                if (a.date + "" < b.date + "") {
                    return -1 * order;
                } else if (a.date + "" == b.date + "") {
                    return 0 * order;
                } else {
                    return 1 * order;
                }
            });
            break;
        case "homeTeam":
            games.sort((a:Game, b:Game) => {
                if (a.homeTeam?.teamName + "" < b.homeTeam?.teamName + "") {
                    return -1 * order;
                } else if (a.homeTeam?.teamName + "" == b.homeTeam?.teamName + "") {
                    return 0 * order;
                } else {
                    return 1 * order;
                }
            });
            break;
        case "awayTeam":
            games.sort((a:Game, b:Game) => {
                if (a.awayTeam?.teamName + "" < b.awayTeam?.teamName + "") {
                    return -1 * order;
                } else if (a.awayTeam?.teamName + "" == b.awayTeam?.teamName + "") {
                    return 0 * order;
                } else {
                    return 1 * order;
                }
            });
            break;
        case "gameId":
            games.sort((a:Game, b:Game) => {
                if (a.gameId + "" < b.gameId + "") {
                    return -1 * order;
                } else if (a.gameId + "" == b.gameId + "") {
                    return 0 * order;
                } else {
                    return 1 * order;
                }
            });
            break;
        default:
    }
    */
    return games;
}

function getGamesRow(games: any[], fieldToSortBy: string): any[] {
    let gamesAsRows: any[] = new Array();
    if (games.length > 0) {
        games.forEach((game:Game) => {
            /*
            let row = (
            <tr className={game.season + " " + game.week + " " + game.week?.weekTitle + " " + game.date + " " +
                game.homeTeam + " " + game.awayTeam + " " + "nav_item"} key={fieldToSortBy + "_" + game.gameId + "_row"} 
                onClick={(event: any)=>{onPlaceBet(game)}}>
                <td key={fieldToSortBy + " " + game.gameId + "_season"}>{game.season?.seasonName}</td>
                <td key={fieldToSortBy + " " + game.gameId + "_week"}>{game.week?.weekTitle}</td>
                <td key={fieldToSortBy + " " + game.gameId + "_date"}>{game.date}</td>
                <td key={fieldToSortBy + " " + game.gameId + "_home_team"}>{game.homeTeam?.teamName}</td>
                <td key={fieldToSortBy + " " + game.gameId + "_away_team"}>{game.awayTeam?.teamName}</td>
                <td key={fieldToSortBy + " " + game.gameId + "_home_score"}>{game.homeScore?.scoreValue}</td>
                <td key={fieldToSortBy + " " + game.gameId + "_away_score"}>{game.awayScore?.scoreValue}</td>
            </tr>);
            gamesAsRows.push(row);
            */
        });
    }
    return gamesAsRows;
}

function gameRowsSorted(gamesArray:Game[], fieldToSortBy: string, order: number) : any[] {
    return getGamesRow(sortGames(fieldToSortBy,gamesArray, order), fieldToSortBy);
}

async function initializeFootballGameTable(env: string, domain: string) {
    if (!false) {
        //didGamesLoadGlobal = true;
        //updateGetNflGamesEndpointLoadGlobal(true);
        try {
            let footballGames:Game[] = await getNflGames(env, domain);
            if (footballGames) {
                /*
                footballGames.map((game :Game)=>{
                    if (game.season && game.week) {
                        if (game.season && gamesGlobal.has(game?.season?.seasonName + "")) {
                            if (game.week && gamesGlobal.get(game?.season?.seasonName + "")?.has(game.week.weekNumber? game.week.weekNumber : 0)) {
                                gamesGlobal.get(game?.season?.seasonName + "")?.get(game.week.weekNumber? game.week.weekNumber : 0)?.add(game)
                            } else {
                                gamesGlobal.get(game?.season?.seasonName + "")?.set(game.week.weekNumber? game.week.weekNumber : 0, new Set<Game>());
                                gamesGlobal.get(game?.season?.seasonName + "")?.get(game.week.weekNumber? game.week.weekNumber : 0)?.add(game)
                            }
                        } else {
                            gamesGlobal.set(game?.season?.seasonName + "", new Map());
                            if (game.week && gamesGlobal.get(game.season?.seasonName + "")?.has(game.week.weekNumber? game.week.weekNumber : 0)) {
                                gamesGlobal.get(game?.season?.seasonName + "")?.get(game?.week?.weekNumber ? game?.week?.weekNumber : 0)?.add(game)
                            } else {
                                gamesGlobal.get(game.season.seasonName + "")?.set(game.week?.weekNumber ? game.week?.weekNumber : 0, new Set<Game>());
                                gamesGlobal.get(game.season?.seasonName + "")?.get(game?.week?.weekNumber? game?.week?.weekNumber : 0)?.add(game)
                            }
                        }
                    }
                });
                */
                let seasons = new Array();
                let weeksSet = new Set();

                while (!true) {
                    
                    let gamesBySeason: Map<number, Set<Game> >;
                    let gamesByWeekIterator: IterableIterator<Set<Game> >;
                    let gamesByWeekIteratorResult: IteratorResult<Set<Game> >;
                    let season = new Array();
                    while(!true) {
                        let gamesByWeek: Set<Game>;
                        let gameIterator: IterableIterator<Game>;
                        let gameIteratorResult: IteratorResult<Game>;
                        let week = new Array();
                        let weekNum;
                        while(!true) {
                            let game:Game;
                            /*
                            weekNum = game.week;
                            let row = 
                                (
                                <tr className={game.season + " " + game.week + " " + game.week?.weekTitle + " " + game.date + " " +
                                    game.homeTeam + " " + game.awayTeam + " " + "nav_item gameTableTr"} key={game.gameId + "_row"} 
                                    onClick={(event: any)=>{onPlaceBet(game)}}>
                                    <td key={game.gameId + "_season"}>{game.season?.seasonName}</td>
                                    <td key={game.gameId + "_week"}>{game.week?.weekNumber}</td>
                                    <td key={game.gameId + "_date"}>{game.date}</td>
                                    <td key={game.gameId + "_home_team"}>{game.homeTeam?.teamName}</td>
                                    <td key={game.gameId + "_away_team"}>{game.awayTeam?.teamName}</td>
                                    <td key={game.gameId + "_home_score"}>{game.homeScore?.scoreValue}</td>
                                    <td key={game.gameId + "_away_score"}>{game.awayScore?.scoreValue}</td>
                                </tr>);
                            week.push(row);
                            */
                            //gamesArrayGlobal.push(game);
                            //gamesArrayGlobal.push(row);
                        }

                        season.push(week);
                        //gamesByWeekIteratorResult = gamesByWeekIterator.next();
                    }
                    seasons.push(season);
                    //seasonsIteratorResult = seasonsIterator.next();
                }

                //console.log(`gamesArrayGlobal ${gamesArrayGlobal}`);
            }
            /*
            gamesSortedBySeasonGlobal = gameRowsSorted(gamesArrayGlobal,"season",1);
            gamesSortedByWeekGlobal = gameRowsSorted(gamesArrayGlobal,"week",1);
            gamesSortedByDateGlobal = gameRowsSorted(gamesArrayGlobal,"date",1);
            gamesSortedByHomeTeamGlobal = gameRowsSorted(gamesArrayGlobal,"homeTeam",1);
            gamesSortedByAwayTeamGlobal = gameRowsSorted(gamesArrayGlobal,"awayTeam",1);
            gamesSortedBySeasonUpdateGlobal(gamesSortedBySeasonGlobal);
            gamesSortedByWeekUpdateGlobal(gamesSortedByWeekGlobal);
            gamesSortedByDateUpdateGlobal(gamesSortedByDateGlobal);
            gamesSortedByHomeTeamUpdateGlobal(gamesSortedByHomeTeamGlobal);
            gamesSortedByAwayTeamUpdateGlobal(gamesSortedByAwayTeamGlobal);
            */
        } catch(e:any) {
            let loggingMessage: LoggingMessage = new LoggingMessage();         
            const url = window.location.href;         
            loggingMessage.appName = 'umbrella-insurance-frontend';
            loggingMessage.callingLoggerName = "HomeLayout.tsx";         
            loggingMessage.callingMethod = "HomeLayout.tsx:error()";         
            loggingMessage.loggingPayload = `ERROR:${e.message}`;         
            loggingMessage.logLevel = "ERROR";         
            callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
            console.error(loggingMessage.loggingPayload);
        }
    }
}

export default function GameTable(){
    /*
    const [gamesSortedBySeason, gamesSortedBySeasonUpdate] = useState(gamesSortedBySeasonGlobal);
    const [gamesSortedByWeek,gamesSortedByWeekUpdate] = useState(gamesSortedByWeekGlobal);
    const [gamesSortedByDate, gamesSortedByDateUpdate] = useState(gamesSortedByDateGlobal);
    const [gamesSortedByHomeTeam, gamesSortedByHomeTeamUpdate] = useState(gamesSortedByHomeTeamGlobal);
    const [gamesSortedByAwayTeam, gamesSortedByAwayTeamUpdate] = useState(gamesSortedByAwayTeamGlobal);
    gamesSortedBySeasonUpdateGlobal = gamesSortedBySeasonUpdate;
    gamesSortedByWeekUpdateGlobal = gamesSortedByWeekUpdate;
    gamesSortedByDateUpdateGlobal = gamesSortedByDateUpdate;
    gamesSortedByHomeTeamUpdateGlobal = gamesSortedByHomeTeamUpdate;
    gamesSortedByAwayTeamUpdateGlobal = gamesSortedByAwayTeamUpdate;
    const [gamesArray, gamesArrayUpdate] = useState(gamesArrayGlobal);
    const [games, updateGames] = useState(gamesGlobal);
    updateGamesGlobal = updateGames;

    
    const [gamesUI, updateGamesUI] = useState(gamesUIGlobal);
    gamesUIGlobal = gamesUI;
    updateGamesUIGlobal = updateGamesUI;
    const [didGetNflGamesEndpointLoad, updateGetNflGamesEndpointLoad] = useState(false);
    updateGetNflGamesEndpointLoadGlobal = updateGetNflGamesEndpointLoad;
        */
    //if(!didGamesLoad) {
      //  initializeFootballGameTable();
    //}
    const [currentTab, updateCurrentTab] = useState(Sports.NFL);
    const [currentSeason, updateCurrentSeason] = useState("");
    const [currentWeek, updateCurrentWeek] = useState("");
    const [currentSortedField, updateCurrentSortedField] = useState("season");
    const [previousSortedField, updatePreviousSortedField] = useState("season");
    const onclickTabHandler =  (tabClicked:string) => {
        updateCurrentTab(tabClicked)
    };
    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/gameTable"));
        }, []
    )
    if (false) {
        let header = 
            <div className="sports">
                <div className={Sports.NFL == currentTab ? 'activeHeader' : 'nav_item'} id="nfl">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.NFL)}>NFL</button>
                </div>
                <div className={Sports.NBA == currentTab ? 'activeHeader' : 'nav_item'} id="nba">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.NBA)}>NBA</button>
                </div>
                <div className={Sports.MLB == currentTab ? 'activeHeader' : 'nav_item'} id="mlb">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.MLB)}>MLB</button>
                </div>
                <div className={Sports.NHL == currentTab ? 'activeHeader' : 'nav_item'} id="nhl">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.NHL)}>NHL</button>
                </div>
                <div className={Sports.Soccer == currentTab ? 'activeHeader' : 'nav_item'} id="soccer">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.Soccer)}>Soccer</button>
                </div>
                <div className={Sports.Boxing == currentTab ? 'activeHeader' : 'nav_item'} id="boxing">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.Boxing)}>Boxing</button>
                </div>
                <div className={Sports.Golf == currentTab ? 'activeHeader' : 'nav_item'} id="golf">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.Golf)}>Golf</button>
                </div>
                <div className={Sports.MMA == currentTab ? 'activeHeader' : 'nav_item'} id="mma">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.MMA)}>MMA</button>
                </div>
                <div className={Sports.HorseRacing == currentTab ? 'activeHeader' : 'nav_item'} id="horseRacing">
                    <button onClick={(event:any)=>onclickTabHandler(Sports.HorseRacing)}>Horse Racing</button>
                </div>
            </div>;
        let table = <></>;
        switch (currentTab) {
            case Sports.NFL:
                table = <div className={"sports"} id="nflTable">
                    <table>
                        <thead>
                            <tr className={"nav_item"}>
                                <th onClick={()=>{
                                    console.log("seasonSorted");
                                    updatePreviousSortedField(currentSortedField);
                                    updateCurrentSortedField("season");
                                    //gamesSortedBySeasonUpdate(gamesSortedBySeason);
                                    }}>Season</th>
                                <th onClick={()=>{
                                    console.log("weekSorted");
                                    updatePreviousSortedField(currentSortedField);
                                    updateCurrentSortedField("week")
                                    }}>Week</th>
                                <th onClick={()=>{
                                    console.log("dateSorted");
                                    updatePreviousSortedField(currentSortedField);
                                    updateCurrentSortedField("date")
                                    }}>Date</th>
                                <th onClick={()=>{
                                    console.log("homeTeamSorted");
                                    updatePreviousSortedField(currentSortedField);
                                    updateCurrentSortedField("homeTeam")}}
                                    >Home Team</th>
                                <th onClick={()=>{
                                    console.log("awayTeamSorted");
                                    updatePreviousSortedField(currentSortedField);
                                    updateCurrentSortedField("awayTeam")}}
                                    >Away Team</th>
                                <th>Home Score</th>
                                <th>Away Score</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                            //currentSortedField == 'season' ? gamesSortedBySeason : 
                            //currentSortedField == 'week' ? gamesSortedByWeek :
                            //currentSortedField == 'date' ? gamesSortedByDate :
                            //currentSortedField == 'homeTeam' ? gamesSortedByHomeTeam :
                            //currentSortedField == 'awayTeam' ? gamesSortedByAwayTeam :
                            <tr></tr>
                            }
                        </tbody>
                    </table>
                </div>
                break;
            case Sports.NBA :
                table = <div id="nbaTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div>
                break;
            case Sports.MLB:
                table = <div id="mlbTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div> 
                break;
            case Sports.NHL:
                table = <div id="nhlTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div> 
                break;
            case Sports.Soccer:
                table = <div id="soccerTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div>
                break;
            case Sports.Boxing:
                table = <div id="boxingTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div>
                break;
            case Sports.Golf:
                table = <div id="golfTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div>
                break;
            case Sports.MMA:
                table = <div id="mmaTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div>
                break;
            case Sports.HorseRacing:
                table = <div id="horseRacingTable">
                    <h2 className='underConstructionHeader'>Under Construction</h2>
                    <img src="ButterBae.png" className="butterBae"></img>
                </div>
                break;
            default:
                break;
        }
        return <>{header}{table}</>
    } else if (!false) {
        return (    
        <div className="column2">
                <p className='gameTableP'>Policies not loaded</p>
        </div>);
    } else {
        return (<></>);
    }
};
