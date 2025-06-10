import { callCreateEmployeeRestEndpoints, callReadEmployeeRestEndpointsByEmployeeId, callDeleteEmployeeRestEndpointsByEmployeeId, callUpdateEmployeeRestEndpoints } from "../../../../../../endpoints/rest/people/employees/v1/EmployeeRestEndpoints";
import { callCreatePersonRestEndpoints, callDeletePersonRestEndpointsByPersonId } from "../../../../../../endpoints/rest/people/v1/PersonRestEndpoints";
import { Employee } from "../../../../../../models/people/employees/v1/Employee";
import { Person } from "../../../../../../models/people/v1/Person";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('employee endpoint tests', () => {
    var employeeId: number | undefined; 

    var person: Person = new Person();
    person.dateOfBirth = "1111-11-11";
    person.firstName = "1";
    person.middleName = "m";
    person.surname = "l";
    person.ssn = "1";
    
    var employee: Employee = new Employee();


    var updatedEmployee: Employee = new Employee();

    var domain: string = "http://localhost:8080";


    test('call create employee', async () => {
        var personResponse: Person = await callCreatePersonRestEndpoints(person, env, domain);
        person.id = personResponse.id;
        employee.person = person;
        updatedEmployee.person = person;
        expect(person.dateOfBirth).toBe(personResponse.dateOfBirth);
        expect(person.firstName).toBe(personResponse.firstName);
        expect(person.middleName).toBe(personResponse.middleName);
        expect(person.surname).toBe(personResponse.surname);

        var employeeResponse: Employee = await callCreateEmployeeRestEndpoints(
            employee, env, domain);
        employeeId = employeeResponse.id;
        expect(employee.person).toBe(employeeResponse.person);
    });

    test('call read employee', async () => {
        var employees: Employee[] | undefined = await callReadEmployeeRestEndpointsByEmployeeId(
            employeeId || 1, env, domain) || [];
        expect(employees[0].person).toBe(employee.person);
    });

    test('call update employee', async () => {
        var employeeResponse: Employee[] = await callUpdateEmployeeRestEndpoints(
            updatedEmployee, env, domain);
        expect(updatedEmployee.person).toBe(employeeResponse[0].person);
    });

    test('call delete employee', async () => {
        var deleteEmployeeResponse: boolean = await callDeleteEmployeeRestEndpointsByEmployeeId(
            employeeId || 1, env, domain);
        expect(true).toBe(deleteEmployeeResponse);

        var deletePersonResponse: boolean = await callDeletePersonRestEndpointsByPersonId(
            person.id || 1, env, domain);
        expect(true).toBe(deletePersonResponse);
    });
});