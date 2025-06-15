import { createSlice } from '@reduxjs/toolkit'

export class PolicyState {
  policyFor?: string;
  selectedPeril?: string;
  selectedState?: string;
  selectedCity?: string;
  constructor() {
  }
}

export const policySlice = createSlice({
  name: 'policy',
  initialState: new PolicyState(),
  reducers: {
    updatePolicyFor: (state, action) => {
      return {
        ...state,
        policyFor: action.payload
      }
    },
    updateSelectedPeril: (state, action) => {
      return {
        ...state,
        selectedPeril: action.payload
      }
    },
    updateSelectedState: (state, action) => {
      return {
        ...state,
        selectedState: action.payload
      }
    },
    updateSelectedCity: (state, action) => {
      return {
        ...state,
        selectedCity: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updatePolicyFor, updateSelectedPeril,
    updateSelectedState, updateSelectedCity
 } = policySlice.actions

export default policySlice.reducer