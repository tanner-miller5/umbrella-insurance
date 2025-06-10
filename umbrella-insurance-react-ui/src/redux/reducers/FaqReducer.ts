import { createSlice } from '@reduxjs/toolkit'
import { Faq } from '../../models/faqs/v1/Faq'

export class FaqState {
  faq?: Faq;
  constructor() {
    this.faq = new Faq();
  }
}

export const faqSlice = createSlice({
  name: 'faq',
  initialState: new FaqState(),
  reducers: {
    updateFaq: (state, action) => {
      return {
        ...state,
        faq: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateFaq } = faqSlice.actions

export default faqSlice.reducer