import { createSlice } from '@reduxjs/toolkit'
import { FootballTeamStats } from '../../models/stats/teamStats/footballTeamStats/v1/FootballTeamStats';
import { FootballPlayerStats } from '../../models/stats/playerStats/footballPlayerStats/v1/FootballPlayerStats';

export class StatState {
    footballPlayerStats?: FootballPlayerStats[]; 
    footballTeamStats?: FootballTeamStats[]; 
    constructor() {

    }
}

export const statSlice = createSlice({
  name: 'stat',
  initialState: new StatState(),
  reducers: {
    updateFootballPlayerStats: (state, action) => {
      return {
        ...state,
        footballPlayerStats: action.payload
      }
    },
    updateFootballTeamStats: (state, action) => {
        return {
          ...state,
          footballTeamStats: action.payload
        }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateFootballPlayerStats, updateFootballTeamStats } = statSlice.actions

export default statSlice.reducer;