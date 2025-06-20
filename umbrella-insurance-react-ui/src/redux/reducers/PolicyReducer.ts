import { createSlice } from '@reduxjs/toolkit'
import { Peril } from '../../models/perils/v1/Peril';
import { PendingPolicy } from '../../models/users/policies/pendingPolicies/v1/PendingPolicy';

export class PolicyState {
  policyFor?: string;
  selectedPeril?: string;
  selectedPerilMinMagnitude?: number;
  selectedPerilMaxMagnitude?: number;
  selectedState?: string;
  selectedCity?: string;
  startPolicyMonthAndYear?: string;
  endPolicyMonthAndYear?: string;
  selectedMagnitude?: number;
  selectedCoverageAmount?: number;
  selectedPremiumAmount?: number;
  peril?: Peril;
  selectedPendingPolicy?: PendingPolicy;
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
    },
    updateStartPolicyMonthAndYear: (state, action) => {
      return {
        ...state,
        startPolicyMonthAndYear: action.payload
      }
    },
    updateEndPolicyMonthAndYear: (state, action) => {
      return {
        ...state,
        endPolicyMonthAndYear: action.payload
      }
    },
    updateSelectedMagnitude: (state, action) => {
      return {
        ...state,
        selectedMagnitude: action.payload
      }
    },
    updateSelectedPerilMinMagnitude: (state, action) => {
      return {
        ...state,
        selectedPerilMinMagnitude: action.payload
      }
    },
    updateSelectedPerilMaxMagnitude: (state, action) => {
      return {
        ...state,
        selectedPerilMaxMagnitude: action.payload
      }
    },
    updateSelectedCoverageAmount: (state, action) => {
      return {
        ...state,
        selectedCoverageAmount: action.payload
      }
    },
    updateSelectedPremiumAmount: (state, action) => {
      return {
        ...state,
        selectedPremiumAmount: action.payload
      }
    },
    updatePeril: (state, action) => {
      return {
        ...state,
        peril: action.payload
      }
    },
    updateSelectedPendingPolicy: (state, action) => {
      return {
        ...state,
        selectedPendingPolicy: action.payload
      }
    },
  }
})

// Action creators are generated for each case reducer function
export const { updatePolicyFor, updateSelectedPeril,
    updateSelectedState, updateSelectedCity,
    updateStartPolicyMonthAndYear, updateEndPolicyMonthAndYear,
    updateSelectedMagnitude, updateSelectedPerilMinMagnitude,
    updateSelectedPerilMaxMagnitude, updateSelectedCoverageAmount,
    updateSelectedPremiumAmount, updatePeril,
    updateSelectedPendingPolicy
 } = policySlice.actions

export default policySlice.reducer