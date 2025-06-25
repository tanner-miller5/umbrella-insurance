import '../../css/headers/header.css';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { updateErrorMessage, updateIsErrorOpen, updateIsHamburgerMenuOpen, updateIsWarningOpen, updateItems, updateReviews, updateWarningMessage } from '../../redux/reducers/AppReducer';
import Error  from '../errors/Error';
import { SignOutRequest } from '../../endpoints/soa/user/v1/requests/SignOutRequest';
import { SignOutResponse } from '../../endpoints/soa/user/v1/responses/SignOutResponse';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { resetUserReducer, updateDidUserLoad } from '../../redux/reducers/UserReducer';
import { SendEmailVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendEmailVerificationRequest';
import { SendPhoneVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendPhoneVerificationRequest';
import { callSignOutRestEndpoints, sendEmailVerification, sendPhoneVerification } from '../../endpoints/soa/user/v1/UserSoaEndpoints';
import { ApplicationRole } from '../../models/applicationRoles/v1/ApplicationRole';
import { ApplicationRoleEnum } from '../../models/applicationRoles/v1/ApplicationRoleEnum';
import { useEffect } from 'react';
import Warning from '../warnings/Warning';
import PrivacyPolicy from '../userAgreements/PrivacyPolicy';
import TermsAndConditions from '../userAgreements/TermsAndConditions';
import { Review } from '../../models/reviews/v1/Review';
import { callReadReviewRestEndpointsByUserIdAndFrontendAppVersionAndBackendAppVersion } from '../../endpoints/rest/reviews/v1/ReviewRestEndpoints';
import { toObject } from '../../utils/Parser';
import { User } from '../../models/users/v1/User';
import { callReadItemRestEndpoints } from '../../endpoints/rest/items/v1/ItemRestEndpoints';
import Loading from '../loadings/Loading';

export function toUTCString(date:Date) : string {
    let year = date.getUTCFullYear() + "";
    let month = (date.getUTCMonth() + 1) + "";
    if (month.length < 2) {
        month = "0" + month;
    }
    let day = date.getUTCDate() + "";
    if (day.length < 2) {
        day = "0" + day;
    }
    let hours = date.getUTCHours() + "";
    if (hours.length < 2) {
        hours = "0" + hours;
    }
    let minutes = date.getUTCMinutes() + "";
    if (minutes.length < 2) {
        minutes = "0" + minutes;
    }
    let seconds = date.getUTCSeconds() + "";
    if (seconds.length < 2) {
        seconds = "0" + seconds;
    }
    let milliseconds = date.getUTCMilliseconds() + "";
    if (milliseconds.length < 2) {
        milliseconds = "0" + milliseconds;
    }
    let formattedCurrentTime = year + "-" + month + "-" +
    day + " " + hours + ":" + minutes + ":" +
    seconds + "." + milliseconds;
    return formattedCurrentTime

}

export function toUTCStringWithTimezone(date:Date) : string {
    let year = date.getUTCFullYear() + "";
    let month = (date.getUTCMonth() + 1) + "";
    if (month.length < 2) {
        month = "0" + month;
    }
    let day = date.getUTCDate() + "";
    if (day.length < 2) {
        day = "0" + day;
    }
    let hours = date.getUTCHours() + "";
    if (hours.length < 2) {
        hours = "0" + hours;
    }
    let minutes = date.getUTCMinutes() + "";
    if (minutes.length < 2) {
        minutes = "0" + minutes;
    }
    let seconds = date.getUTCSeconds() + "";
    if (seconds.length < 2) {
        seconds = "0" + seconds;
    }
    let milliseconds = date.getUTCMilliseconds() + "";
    if (milliseconds.length < 2) {
        milliseconds = "0" + milliseconds;
    }
    let formattedCurrentTime = year + "-" + month + "-" +
    day + "T" + hours + ":" + minutes + ":" +
    seconds + "." + milliseconds + "Z";
    return formattedCurrentTime

}

export default function Header() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const butterBucksAccountValue: number = useSelector((state: RootState)=>{
        return state.user.butterBucksAccountValue;
    }) || 0;
    const usdAccountValue: number = useSelector((state: RootState)=>{
        return state.user.usdAccountValue;
    }) || 0;
    const didUserLoad: boolean = useSelector((state: RootState)=>{
        return state.user.didUserLoad;
    });
    const currentPage: string = useSelector((state: RootState)=>{
        return state.app.currentPage;
    });
    let defaultButtonStyle = "headerButton";
    let activeButtonStyle = "liActive headerButton";

    let faqClassName = defaultButtonStyle;
    let announcementClassName = defaultButtonStyle;
    let signInClassName = defaultButtonStyle;
    let homeClassName = defaultButtonStyle;
    let createUserClassName = defaultButtonStyle;
    let resetPasswordClassName = defaultButtonStyle;
    let changePasswordClassName = defaultButtonStyle;
    let changeTwoFactorAuthTypeClassName = defaultButtonStyle;
    let updateUserClassName = defaultButtonStyle;
    let confirmUserEmailClassName = defaultButtonStyle;
    let confirmUserPhoneClassName = defaultButtonStyle;
    let createBettingEventClassName = defaultButtonStyle;
    let createFootballEventClassName = defaultButtonStyle;
    let createTeamClassName = defaultButtonStyle;
    let createAnnouncementClassName = defaultButtonStyle;
    let editAnnouncementClassName = defaultButtonStyle;
    let deleteUserClassName = defaultButtonStyle;
    let createFaqClassName = defaultButtonStyle;
    let editFaqClassName = defaultButtonStyle;
    let reviewsClassName = defaultButtonStyle;
    let createReviewClassName = defaultButtonStyle;
    let editReviewClassName = defaultButtonStyle;
    let itemsClassName = defaultButtonStyle;
    let cartClassName = defaultButtonStyle;
    let checkoutClassName = defaultButtonStyle;
    let aboutUsClassName = defaultButtonStyle;
    let perilTypesClassName = defaultButtonStyle;
    let createPolicyClassName = defaultButtonStyle;
    if (currentPage === '/faq') {
        faqClassName = activeButtonStyle;
    } else if (currentPage === '/announcements') {
        announcementClassName = activeButtonStyle;
    } else if (currentPage === '/signIn') {
        signInClassName = activeButtonStyle;
    } else if (currentPage === '/gameTable') {
        homeClassName = activeButtonStyle;
    } else if (currentPage === '/') {
        homeClassName = activeButtonStyle;
    } else if (currentPage === '/createUser') {
        createUserClassName = activeButtonStyle;
    } else if (currentPage === '/sendResetPassword') {
        resetPasswordClassName = activeButtonStyle;
    } else if (currentPage === '/changePassword') {
        changePasswordClassName = activeButtonStyle; 
    } else if (currentPage === '/changeTwoFactorAuthType') {
        changeTwoFactorAuthTypeClassName = activeButtonStyle; 
    } else if (currentPage === '/updateUser') {
        updateUserClassName = activeButtonStyle; 
    } else if (currentPage === '/confirmUserPhone') {
        confirmUserPhoneClassName = activeButtonStyle; 
    } else if (currentPage === '/confirmUserEmail') {
        confirmUserEmailClassName = activeButtonStyle; 
    } else if (currentPage === '/createBettingEvent') {
        createBettingEventClassName = activeButtonStyle;
    } else if (currentPage === '/createFootballEvent') {
        createFootballEventClassName = activeButtonStyle;
    } else if (currentPage === '/createTeam') {
        createTeamClassName = activeButtonStyle;
    } else if (currentPage === '/createAnnouncement') {
        createAnnouncementClassName = activeButtonStyle;
    } else if (currentPage === '/editAnnouncement') {
        editAnnouncementClassName = activeButtonStyle;
    } else if (currentPage === '/deleteUser') {
        deleteUserClassName = activeButtonStyle;
    } else if (currentPage === '/createFaq') {
        createFaqClassName = activeButtonStyle;
    } else if (currentPage === '/editFaq') {
        editFaqClassName = activeButtonStyle;
    } else if (currentPage === '/createReview') {
        createReviewClassName = activeButtonStyle;
    } else if (currentPage === '/editReview') {
        editReviewClassName = activeButtonStyle;
    } else if (currentPage === '/reviews') {
        reviewsClassName = activeButtonStyle;
    } else if (currentPage === '/items') {
        itemsClassName = activeButtonStyle;
    } else if (currentPage === '/cart') {
        cartClassName = activeButtonStyle;
    } else if (currentPage === '/checkout') {
        checkoutClassName = activeButtonStyle;
    } else if (currentPage === '/aboutUs') {
        aboutUsClassName = activeButtonStyle;
    } else if (currentPage === '/perilTypes') {
        perilTypesClassName = activeButtonStyle;
    } else if (currentPage === '/createPolicy') {
        createPolicyClassName = activeButtonStyle;
    }
    const env = useSelector((state:RootState) => {
        return state.environment.env;
    })
    const domain = useSelector((state:RootState) => {
        return state.environment.domain;
    })
    const isHamburgerMenuOpen: boolean = useSelector((state:RootState) => {
        return state.app.isHamburgerMenuOpen;
    })
    let errorMessage: string = useSelector((state: RootState)=>{
        return state.app.errorMessage;
    }) || "";
    let isErrorOpen: boolean = useSelector((state: RootState)=>{
        return state.app.isErrorOpen;
    });
    let warningMessage: string = useSelector((state: RootState)=>{
        return state.app.warningMessage;
    }) || "";
    let isWarningOpen: boolean = useSelector((state: RootState)=>{
        return state.app.isWarningOpen;
    });
    let isPrivacyPolicyOpen: boolean = useSelector((state: RootState)=>{
        return state.app.isPrivacyPolicyOpen;
    });
    let isTermsAndConditionsOpen: boolean = useSelector((state: RootState)=>{
        return state.app.isTermsAndConditionsOpen;
    });
    let isLoadingOpen: boolean = useSelector((state: RootState)=>{
        return state.loading.value;
    });
    let username: string | undefined = useSelector((state: RootState)=>{
        return state.user.username;
    });
    let isEmailAddressVerified: boolean | undefined = useSelector((state: RootState)=>{
        return state.user.isEmailAddressVerified;
    });
    let isPhoneNumberVerified: boolean | undefined = useSelector((state: RootState)=>{
        return state.user.isPhoneNumberVerified;
    });
    let sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    let applicationRoles: ApplicationRole[] | undefined = useSelector((state: RootState)=>{
        return state.user.applicationRoles;
    });

    let isCustomer: boolean = false;
    let isCustomerSupport: boolean = false;
    let isExecutive: boolean = false;
    let isManagement: boolean = false;
    let isTechSupport: boolean = false;
    if(applicationRoles?.length) {
        for(let i = 0; i < applicationRoles?.length; i++) {
            switch(applicationRoles[i].applicationRoleName) {
                case ApplicationRoleEnum.customer.toString():
                    isCustomer = true
                    break;
                case ApplicationRoleEnum.customer_support.toString():
                    isCustomerSupport = true;
                    break;
                case ApplicationRoleEnum.executive.toString():
                    isExecutive = true;
                    break;
                case ApplicationRoleEnum.management.toString():
                    isManagement = true;
                    break;
                case ApplicationRoleEnum.tech_support.toString():
                    isTechSupport = true;
                    break;
                default:

            }
        }
    }

    let userId: number = useSelector((state: RootState)=>{
        return state.user.userId;
    }) || 0;

    let reviews: Review[] = useSelector((state: RootState) => {
        return state.app.reviews;
    });

    let frontendAppVersion: string = useSelector((state: RootState)=>{
        return state.app.frontendAppVersion;
    }) || "";

    let backendAppVersion: string = useSelector((state: RootState)=>{
        return state.app.backendAppVersion;
    }) || "";

    let showEditReview: boolean = false;
    for(let i = 0; i < reviews.length; i++) {
        if(reviews[i].user?.id === userId) {
            showEditReview = true;
        }
    }

    useEffect(
        function() {
            async function getReviews() {
                const reviews = await callReadReviewRestEndpointsByUserIdAndFrontendAppVersionAndBackendAppVersion(
                    userId || 0, frontendAppVersion, backendAppVersion, env, domain, sessionCode);
                if(reviews) {
                    dispatch(updateReviews(toObject(reviews)));
                }
            };
            async function getItems() {
                const items = await callReadItemRestEndpoints(env, domain, sessionCode);
                dispatch(updateItems(items));
            }
            if(userId !== 0) {
                getReviews();
                getItems();
            }
        }, [userId]
    );

    function hamburgerButtonClicked() {
        dispatch(updateIsHamburgerMenuOpen(!isHamburgerMenuOpen));
    }
    function closeNavMenu() {
        dispatch(updateIsHamburgerMenuOpen(false));
    }
    function onClickHome() {
        closeNavMenu();
        navigate("/");
    }
    function onClickFaq() {
        closeNavMenu();
        navigate("/faq");
    }
    function onClickCreateFootballEvent() {
        closeNavMenu();
        navigate("/createFootballEvent");
    }
    function onClickAnnouncements() {
        closeNavMenu();
        navigate("/announcements");
    }
    function onClickUpdateUser() {
        closeNavMenu();
        navigate("/updateUser");
    }
    function onClickCreateUser() {
        closeNavMenu();
        navigate("/createUser");
    }
    function onClickResetPassword() {
        closeNavMenu();
        navigate("/sendResetPassword")
    }
    function onClickChangePassword() {
        closeNavMenu();
        navigate("/changePassword")
    }
    function onClickDeleteUser() {
        closeNavMenu();
        navigate("/deleteUser")
    }
    function onClickChangeTwoFactorAuthType() {
        closeNavMenu();
        navigate("/changeTwoFactorAuthType")
    }
    function onClickVerifyEmail() {
        let sendEmailVerificationRequest: SendEmailVerificationRequest = new SendEmailVerificationRequest();
                sendEmailVerificationRequest.username = username;
                sendEmailVerification(sendEmailVerificationRequest, env, domain);
        navigate("/confirmUserEmail")
        closeNavMenu();
    }
    function onClickVerifyPhoneNumber() {
        let sentPhoneVerificationRequest: SendPhoneVerificationRequest = new SendPhoneVerificationRequest();
                sentPhoneVerificationRequest.username = username;
        sendPhoneVerification(sentPhoneVerificationRequest, env, domain);
        closeNavMenu();
        navigate("/confirmUserPhone")
    }
    async function onClickSignOut() {
        dispatch(updateLoadingState(true));
        let signOutRequest: SignOutRequest = new SignOutRequest();
        signOutRequest.username = username;
        let signOutResponse: SignOutResponse | undefined = await callSignOutRestEndpoints(
            sessionCode, signOutRequest, env, domain);
        if(signOutResponse?.isSuccess) {
        } else {
            console.error('Unable to sign out');
        }
        dispatch(updateLoadingState(false));
        dispatch(resetUserReducer(""));
        closeNavMenu();  
        navigate("/");
    }
    function onClickSignIn() {
        closeNavMenu();
        navigate("/signIn");
    }
    function onClickCreateBettingEvent() {
        closeNavMenu();
        navigate("/createBettingEvent");
    }
    function onClickCreateTeam() {
        closeNavMenu();
        navigate("/createTeam");
    }
    function onClickCreateAnnouncement() {
        closeNavMenu();
        navigate("/createAnnouncement");
    }
    function onClickEditAnnouncement() {
        closeNavMenu();
        navigate("/editAnnouncement");
    }
    function onClickCreateFaq() {
        closeNavMenu();
        navigate("/createFaq");
    }
    function onClickEditFaq() {
        closeNavMenu();
        navigate("/editFaq");
    }
    function onClickCreateReview() {
        closeNavMenu();
        navigate("/createReview");
    }
    function onClickEditReview() {
        closeNavMenu();
        navigate("/editReview");
    }
    function onClickReviews() {
        closeNavMenu();
        navigate("/reviews");
    }
    function onClickItems() {
        closeNavMenu();
        navigate("/items");
    }
    function onClickCart() {
        closeNavMenu();
        navigate("/cart");
    }
    function onClickCheckout() {
        closeNavMenu();
        navigate("/checkout");
    }
    function onClickLogo() {
        closeNavMenu();
        navigate("/");
    }
    function onClickAboutUs() {
        closeNavMenu();
        navigate("/aboutUs");
    }
    function onClickPerilTypes() {
        closeNavMenu();
        navigate("/perilTypes");
    }
    function onClickCreatePolicy() {
        closeNavMenu();
        navigate("/createPolicy");
    }
    let endDateTime: string | undefined = useSelector((state: RootState)=>{
        return state.user.endDateTime;
    });

    useEffect(()=>{
        const intervalId = setInterval(()=>{
            let currentTime = new Date(Date.now());
            let formattedCurrentTime = toUTCString(currentTime);
            if(endDateTime === undefined) {
                endDateTime = "9999";
            }
            if(endDateTime < formattedCurrentTime && didUserLoad) {
                onClickSignOut();
                dispatch(updateWarningMessage("Signed out!"));
                dispatch(updateIsWarningOpen(true));
            }
        }, 30000);
        // Cleanup function to clear the interval
        return () => clearInterval(intervalId);
    },[endDateTime]);

    if(isLoadingOpen) {
        return (
            <>
                <div className="title" >
                    <div onClick={onClickLogo}>
                        <img src="logo-03.png" className="titleImage"></img>
                    </div>
                </div>
                <Loading />
            </>

        );
    }
    return (            
    <>
        <Error message={errorMessage} isOpen={isErrorOpen}></Error>
        <Warning message={warningMessage} isOpen={isWarningOpen}></Warning>
        <PrivacyPolicy isOpen={isPrivacyPolicyOpen}></PrivacyPolicy>
        <TermsAndConditions isOpen={isTermsAndConditionsOpen}></TermsAndConditions>
        <button className="hamburger" onClick={hamburgerButtonClicked} >☰</button>
        <div className="title" >
            <div onClick={onClickLogo}>
                <img src="logo-03.png" className="titleImage"></img>
            </div>
        </div>
        <ul className={isHamburgerMenuOpen ? 'show' : "" }>
            {didUserLoad && <li className='headerButton'>{`Account Value: $${usdAccountValue.toFixed(2)}`}</li>}
            {didUserLoad && <li className='headerButton'>{`Butter Bucks: ${butterBucksAccountValue.toFixed(2)}`}</li>}
            <li onClick={onClickHome} className={homeClassName}>Home</li>
            <li onClick={onClickFaq} className={faqClassName}>FAQ</li>
            <li onClick={onClickAnnouncements} className={announcementClassName}>Announcements</li>
            {didUserLoad && <li onClick={onClickUpdateUser} className={updateUserClassName}>Update Account</li>}
            {didUserLoad && <li onClick={onClickSignOut} className='headerButton'>Sign Out</li>}
            {!didUserLoad && <li onClick={onClickSignIn} className={signInClassName}>Login</li>}
            {!didUserLoad && <li onClick={onClickCreateUser} className={createUserClassName}>Create User</li>}
            {!didUserLoad && <li onClick={onClickResetPassword} className={resetPasswordClassName}>Reset Password</li>}
            {didUserLoad && <li onClick={onClickChangePassword} className={changePasswordClassName}>Change Password</li>}
            {false && didUserLoad && <li onClick={onClickChangeTwoFactorAuthType} className={changeTwoFactorAuthTypeClassName}>Change Two Factor Auth</li>}
            {false && didUserLoad && !isEmailAddressVerified && <li onClick={onClickVerifyEmail} className={confirmUserEmailClassName}>Confirm Email Address</li>}
            {false && didUserLoad && !isPhoneNumberVerified && <li onClick={onClickVerifyPhoneNumber} className={confirmUserPhoneClassName}>Confirm Phone Number</li>}
            {isCustomerSupport && <li onClick={onClickCreateBettingEvent} className={createBettingEventClassName}>Create Betting Event</li>}
            {didUserLoad && <li onClick={onClickDeleteUser} className={deleteUserClassName}>Delete User</li>}
            {isCustomerSupport && <li onClick={onClickCreateFootballEvent} className={createFootballEventClassName}>Create Football Event</li>}
            {isCustomerSupport && <li onClick={onClickCreateTeam} className={createTeamClassName}>Create Team</li>}
            {isCustomerSupport && didUserLoad && <li onClick={onClickCreateAnnouncement} className={createAnnouncementClassName}>Create Announcement</li>}
            {isCustomerSupport && didUserLoad && <li onClick={onClickEditAnnouncement} className={editAnnouncementClassName}>Edit Announcement</li>}
            {isCustomerSupport && didUserLoad && <li onClick={onClickCreateFaq} className={createFaqClassName}>Create FAQ</li>}
            {isCustomerSupport && didUserLoad && <li onClick={onClickEditFaq} className={editFaqClassName}>Edit FAQ</li>}
            {didUserLoad && <li onClick={onClickReviews} className={reviewsClassName}>Reviews</li>}
            {reviews.length < 1 && isCustomer && didUserLoad && <li onClick={onClickCreateReview} className={createReviewClassName}>Create Review</li>}
            {showEditReview && isCustomer && didUserLoad && <li onClick={onClickEditReview} className={editReviewClassName}>Edit Review</li>}
            {false && didUserLoad && <li onClick={onClickItems} className={itemsClassName}>Items</li>}
            {false && didUserLoad && <li onClick={onClickCheckout} className={checkoutClassName}>Checkout</li>}
            {false && didUserLoad && <li onClick={onClickCart} className={cartClassName}>Cart</li>}
            <li onClick={onClickAboutUs} className={aboutUsClassName}>About Us</li>
            <li onClick={onClickPerilTypes} className={perilTypesClassName}>Types of Perils</li>
            {didUserLoad && <li onClick={onClickCreatePolicy} className={createPolicyClassName}>Create Policy</li>}
        </ul>
    </>)
};

