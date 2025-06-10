import { createSlice } from '@reduxjs/toolkit'

export class CounterState {
  value: number;
  constructor() {
    this.value = 0;
  }
}

export const counterSlice = createSlice({
  name: 'counter',
  initialState: new CounterState(),
  reducers: {
    increment: (state, action) => {
      return {
        ...state,
        value: state.value + 1
      }
    },
    decrement: (state, action) => {
      return {
        ...state,
        value: state.value - 1
      }
    },
    incrementByAmount: (state, action) => {
      return {
        ...state,
        value: state.value + action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { increment, decrement, incrementByAmount } = counterSlice.actions

export default counterSlice.reducer