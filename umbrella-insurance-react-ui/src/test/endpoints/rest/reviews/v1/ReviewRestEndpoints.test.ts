import { callCreateReviewRestEndpoints } from "../../../../../endpoints/rest/reviews/v1/ReviewRestEndpoints";
import { Review } from "../../../../../models/reviews/v1/Review";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('review endpoint tests', () => {
    var reviewId: number | undefined; 
    var review: Review = new Review();
    review.subject = "1";

    var updatedReview: Review = new Review();
    updatedReview.subject = "2";

    var domain: string = "http://localhost:8080";


    test('call create review', async () => {
        var reviewResponse: Review = await callCreateReviewRestEndpoints(
            review, env, domain, '');
        reviewId = reviewResponse.id;
        expect(review.subject).toBe(reviewResponse.subject);
    });
        //fix me session code
    /*
    test('call read review', async () => {
        var reviews: Review[] | undefined = await callReadReviewRestEndpointsByReviewId(
            reviewId || 1, env, domain, sessionCode) || [];
        expect(reviews[0].subject).toBe(review.subject);
    });

    test('call update review', async () => {
        var reviewResponse: Review[] = await callUpdateReviewRestEndpointsByReviewId(
            updatedReview, reviewId || 1, env, domain);
        expect(updatedReview.subject).toBe(reviewResponse[0].subject);
    });

    test('call delete review', async () => {
        var deleteReviewResponse: boolean = await callDeleteReviewRestEndpointsByReviewId(
            reviewId || 1, env, domain);
        expect(true).toBe(deleteReviewResponse);
    });
    */
});