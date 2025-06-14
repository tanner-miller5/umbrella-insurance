import { configureStore, combineSlices } from "@reduxjs/toolkit";
import counterReducer, { counterSlice, CounterState } from "../reducers/CounterReducer";
import userReducer, { userSlice, UserState } from "../reducers/UserReducer";
import LoadingReducer, { loadingSlice, LoadingState } from "../reducers/LoadingReducer";
import EnvironmentReducer, { environmentSlice, EnvironmentState } from "../reducers/EnvironmentReducer";
import { appSlice, AppState } from "../reducers/AppReducer";
import { statSlice, StatState } from "../reducers/StatReducer";
import { announcementSlice, AnnouncementState } from "../reducers/AnnouncementReducer";
import { faqSlice, FaqState } from "../reducers/FaqReducer";
import { reviewSlice, ReviewState } from "../reducers/ReviewsReducer";
import { policySlice, PolicyState } from "../reducers/PolicyReducer";
import { geographySlice, GeographyState } from "../reducers/GeographyReducer";

export const rootReducer = combineSlices(counterSlice, userSlice, loadingSlice, 
    environmentSlice, appSlice, statSlice, announcementSlice, faqSlice, reviewSlice,
    policySlice, geographySlice);

export class RootState {
    counter: CounterState;
    user: UserState;
    loading: LoadingState;
    environment: EnvironmentState;
    app: AppState;
    stat: StatState;
    announcement: AnnouncementState;
    faq: FaqState;
    review: ReviewState;
    policy: PolicyState;
    geography: GeographyState;
    constructor() {
        this.counter = new CounterState();
        this.user = new UserState();
        this.loading = new LoadingState();
        this.environment = new EnvironmentState();
        this.app = new AppState();
        this.stat = new StatState();
        this.announcement = new AnnouncementState();
        this.faq = new FaqState();
        this.review = new ReviewState();
        this.policy = new PolicyState();
        this.geography = new GeographyState();
    }
}

export const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => {
        return getDefaultMiddleware({
            serializableCheck: false,
        })
    }
});