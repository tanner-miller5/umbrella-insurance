import { createSlice } from '@reduxjs/toolkit'
import { Review } from '../../models/reviews/v1/Review'

export class ReviewState {
  review?: Review;
  constructor() {
    this.review = new Review();
  }
}

export const reviewSlice = createSlice({
  name: 'review',
  initialState: new ReviewState(),
  reducers: {
    updateReview: (state, action) => {
      return {
        ...state,
        review: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateReview } = reviewSlice.actions

export default reviewSlice.reducer