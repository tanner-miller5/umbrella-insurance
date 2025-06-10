import axios from "axios";
import { Game } from "../../../../../models/games/v1/Game";

export async function getNflGames(env: string, domain: string): Promise<Game[]> {
  let config = {
    headers: {
        "Access-Control-Allow-Origin": "*",
        'Content-Type': 'application/json',
    }
  }  
  let result = await axios.get(`${domain}/games/nfl`, config);
  let games : Game[]
  games = result.data;
  return games;
}