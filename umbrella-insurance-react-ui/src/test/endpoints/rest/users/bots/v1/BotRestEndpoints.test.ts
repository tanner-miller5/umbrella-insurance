import { callCreateSpecialtyRestEndpoints, callDeleteSpecialtyRestEndpointsBySpecialtyId } from "../../../../../../endpoints/rest/people/analysts/specialties/v1/SpecialtyRestEndpoints";
import { callCreateAnalystRestEndpoints, callDeleteAnalystRestEndpointsByAnalystId } from "../../../../../../endpoints/rest/people/analysts/v1/AnalystRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { callCreateUnitRestEndpoints, callDeleteUnitRestEndpointsByUnitId } from "../../../../../../endpoints/rest/units/v1/UnitRestEndpoints";
import { callCreateBotRestEndpoints, callReadBotRestEndpointsByBotId, callDeleteBotRestEndpointsByBotId, callUpdateBotRestEndpoints } from "../../../../../../endpoints/rest/users/bots/v1/BotRestEndpoints";
import { Specialty } from "../../../../../../models/people/analysts/specialties/v1/Specialty";
import { Analyst } from "../../../../../../models/people/analysts/v1/Analyst";
import { Person } from "../../../../../../models/people/v1/Person";
import { Unit } from "../../../../../../models/units/v1/Unit";
import { Bot } from "../../../../../../models/users/bots/v1/Bot";
import { User } from "../../../../../../models/users/v1/User";
import { callCreateUserRestEndpoints } from "../../../../../../endpoints/soa/user/v1/UserSoaEndpoints";
import { CreateUserResponse } from "../../../../../../endpoints/soa/user/v1/responses/CreateUserResponse";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('bot endpoint tests', () => {
var domain: string = "http://localhost:8080";
    
    var botId: number | undefined; 

    var unit: Unit = new Unit();
    unit.unitName = "1";

    var createdDateTime = "2011-11-11T11:11:11Z";
    var emailAddress: string = "1";
    var phoneNumber: string = "2";
    var username: string = "3";
    var isPhoneNumberVerified: boolean = false;
    var isEmailAddressVerified: boolean = false;

    var specialty: Specialty = new Specialty();
    specialty.specialtyName = "1";

    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1";

    var user: User = new User();

    user.createdDateTime = createdDateTime;
    user.emailAddress = emailAddress;
    user.phoneNumber = phoneNumber;
    user.username = username;
    user.isEmailAddressVerified = isEmailAddressVerified;
    user.isPhoneNumberVerified = isPhoneNumberVerified;


    var analyst: Analyst = new Analyst();

    var bot: Bot = new Bot();
    bot.botName = "1";
    bot.amountFunded = 100;
    bot.createdDateTime = "1111-11-11T11:11:11Z";
    bot.deletedDateTime = "2111-11-11T11:11:11Z";
    bot.isDeleted = false;
    bot.isDisabled = false;

    var updatedBot: Bot = new Bot();
    updatedBot.botName = "3";
    updatedBot.amountFunded = 200;
    updatedBot.createdDateTime = "3111-11-11T11:11:11Z";
    updatedBot.deletedDateTime = "4111-11-11T11:11:11Z";
    updatedBot.isDeleted = true;
    updatedBot.isDisabled = true;

    test('call create bot', async () => {
        var specialtyResponse: Specialty = await callCreateSpecialtyRestEndpoints(
            specialty, env, domain);
        specialty.id = specialtyResponse.id;
        analyst.specialty = specialty;
        expect(specialty.specialtyName).toBe(specialtyResponse.specialtyName);
        
        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        person.id = personResponse.id;
        analyst.person = person;
        user.person = person;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);

        var userResponse: CreateUserResponse = await callCreateUserRestEndpoints(user, env, domain);
        user = userResponse.user || new User();
        bot.user = user;
        updatedBot.user = user;
        expect(user.createdDateTime).toBe(userResponse.createdDateTime);
        expect(user.emailAddress).toBe(userResponse.emailAddress);
        expect(user.phoneNumber).toBe(userResponse.phoneNumber);
        expect(user.username).toBe(userResponse.username);
        expect(user.isEmailAddressVerified).toBe(userResponse.isEmailAddressVerified);
        expect(user.isPhoneNumberVerified).toBe(userResponse.isPhoneNumberVerified);
        
        var analystResponse: Analyst = await callCreateAnalystRestEndpoints(
            analyst, env, domain);
        analyst.id = analystResponse.id;
        bot.analyst = analyst;
        updatedBot.analyst = analyst;
        expect(analyst.person).toBe(analystResponse.person);
        expect(analyst.specialty).toBe(analystResponse.specialty);

        var unitResponse: Unit = await callCreateUnitRestEndpoints(
            unit, env, domain);
        unit.id = unitResponse.id;
        bot.unit = unit;
        updatedBot.unit = unit;
        expect(unit.unitName).toBe(unitResponse.unitName);

        var botResponse: Bot = await callCreateBotRestEndpoints(
            bot, env, domain);
        botId = botResponse.id;
        expect(bot.botName).toBe(botResponse.botName);
        expect(bot.amountFunded).toBe(botResponse.amountFunded);
        expect(bot.analyst).toBe(botResponse.analyst);
        expect(bot.createdDateTime).toBe(botResponse.createdDateTime);
        expect(bot.deletedDateTime).toBe(botResponse.deletedDateTime);
        expect(bot.isDeleted).toBe(botResponse.isDeleted);
        expect(bot.isDisabled).toBe(botResponse.isDisabled);
        expect(bot.unit).toBe(botResponse.unit);
        expect(bot.user).toBe(botResponse.user);
    });

    test('call read bot', async () => {
        var botResponse: Bot[] | undefined = await callReadBotRestEndpointsByBotId(
            botId || 1, env, domain) || [];
        expect(bot.botName).toBe(botResponse[0].botName);
        expect(bot.amountFunded).toBe(botResponse[0].amountFunded);
        expect(bot.analyst).toBe(botResponse[0].analyst);
        expect(bot.createdDateTime).toBe(botResponse[0].createdDateTime);
        expect(bot.deletedDateTime).toBe(botResponse[0].deletedDateTime);
        expect(bot.isDeleted).toBe(botResponse[0].isDeleted);
        expect(bot.isDisabled).toBe(botResponse[0].isDisabled);
        expect(bot.unit).toBe(botResponse[0].unit);
        expect(bot.user).toBe(botResponse[0].user);
    });

    test('call update bot', async () => {
        var botResponse: Bot[] = await callUpdateBotRestEndpoints(
            updatedBot, env, domain);
        expect(updatedBot.botName).toBe(botResponse[0].botName);
        expect(updatedBot.amountFunded).toBe(botResponse[0].amountFunded);
        expect(updatedBot.analyst).toBe(botResponse[0].analyst);
        expect(updatedBot.createdDateTime).toBe(botResponse[0].createdDateTime);
        expect(updatedBot.deletedDateTime).toBe(botResponse[0].deletedDateTime);
        expect(updatedBot.isDeleted).toBe(botResponse[0].isDeleted);
        expect(updatedBot.isDisabled).toBe(botResponse[0].isDisabled);
        expect(updatedBot.unit).toBe(botResponse[0].unit);
        expect(updatedBot.user).toBe(botResponse[0].user);
    });

    test('call delete bot', async () => {
        var deleteBotResponse: boolean = await callDeleteBotRestEndpointsByBotId(
            botId || 1, env, domain);
        expect(true).toBe(deleteBotResponse);

        var deleteAnalystResponse: boolean = await callDeleteAnalystRestEndpointsByAnalystId(
            analyst.id || 1, env, domain);
        expect(true).toBe(deleteAnalystResponse);

        //var deleteUserResponse: boolean = await callDeleteUserRestEndpointsByUserId(user.userId || 1, env, domain);
        //expect(true).toBe(deleteUserResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);

        var deleteSpecialtyResponse: boolean = await callDeleteSpecialtyRestEndpointsBySpecialtyId(
            specialty.id || 1, env, domain);
        expect(true).toBe(deleteSpecialtyResponse);

        var deleteUnitResponse: boolean = await callDeleteUnitRestEndpointsByUnitId(
            unit.id || 1, env, domain);
        expect(true).toBe(deleteUnitResponse);
    });
});