import { callCreateRewardRestEndpoints, callDeleteRewardRestEndpointsByRewardId, callReadRewardRestEndpointsByRewardId, callUpdateRewardRestEndpoints } from "../../../../../endpoints/rest/rewards/v1/RewardRestEndpoints";
import { Reward } from "../../../../../models/rewards/v1/Reward";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('reward endpoint tests', () => {
    var rewardId: number | undefined; 
    var reward: Reward = new Reward();
    reward.rewardName = "1";

    var updatedReward: Reward = new Reward();
    updatedReward.rewardName = "2";
    var domain: string = "http://localhost:8080";

    test('call create reward', async () => {
        var rewardResponse: Reward = await callCreateRewardRestEndpoints(
            reward, env, domain);
        rewardId = rewardResponse.id;
        expect(reward.rewardName).toBe(rewardResponse.rewardName);
    });

    test('call read reward', async () => {
        var rewards: Reward[] | undefined = await callReadRewardRestEndpointsByRewardId(
            rewardId || 1, env, domain) || [];
        expect(rewards[0].rewardName).toBe(reward.rewardName);
    });

    test('call update reward', async () => {
        var rewardResponse: Reward[] = await callUpdateRewardRestEndpoints(
            updatedReward, env, domain);
        expect(updatedReward.rewardName).toBe(rewardResponse[0].rewardName);
    });

    test('call delete reward', async () => {
        var deleteRewardResponse: boolean = await callDeleteRewardRestEndpointsByRewardId(
            rewardId || 1, env, domain);
        expect(true).toBe(deleteRewardResponse);
    });
});