import { createSlice } from '@reduxjs/toolkit'
import { AccountBalanceTransaction } from '../../models/users/accountBalances/accountBalanceTransactions/v1/AccountBalanceTransaction';

export class AccountBalanceTransactionState {
  accountBalanceTransactions?: AccountBalanceTransaction[]
  constructor() {
  }
}

export const accountBalanceTransactionSlice = createSlice({
  name: 'accountBalanceTransaction',
  initialState: new AccountBalanceTransactionState(),
  reducers: {
    updateAccountBalanceTransactions: (state, action) => {
      return {
        ...state,
        accountBalanceTransactions: action.payload
      }
    },
  }
})

// Action creators are generated for each case reducer function
export const { updateAccountBalanceTransactions, 
 } = accountBalanceTransactionSlice.actions

export default accountBalanceTransactionSlice.reducer