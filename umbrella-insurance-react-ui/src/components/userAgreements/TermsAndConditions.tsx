import { useDispatch } from 'react-redux';
import '../../css/userAgreements/termsAndConditions.css';
import React, { useEffect } from 'react';
import { updateCurrentPage, updateIsTermsAndConditionsOpen } from '../../redux/reducers/AppReducer';

interface TermsAndConditionsProps {
    isOpen: boolean;
}
export default function TermsAndConditions({isOpen}:TermsAndConditionsProps){
    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/termsAndConditions"));
        }, []
    )
    function closeTermsAndConditions(){
        dispatch(updateIsTermsAndConditionsOpen(false));
    }
    return (
        <div className='overlay' hidden={!isOpen}>
            <div className='userAgreementBox'>
                <h1>Terms and Conditions for Umbrella Insurance Inc.</h1>
                <p>Effective Date: February 21, 2025
Last Updated: February 21, 2025  
Welcome to www.umbrella-insurance.com ("we," "us," or "our"). These Terms and Conditions ("T&C" or "Terms") govern your access to and use of our website www.umbrella-insurance.com (the "Site") and our online gambling services (the "Services"). By accessing or using the Site or Services, you agree to be bound by these Terms. If you do not agree, please do not use our Site or Services.
1. Acceptance of Terms
By registering an account, depositing funds, or participating in any gambling activity on our Site, you confirm that you have read, understood, and agree to these Terms, along with our Privacy Policy (#) and any other applicable policies (e.g., Responsible Gambling Policy). We may update these Terms at any time, and continued use of the Services constitutes acceptance of the updated Terms.
2. Eligibility
To use our Services, you must:
Be at least 21 years old or the legal gambling age in your jurisdiction, whichever is higher.
Reside in a jurisdiction where online gambling is legal and not prohibited.
Not be listed on any self-exclusion or restricted gambling lists.
Provide accurate and complete information during registration.
We reserve the right to request proof of age, identity, or residency at any time. Failure to comply may result in account suspension or closure.
3. Account Registration
Single Account: You may only register and maintain one account. Multiple accounts are prohibited and may lead to termination and forfeiture of funds.
Accuracy: You must provide truthful and up-to-date information during registration (e.g., name, address, email).
Security: You are responsible for maintaining the confidentiality of your account credentials. Notify us immediately at help@umbrella-insurance.com if you suspect unauthorized use.
Verification: We may require identity verification (e.g., ID, proof of address) before allowing deposits, withdrawals, or gameplay.
4. Deposits and Withdrawals
Deposits: Funds may be deposited using approved payment methods listed on the Site. You must only use payment methods registered in your name.
Withdrawals: Withdrawals are subject to verification checks and may take 3–5 business days to process. Minimum and maximum withdrawal limits apply as specified on the Site.
Currency: All transactions are processed in USD. Currency conversion fees may apply.
Chargebacks: Initiating a chargeback or reversing a payment may result in account suspension and legal action.
5. Gambling Rules
Gameplay: Each game or betting option has specific rules, available on the Site. By participating, you agree to abide by those rules.
Fairness: Our games use certified Random Number Generators (RNGs) to ensure fair outcomes, audited by [Insert Regulator or Testing Agency, if applicable].
Errors: In the event of technical errors (e.g., system glitches), we reserve the right to void bets or adjust winnings as deemed fair.
Prohibited Activities: Cheating, collusion, use of bots, or exploiting software vulnerabilities is strictly prohibited and may result in account closure and forfeiture of funds.
6. Bonuses and Promotions
Eligibility: Bonuses and promotions are subject to specific terms (e.g., wagering requirements, expiration dates) outlined in the promotion details.
Abuse: Attempting to abuse or manipulate bonus offers (e.g., multiple account creation) may lead to bonus revocation and account termination.
Changes: We reserve the right to modify or cancel promotions at any time without prior notice.
7. Responsible Gambling
We are committed to promoting responsible gambling. Tools such as deposit limits, self-exclusion, and cooling-off periods are available. Contact [Insert Contact Email] or visit our Responsible Gambling Page (#) for assistance.
If you believe you have a gambling problem, seek help from organizations like [Insert Local Support, e.g., Gamblers Anonymous].
8. Intellectual Property
All content on the Site (e.g., logos, text, software) is owned by us or our licensors and protected by copyright and trademark laws. You may not copy, modify, or distribute this content without our permission.
9. Limitation of Liability
As-Is Basis: The Services are provided "as is" without warranties of any kind. We do not guarantee uninterrupted access or error-free operation.
Losses: We are not liable for losses due to factors beyond our control (e.g., internet outages, device malfunctions).
Maximum Liability: Our liability to you shall not exceed the amount of your last deposit, unless required by law.
10. Account Suspension and Termination
We may suspend or terminate your account at our discretion if:
You breach these Terms.
We suspect fraud, money laundering, or illegal activity.
You fail to provide requested verification documents.
Required by law or our regulators.
Upon termination, any remaining balance (less pending obligations) will be returned, subject to legal requirements.
11. Prohibited Jurisdictions
Use of our Services is prohibited in jurisdictions where online gambling is illegal or where we do not hold a valid license. It is your responsibility to ensure compliance with local laws.
12. Governing Law and Dispute Resolution
Governing Law: These Terms are governed by the laws of The United States of America.
Disputes: Any disputes arising from these Terms shall be resolved through negotiation. If unresolved, they will be submitted to [Insert Court or Arbitration Body, e.g., arbitration in Malta] for final resolution.
13. Force Majeure
We are not liable for delays or failures in providing Services due to events beyond our control (e.g., natural disasters, cyberattacks, regulatory changes).
14. Amendments
We may update these Terms at any time. Changes will be posted on this page with an updated "Last Updated" date. Significant changes will be communicated via email or Site notification.
15. Contact Us
For questions, complaints, or support regarding these Terms, contact us at:
Email: help@umbrella-insurance.com
Address: [Insert Physical Address]
Phone:  
16. Miscellaneous
Entire Agreement: These Terms, along with the Privacy Policy, constitute the full agreement between you and us.
Severability: If any provision is found invalid, the remaining provisions remain in effect.
No Waiver: Our failure to enforce any right does not waive that right.</p>
                <button onClick={closeTermsAndConditions}>ok</button>
            </div>
        </div>
    );
};