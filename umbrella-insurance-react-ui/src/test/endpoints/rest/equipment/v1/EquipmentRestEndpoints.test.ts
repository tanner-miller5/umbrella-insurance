import { callCreateEquipmentRestEndpoints, callReadEquipmentRestEndpointsByEquipmentId, callDeleteEquipmentRestEndpointsByEquipmentId, callUpdateEquipmentRestEndpoints } from "../../../../../endpoints/rest/equipment/v1/EquipmentRestEndpoints";
import { Equipment } from "../../../../../models/equipment/v1/Equipment";


beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('equipment endpoint tests', () => {
    var equipmentId: number | undefined; 
    var equipment: Equipment = new Equipment();
    equipment.equipmentName = "1";

    var updatedEquipment: Equipment = new Equipment();
    updatedEquipment.equipmentName = "2";

    var domain: string = "http://localhost:8080";

    test('call create equipment', async () => {
        var equipmentResponse: Equipment = await callCreateEquipmentRestEndpoints(
            equipment, env, domain);
        equipmentId = equipmentResponse.id;
        expect(equipment.equipmentName).toBe(equipmentResponse.equipmentName);
    });

    test('call read equipment', async () => {
        var equipments: Equipment[] | undefined = await callReadEquipmentRestEndpointsByEquipmentId(
            equipmentId || 1, env, domain) || [];
        expect(equipments[0].equipmentName).toBe(equipment.equipmentName);
    });

    test('call update equipment', async () => {
        var equipmentResponse: Equipment[] = await callUpdateEquipmentRestEndpoints(
            updatedEquipment, env, domain);
        expect(updatedEquipment.equipmentName).toBe(equipmentResponse[0].equipmentName);
    });

    test('call delete equipment', async () => {
        var deleteEquipmentResponse: boolean = await callDeleteEquipmentRestEndpointsByEquipmentId(
            equipmentId || 1, env, domain);
        expect(true).toBe(deleteEquipmentResponse);
    });
});