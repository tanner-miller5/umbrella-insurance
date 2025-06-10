import { createSlice } from '@reduxjs/toolkit'
import { ApplicationRole } from '../../models/applicationRoles/v1/ApplicationRole';
import { Item } from '../../models/items/v1/Item';
import { Cart } from '../../models/carts/v1/Cart';
import { CartItemRelationship } from '../../models/cartItemRelationships/v1/CartItemRelationship';

export class UserState {
  username?: string;
  tmpUsername?: string;
  password?: string;
  emailAddress?: string;
  tmpEmailAddress?: string;
  phoneNumber?: string;
  tmpPhoneNumber?: string;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  dateOfBirth?: string;
  consentedToEmails?: boolean;
  tmpConsentedToEmails?: boolean;
  consentedToTexts?: boolean;
  tmpConsentedToTexts?: boolean;
  twoFactorAuthType?: string;
  tmpTwoFactorAuthType?: string;
  consentedToTermsAndConditions?: boolean;
  resetMethod?: string;
  existingPassword?: string;
  verificationCode?: string;
  usdAccountValue?: number;
  butterBucksAccountValue?: number;
  usdEscrowAccountValue?: number;
  butterBucksEscrowAccountValue?: number;
  authAppDataUrl?: string;
  userAgent?: string;
  latitude?: number;
  longitude?: number;
  accuracy?: number;
  newPassword?: string;
  confirmNewPassword?: string;
  sessionCode?: string;
  qrCode?: any;
  didUserLoad: boolean;
  confirmPassword?: string;
  isEmailAddressVerified?: boolean;
  isPhoneNumberVerified?: boolean;
  isAuthAppVerified?: boolean;
  cantSeeQrCode?: boolean;
  createBettingEventName?: string;
  createBettingEventDateTime?: string;
  applicationRoles?: ApplicationRole[];
  endDateTime?: string;
  userId?: number;
  cartItems?: Item[];
  cart?: Cart;
  cartItemRelationship?: CartItemRelationship[];
  constructor() {
    this.didUserLoad = false;
  }
/*
  toJSON() {
    return {
        "username": this.username ? this.username : null,
        "tmpUsername": this.tmpUsername ? this.tmpUsername : null,
        "password": this.password ? this.password : null,
        "emailAddress": this.emailAddress ? this.emailAddress : null,
        "tmpEmailAddress": this.tmpEmailAddress ? this.tmpEmailAddress : null,
        "phoneNumber": this.phoneNumber ? this.phoneNumber : null,
        "tmpPhoneNumber": this.tmpPhoneNumber ? this.tmpPhoneNumber : null,

        "firstName": this.firstName ? this.firstName : null,
        "middleName": this.middleName ? this.middleName : null,
        "lastName": this.lastName ? this.lastName  : null,
        "dateOfBirth": this.dateOfBirth ? this.dateOfBirth : null,
        "consentedToEmails": this.consentedToEmails ? this.consentedToEmails : null,
        "tmpConsentedToEmails": this.tmpConsentedToEmails ? this.tmpConsentedToEmails : null,
        "consentedToTexts": this.consentedToTexts ? this.consentedToTexts : null,

        "tmpConsentedToTexts": this.tmpConsentedToTexts ? this.tmpConsentedToTexts : null,
        "twoFactorAuthType": this.twoFactorAuthType ? this.twoFactorAuthType : null,
        "tmpTwoFactorAuthType": this.tmpTwoFactorAuthType ? this.tmpTwoFactorAuthType  : null,
        "consentedToTermsAndConditions": this.consentedToTermsAndConditions ? this.consentedToTermsAndConditions : null,
        "resetMethod": this.resetMethod ? this.resetMethod : null,
        "existingPassword": this.existingPassword ? this.existingPassword : null,
        "verificationCode": this.verificationCode ? this.verificationCode: null,

        "usdAccountValue": this.usdAccountValue ? this.usdAccountValue : null,
        "butterBucksAccountValue": this.butterBucksAccountValue ? this.butterBucksAccountValue : null,
        "usdEscrowAccountValue": this.usdEscrowAccountValue ? this.usdEscrowAccountValue  : null,
        "butterBucksEscrowAccountValue": this.butterBucksEscrowAccountValue ? this.butterBucksEscrowAccountValue : null,
        "authAppDataUrl": this.authAppDataUrl ? this.authAppDataUrl : null,
        "userAgent": this.userAgent ? this.userAgent : null,
        "latitude": this.latitude ? this.latitude: null,

        "longitude": this.longitude ? this.longitude : null,
        "accuracy": this.accuracy ? this.accuracy : null,
        "newPassword": this.newPassword ? this.newPassword  : null,
        "confirmNewPassword": this.confirmNewPassword ? this.confirmNewPassword : null,
        "sessionCode": this.sessionCode ? this.sessionCode : null,
        "qrCode": this.qrCode ? this.qrCode : null,
        "didUserLoad": this.didUserLoad ? this.didUserLoad: null,

        "confirmPassword": this.confirmPassword ? this.confirmPassword : null,
        "isEmailAddressVerified": this.isEmailAddressVerified ? this.isEmailAddressVerified : null,
        "isPhoneNumberVerified": this.isPhoneNumberVerified ? this.isPhoneNumberVerified  : null,
        "isAuthAppVerified": this.isAuthAppVerified ? this.isAuthAppVerified : null,
        "cantSeeQrCode": this.cantSeeQrCode ? this.cantSeeQrCode : null,
        "createBettingEventName": this.createBettingEventName ? this.createBettingEventName : null,
        "createBettingEventDateTime": this.createBettingEventDateTime ? this.createBettingEventDateTime: null,

        "applicationRoles": this.applicationRoles ? this.applicationRoles : null,
        "endDateTime": this.endDateTime ? this.endDateTime : null,
        "userId": this.userId ? this.userId.toString() : null,
        "cartItems": this.cartItems ? this.cartItems : null,
        "cart": this.cart ? this.cart : null,
        "cartItemRelationship": this.cartItemRelationship ? this.cartItemRelationship : null
    }
  }
*/
}

export const userSlice = createSlice({
  name: 'user',
  initialState: new UserState(),
  reducers: {
    updateUsername: (state, action) => {
      return {
        ...state,
        username: action.payload
      }
    },
    updateTmpUsername: (state, action) => {
      return {
        ...state,
        tmpUsername: action.payload
      }
    },
    updatePassword: (state, action) => {
      return {
        ...state,
        password: action.payload
      }
    },
    updateEmailAddress: (state, action) => {
        return {
          ...state,
          emailAddress: action.payload
        }
    },
    updateTmpEmailAddress: (state, action) => {
      return {
        ...state,
        tmpEmailAddress: action.payload
      }
    },
    updatePhoneNumber: (state, action) => {
        return {
          ...state,
          phoneNumber: action.payload
        }
    },
    updateTmpPhoneNumber: (state, action) => {
      return {
        ...state,
        tmpPhoneNumber: action.payload
      }
    },
    updateFirstName: (state, action) => {
        return {
          ...state,
          firstName: action.payload
        }
    },
    updateMiddleName: (state, action) => {
        return {
          ...state,
          middleName: action.payload
        }
    },
    updateLastName: (state, action) => {
        return {
          ...state,
          lastName: action.payload
        }
    },
    updateDateOfBirth: (state, action) => {
        return {
          ...state,
          dateOfBirth: action.payload
        }
    },
    updateConsentedToEmails: (state, action) => {
        return {
          ...state,
          consentedToEmails: Boolean(action.payload)
        }
    },
    updateTmpConsentedToEmails: (state, action) => {
      return {
        ...state,
        tmpConsentedToEmails: Boolean(action.payload)
      }
    },
    updateConsentedToTexts: (state, action) => {
        return {
          ...state,
          consentedToTexts: Boolean(action.payload)
        }
    },
    updateTmpConsentedToTexts: (state, action) => {
      return {
        ...state,
        tmpConsentedToTexts: Boolean(action.payload)
      }
    },
    updateTwoFactorAuthType: (state, action) => {
        return {
          ...state,
          twoFactorAuthType: action.payload
        }
    },
    updateTmpTwoFactorAuthType: (state, action) => {
      return {
        ...state,
        tmpTwoFactorAuthType: action.payload
      }
  },
    updateConsentedToTermsAndConditions: (state, action) => {
        return {
          ...state,
          consentedToTermsAndConditions: Boolean(action.payload)
        }
    },
    updateResetMethod: (state, action) => {
        return {
          ...state,
          resetMethod: action.payload
        }
    },
    updateVerificationCode: (state, action) => {
        return {
          ...state,
          verificationCode: action.payload
        }
    },
    updateExistingPassword: (state, action) => {
        return {
          ...state,
          existingPassword: action.payload
        }
    },
    updateUsdAccountValue: (state, action) => {
        return {
          ...state,
          usdAccountValue: action.payload
        }
    },
    updateAuthAppDataUrl: (state, action) => {
        return {
          ...state,
          authAppDataUrl: action.payload
        }
    },
    updateUserAgent: (state, action) => {
      return {
        ...state,
        userAgent: action.payload
      }
    },
    updateLatitude: (state, action) => {
      return {
        ...state,
        latitude: action.payload
      }
    },
    updateLongitude: (state, action) => {
      return {
        ...state,
        longitude: action.payload
      }
    },
    updateAccuracy: (state, action) => {
      return {
        ...state,
        accuracy: action.payload
      }
    },
    updateNewPassword: (state, action) => {
      return {
        ...state,
        newPassword: action.payload
      }
    },
    updateConfirmNewPassword: (state, action) => {
      return {
        ...state,
        confirmNewPassword: action.payload
      }
    },
    updateSessionCode: (state, action) => {
      return {
        ...state,
        sessionCode: action.payload
      }
    },
    updateQrCode: (state, action) => {
      return {
        ...state,
        qrCode: action.payload
      }
    },
    updateDidUserLoad: (state, action) => {
      return {
        ...state,
        didUserLoad: action.payload
      }
    },
    updateButterBucksAccountValue: (state, action) => {
      return {
        ...state,
        butterBucksAccountValue: action.payload
      }
    },
    resetUserReducer: (state, action) => {
      state = new UserState();
      return {
        ...state
      }
    },
    updateConfirmPassword: (state, action) => {
      return {
        ...state,
        confirmPassword: action.payload
      }
    },
    updateUsdEscrowAccountValue: (state, action) => {
      return {
        ...state,
        usdEscrowAccountValue: action.payload
      }
    },
    updateButterBucksEscrowAccountValue: (state, action) => {
      return {
        ...state,
        butterBucksEscrowAccountValue: action.payload
      }
    },
    updateIsEmailAddressVerified: (state, action) => {
      return {
        ...state,
        isEmailAddressVerified: action.payload
      }
    },
    updateIsPhoneNumberVerified: (state, action) => {
      return {
        ...state,
        isPhoneNumberVerified: action.payload
      }
    },
    updateIsAuthAppVerified: (state, action) => {
      return {
        ...state,
        isAuthAppVerified: action.payload
      }
    },
    updateCantSeeQrCode: (state, action) => {
      return {
        ...state,
        cantSeeQrCode: action.payload
      }
    },
    updateCreateBettingEventName: (state, action) => {
      return {
        ...state,
        createBettingEventName: action.payload
      }
    },
    updateCreateBettingEventDateTime: (state, action) => {
      return {
        ...state,
        createBettingEventDateTime: action.payload
      }
    },
    updateApplicationRoles: (state, action) => {
      return {
        ...state,
        applicationRoles: action.payload
      }
    },
    updateEndDateTime: (state, action) => {
      return {
        ...state,
        endDateTime: action.payload
      }
    },
    updateUserId: (state, action) => {
      return {
        ...state,
        userId: action.payload
      }
    },
    updateCartItems: (state, action) => {
      return {
        ...state,
        cartItems: action.payload
      }
    },
    updateCart: (state, action) => {
      return {
        ...state,
        cart: action.payload
      }
    },
    updateCartItemRelationship: (state, action) => {
      return {
        ...state,
        cartItemRelationship: action.payload
      }
    },
  }
})

// Action creators are generated for each case reducer function
export const { updateUsername, updateTmpUsername, updatePassword, updateEmailAddress, updateTmpEmailAddress,
    updatePhoneNumber, updateTmpPhoneNumber, updateFirstName, updateMiddleName, updateLastName,
    updateDateOfBirth, updateConsentedToEmails, updateTmpConsentedToEmails,
    updateConsentedToTexts, updateTmpConsentedToTexts, updateTwoFactorAuthType,
    updateResetMethod, updateConsentedToTermsAndConditions,
    updateExistingPassword, updateVerificationCode, updateUsdAccountValue,
    updateAuthAppDataUrl, updateUserAgent, updateLatitude, updateLongitude, updateAccuracy,
    updateNewPassword, updateConfirmNewPassword, updateSessionCode, updateQrCode,
    updateDidUserLoad, updateButterBucksAccountValue, resetUserReducer, updateConfirmPassword,
    updateUsdEscrowAccountValue, updateButterBucksEscrowAccountValue, updateTmpTwoFactorAuthType,
    updateIsEmailAddressVerified, updateCantSeeQrCode, updateIsPhoneNumberVerified, 
    updateIsAuthAppVerified, updateCreateBettingEventName, updateCreateBettingEventDateTime,
    updateApplicationRoles, updateEndDateTime, updateUserId, updateCartItems, updateCart,
    updateCartItemRelationship
} = userSlice.actions

export default userSlice.reducer