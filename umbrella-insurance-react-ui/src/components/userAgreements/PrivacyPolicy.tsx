import { useDispatch } from 'react-redux';
import '../../css/userAgreements/privacyPolicy.css';
import React, { useEffect } from 'react';
import { updateCurrentPage, updateIsPrivacyPolicyOpen } from '../../redux/reducers/AppReducer';
interface PrivacyPolicyProps {
    isOpen: boolean;
}
export default function PrivacyPolicy({isOpen}:PrivacyPolicyProps){
    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/privacyPolicy"));
        }, []
    )
    function closePrivacyPolicy(){
        dispatch(updateIsPrivacyPolicyOpen(false));
    }
    return (       
        <div className='overlay' hidden={!isOpen}>
            <div className='userAgreementBox'>
                <h1>Privacy Policy for Umbrella Insurance Inc.</h1>
                <p>
Effective Date: February 21, 2025
Last Updated: February 21, 2025  
At Umbrella Insurance Inc. ("we," "us," or "our"), we are committed to protecting your privacy and ensuring the security of your personal information. This Privacy Policy explains how we collect, use, disclose, and safeguard your information when you visit our website www.umbrella-insurance.com (the "Site") or use our online gambling services (the "Services"). By accessing or using our Site and Services, you agree to the terms of this Privacy Policy.
1. Information We Collect
We collect information that you provide to us directly, as well as information collected automatically when you use our Services. The types of information we may collect include:
a. Personal Information You Provide:
Account Information: When you register for an account, we may collect your name, email address, date of birth, phone number, and physical address to verify your identity and comply with legal requirements.
Payment Information: When you deposit or withdraw funds, we collect payment details such as credit/debit card numbers, bank account information, or e-wallet details.
Verification Information: To comply with gambling regulations, we may request government-issued identification, proof of address, or other documents.
Communications: If you contact us via email, live chat, or other methods, we may retain records of those interactions.
b. Information Collected Automatically:
Usage Data: We collect information about your interactions with the Site, such as pages visited, games played, bets placed, and time spent on the platform.
Device Information: We may collect details about your device, including IP address, browser type, operating system, and mobile device identifiers.
Cookies and Tracking Technologies: We use cookies, web beacons, and similar technologies to enhance your experience, analyze Site usage, and deliver personalized content or advertisements. You can manage your cookie preferences through your browser settings.
c. Location Data:
We may collect geolocation information to ensure compliance with regional gambling laws and to prevent fraud. This data may be derived from your IP address or provided by you during account setup.
2. How We Use Your Information
We use your information for the following purposes:
To Provide and Improve Our Services: To process transactions, manage your account, offer gambling services, and enhance Site functionality.
Legal Compliance: To verify your age, identity, and eligibility to gamble, and to comply with anti-money laundering (AML) and know-your-customer (KYC) regulations.
Security and Fraud Prevention: To detect and prevent fraudulent activity, cheating, or abuse of our Services.
Marketing and Promotions: To send you promotional offers, newsletters, or updates about our Services (you may opt out at any time).
Analytics: To analyze usage trends and improve the user experience.
3. How We Share Your Information
We do not sell your personal information. However, we may share your information with:
Service Providers: Third-party vendors who assist us in operating the Site, processing payments, or providing customer support (e.g., payment processors, hosting providers).
Regulatory Authorities: Government agencies or regulators as required by law, such as for tax reporting or gambling oversight.
Law Enforcement: If necessary to respond to legal requests, enforce our policies, or protect the rights, property, or safety of others.
Business Transfers: In the event of a merger, acquisition, or sale of assets, your information may be transferred to the new entity.
4. Your Choices and Rights
Depending on your jurisdiction, you may have certain rights regarding your personal information, including:
Access: Request a copy of the personal data we hold about you.
Correction: Request updates or corrections to inaccurate information.
Deletion: Request the deletion of your data, subject to legal retention requirements.
Opt-Out: Unsubscribe from marketing communications by clicking the "unsubscribe" link in emails or contacting us directly.
Restrict Processing: Request that we limit how we use your data in certain circumstances.
To exercise these rights, please contact us at help@umbrella-insurance.com. We may require identity verification before processing your request.
5. Data Retention
We retain your personal information for as long as necessary to fulfill the purposes outlined in this Privacy Policy, comply with legal obligations (e.g., tax or gambling regulations), or resolve disputes. Account data may be retained for a minimum period (e.g., 5–10 years) as required by law after account closure.
6. Security
We implement reasonable technical and organizational measures to protect your information from unauthorized access, loss, or misuse. However, no online system is 100% secure, and we cannot guarantee absolute security.
7. International Data Transfers
If you are located outside The United States of America, your information may be transferred to and processed in The United States of America, where our servers are located. We ensure appropriate safeguards are in place to protect your data in accordance with applicable laws.
8. Cookies
We use cookies to enhance your experience. You can disable cookies in your browser settings, but this may limit Site functionality. For more details, see our [Cookie Policy] (link if applicable).
9. Third-Party Links
Our Site may contain links to third-party websites (e.g., payment providers). We are not responsible for the privacy practices of these external sites and encourage you to review their policies.
10. Children’s Privacy
Our Services are intended for users aged 21 and older. We do not knowingly collect information from individuals under this age. If we discover such data, we will delete it promptly.
11. Changes to This Privacy Policy
We may update this Privacy Policy from time to time. Changes will be posted on this page with an updated "Last Updated" date. Significant changes will be communicated via email or a prominent notice on the Site.
12. Contact Us
If you have questions or concerns about this Privacy Policy, please contact us at:
Email: help@umbrella-insurance.com
Address: [Insert Physical Address]
Phone: </p>
                <button onClick={closePrivacyPolicy}>ok</button>
            </div>
        </div>
    );
};
