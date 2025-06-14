import { createSlice } from '@reduxjs/toolkit'
import { State } from '../../models/geographies/states/v1/State';

export class GeographyState {
  states?: State[];
  constructor() {
  }
}

export const geographySlice = createSlice({
  name: 'geography',
  initialState: new GeographyState(),
  reducers: {
    updateStates: (state, action) => {
      return {
        ...state,
        states: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateStates } = geographySlice.actions

export default geographySlice.reducer