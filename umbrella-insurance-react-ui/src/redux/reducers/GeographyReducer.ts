import { createSlice } from '@reduxjs/toolkit'
import { State } from '../../models/geographies/states/v1/State';
import { Location } from '../../models/geographies/locations/v1/Location';

export class GeographyState {
  states?: State[];
  cities?: Location[];
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
    },
    updateCities: (state, action) => {
      return {
        ...state,
        cities: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateStates, updateCities } = geographySlice.actions

export default geographySlice.reducer