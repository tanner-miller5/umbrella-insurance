import { createSlice } from '@reduxjs/toolkit'

export class LoadingState {
  value: boolean;
  constructor() {
    this.value = false;
  }
}

export const loadingSlice = createSlice({
  name: 'loading',
  initialState: new LoadingState(),
  reducers: {
    updateLoadingState: (state, action) => {
      return {
        ...state,
        value: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateLoadingState } = loadingSlice.actions

export default loadingSlice.reducer