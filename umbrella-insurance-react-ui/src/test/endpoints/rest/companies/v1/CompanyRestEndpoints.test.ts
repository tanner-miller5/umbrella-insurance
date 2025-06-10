import { callCreateCompanyRestEndpoints, callDeleteCompanyRestEndpointsByCompanyId, callReadCompanyRestEndpointsByCompanyId, callUpdateCompanyRestEndpoints } from "../../../../../endpoints/rest/companies/v1/CompanyRestEndpoints";
import { Company } from "../../../../../models/companies/v1/Company";

beforeEach((): void => {
    jest.setTimeout(60000);
});
var companyId: number | undefined; 
var env: string = "TEST";
describe.skip('company endpoint tests', () => {
    var company: Company = new Company();
    company.companyName = "1";

    var updatedCompany: Company = new Company();
    updatedCompany.companyName = "2";

    var domain: string = "http://localhost:8080";

    test('call create company', async () => {
        var companyResponse: Company = await callCreateCompanyRestEndpoints(company, env, domain);
        companyId = companyResponse.id;
        expect(company.companyName).toBe(companyResponse.companyName);
    });

    test('call read company', async () => {
        var companys: Company[] | undefined = await callReadCompanyRestEndpointsByCompanyId(companyId || 1, env, domain) || [];
        expect(companys[0].companyName).toBe(company.companyName);
    });

    test('call update company', async () => {
        var companyResponse: Company[] = await callUpdateCompanyRestEndpoints(
            updatedCompany, env, domain);
        expect(updatedCompany.companyName).toBe(companyResponse[0].companyName);
    });

    test('call delete company', async () => {
        var deleteCompanyResponse: boolean = await callDeleteCompanyRestEndpointsByCompanyId(companyId || 1, env, domain);
        expect(true).toBe(deleteCompanyResponse);
    });
});