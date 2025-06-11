import { createSlice } from '@reduxjs/toolkit'
import { Announcement } from '../../models/announcements/v1/Announcement';
import { Faq } from '../../models/faqs/v1/Faq';
import { Unit } from '../../models/units/v1/Unit';
import { Review } from '../../models/reviews/v1/Review';
import { Item } from '../../models/items/v1/Item';
import { Peril } from '../../models/perils/v1/Peril';


export class AppState {
  currentPage: string;
  announcements: Announcement[];
  faqs: Faq[];
  isHamburgerMenuOpen: boolean;
  isErrorOpen: boolean;
  errorMessage?: string;
  units?: Unit[];
  reviews: Review[];
  isWarningOpen: boolean;
  warningMessage?: string;
  isPrivacyPolicyOpen: boolean;
  isTermsAndConditionsOpen: boolean;
  backendAppVersion?: string;
  frontendAppVersion?: string;
  items: Item[];
  perils: Peril[];
  constructor() {
    this.currentPage = "/";
    this.announcements = [];
    this.faqs = [];
    this.isHamburgerMenuOpen = false;
    this.isErrorOpen = false;
    this.reviews = [];
    this.isWarningOpen = false;
    this.isPrivacyPolicyOpen = false;
    this.isTermsAndConditionsOpen = false;
    this.items = [];
    this.perils = [];
  }
}

export const appSlice = createSlice({
  name: 'app',
  initialState: new AppState(),
  reducers: {
    updateCurrentPage: (state, action) => {
      return {
        ...state,
        currentPage: action.payload
      }
    },
    updateAnnouncements: (state, action) => {
      return {
        ...state,
        announcements: action.payload
      }
    },
    updateFaqs: (state, action) => {
      return {
        ...state,
        faqs: action.payload
      }
    },
    updateIsHamburgerMenuOpen: (state, action) => {
      return {
        ...state,
        isHamburgerMenuOpen: action.payload
      }
    },
    updateIsErrorOpen: (state, action) => {
      return {
        ...state,
        isErrorOpen: action.payload
      }
    },
    updateErrorMessage: (state, action) => {
      return {
        ...state,
        errorMessage: action.payload
      }
    },
    updateUnits: (state, action) => {
      return {
        ...state,
        units: action.payload
      }
    },
    updateReviews: (state, action) => {
      return {
        ...state,
        reviews: action.payload
      }
    },
    updateIsWarningOpen: (state, action) => {
      return {
        ...state,
        isWarningOpen: action.payload
      }
    },
    updateWarningMessage: (state, action) => {
      return {
        ...state,
        warningMessage: action.payload
      }
    },
    updateIsPrivacyPolicyOpen: (state, action) => {
      return {
        ...state,
        isPrivacyPolicyOpen: action.payload
      }
    },
    updateIsTermsAndConditionsOpen: (state, action) => {
      return {
        ...state,
        isTermsAndConditionsOpen: action.payload
      }
    },
    updateFrontendAppVersion: (state, action) => {
      return {
        ...state,
        frontendAppVersion: action.payload
      }
    },
    updateBackendAppVersion: (state, action) => {
      return {
        ...state,
        backendAppVersion: action.payload
      }
    },
    updateItems: (state, action) => {
      return {
        ...state,
        items: action.payload
      }
    },
    updatePerils: (state, action) => {
      return {
        ...state,
        perils: action.payload
      }
    },
  }
})

// Action creators are generated for each case reducer function
export const { updateCurrentPage, updateAnnouncements, updateFaqs,
  updateIsHamburgerMenuOpen, updateIsErrorOpen, updateErrorMessage,
  updateUnits, updateReviews, updateIsWarningOpen, updateWarningMessage,
  updateIsPrivacyPolicyOpen, updateIsTermsAndConditionsOpen,
  updateFrontendAppVersion, updateBackendAppVersion, updateItems,
  updatePerils
 } = appSlice.actions

export default appSlice.reducer