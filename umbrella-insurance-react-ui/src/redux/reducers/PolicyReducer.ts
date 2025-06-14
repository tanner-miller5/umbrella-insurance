import { createSlice } from '@reduxjs/toolkit'

export class PolicyState {
  policyFor?: string;
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
    }
  }
})

// Action creators are generated for each case reducer function
export const { updatePolicyFor } = policySlice.actions

export default policySlice.reducer