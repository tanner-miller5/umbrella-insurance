import { createSlice } from '@reduxjs/toolkit'
import { Announcement } from '../../models/announcements/v1/Announcement'

export class AnnouncementState {
  announcement?: Announcement;
  constructor() {
    this.announcement = new Announcement();
  }
}

export const announcementSlice = createSlice({
  name: 'announcement',
  initialState: new AnnouncementState(),
  reducers: {
    updateAnnouncement: (state, action) => {
      return {
        ...state,
        announcement: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateAnnouncement } = announcementSlice.actions

export default announcementSlice.reducer