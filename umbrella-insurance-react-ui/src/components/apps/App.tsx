import { useEffect } from 'react';
import '../../css/apps/App.css';
import Announcements from '../announcements/Announcements';
import GameTable from '../bets/GameTable';
import PlaceBet from '../bets/PlaceBet';
import Faq from '../faqs/Faq';
import ChangePassword from '../users/ChangePassword';
import ConfirmUserEmail from '../users/ConfirmUserEmail';
import ConfirmUserPhone from '../users/ConfirmUserPhone';
import CreateUser from '../users/CreateUser';
import DeleteUser from '../users/DeleteUser';
import UpdateUser from '../users/UpdateUser';
import ResetPassword from '../users/ResetPassword';
import SignIn from '../users/SignIn';
import {
  Route,
  Routes
} from "react-router-dom";
import HomeLayout from '../layouts/HomeLayout';
import { useDispatch, useSelector } from 'react-redux';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { updatePassword, updateUsername } from '../../redux/reducers/UserReducer';
import VerifyAuthApp from '../users/VerifyAuthApp';
import { updateEnv, updateDomain, updateIsWsConnected } from '../../redux/reducers/EnvironmentReducer';
import { RootState } from '../../redux/store/Store';
import { WebSocketBackend } from '../../websockets/WebSocket';
import EnterPassword from '../users/EnterPassword';
import { callReadUnitsRestEndpoint } from '../../endpoints/rest/units/v1/UnitRestEndpoints';
import { Unit } from '../../models/units/v1/Unit';
import { updateFrontendAppVersion, updateUnits } from '../../redux/reducers/AppReducer';
import { toObject } from '../../utils/Parser';
import BetHistory from '../bets/BetHistory';
import ChangeTwoFactorAuth from '../users/ChangeTwoFactorAuth';
import CreateBettingEvent from '../events/CreateBettingEvent';
import CreateFootballEvent from '../events/CreateFootballEvent';
import CreateTeam from '../teams/CreateTeam';
import path from 'path';
import Health from '../health/Health';
import CreateAnnouncement from '../announcements/CreateAnnouncement';
import EditAnnouncement from '../announcements/EditAnnouncement';
import CreateFaq from '../faqs/CreateFaq';
import EditFaq from '../faqs/EditFaq';
import Reviews from '../reviews/Reviews';
import EditReview from '../reviews/EditReview';
import CreateReview from '../reviews/CreateReview';
import Checkout from '../checkout/Checkout';
import Cart from '../cart/Cart';
import Items from '../items/Items';
import AboutUs from '../aboutUs/AboutUs';
import Peril from '../Perils/Peril';

function App() {

  const wsProtocol: string = useSelector((state: RootState) => {
    return state.environment.wsProtocol;
  });
  const wsDomain: string = useSelector((state: RootState) => {
    return state.environment.wsDomain;
  });
  const isWsConnected: boolean = useSelector((state: RootState) => {
    return state.environment.isWsConnected;
  })
  const env: string = useSelector((state: RootState) => {
    return state.environment.env;
  })
  const domain: string = useSelector((state: RootState) => {
    return state.environment.domain;
  });
  const units: Unit[]| undefined = useSelector((state: RootState) => {
    return state.app.units;
  });
  const dispatch = useDispatch();
  async function getUnits() {
    if(!units) {
      let newUnits:Unit[] | undefined = await callReadUnitsRestEndpoint(env, domain);
      if (newUnits) {
          dispatch(updateUnits(toObject(newUnits)));
      }
    }

  }

  if(!isWsConnected) {
    let websocket = new WebSocketBackend();
    websocket.connect(env, wsDomain, wsProtocol);
    dispatch(updateIsWsConnected(true));
  }

  useEffect(()=> {
    dispatch(updateFrontendAppVersion(process.env.REACT_APP_VERSION));
    if(!units) {
      getUnits();
    }
  },[]);
  return (
    <Routes>
      <Route path="/" element={<HomeLayout />}>
        <Route index element={<GameTable />} />
        <Route path="placeBet" element={<PlaceBet />} />
        <Route path="createUser" element={<CreateUser />} />
        <Route path="deleteUser" element={<DeleteUser />} />
        <Route path="updateUser" element={<UpdateUser />} />
        <Route path="sendResetPassword" element={<ResetPassword />} />
        <Route path="faq" element={<Faq />} />
        <Route path="announcements" element={<Announcements />} />
        <Route path="signIn" element={<SignIn />} />
        <Route path="gameTable" element={<GameTable />} />
        <Route path="betHistory" element={<BetHistory />} />
        <Route path="confirmUserEmail" element={<ConfirmUserEmail />} />
        <Route path="confirmUserPhone" element={<ConfirmUserPhone />} />
        <Route path="changePassword" element={<ChangePassword />} />
        <Route path="verifyAuthApp" element={<VerifyAuthApp />} />
        <Route path="enterPassword" element={<EnterPassword />} />
        <Route path="createBettingEvent" element={<CreateBettingEvent />} />
        <Route path="changeTwoFactorAuthType" element={<ChangeTwoFactorAuth />} />
        <Route path="createFootballEvent" element={<CreateFootballEvent />} />
        <Route path="createTeam" element={<CreateTeam />} />
        <Route path="actuator/health" element={<Health />} />
        <Route path="createAnnouncement" element={<CreateAnnouncement />} />
        <Route path="editAnnouncement" element={<EditAnnouncement />} />
        <Route path="createFaq" element={<CreateFaq />} />
        <Route path="editFaq" element={<EditFaq />} />
        <Route path="createReview" element={<CreateReview />} />
        <Route path="editReview" element={<EditReview />} />
        <Route path="reviews" element={<Reviews />} />
        <Route path="checkout" element={<Checkout />} />
        <Route path="cart" element={<Cart />} />
        <Route path="items" element={<Items />} />
        <Route path="aboutUs" element={<AboutUs />} />
        <Route path="perils" element={<Peril />} />
        {/* Using path="*"" means "match anything", so this route
              acts like a catch-all for URLs that we don't have explicit
              routes for. */}
        <Route path="*" element={<GameTable />} />
      </Route>
    </Routes>)
}

export default App;
