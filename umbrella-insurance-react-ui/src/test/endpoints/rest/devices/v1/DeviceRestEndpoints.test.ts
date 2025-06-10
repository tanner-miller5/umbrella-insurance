import { callCreateDeviceRestEndpoints, callDeleteDeviceRestEndpointsByDeviceId, callReadDeviceRestEndpointsByDeviceId, callUpdateDeviceRestEndpoints } from "../../../../../endpoints/rest/devices/v1/DeviceRestEndpoints";
import { Device } from "../../../../../models/devices/v1/Device";

beforeEach((): void => {
    jest.setTimeout(60000);
});

var env: string = "TEST";
describe.skip('device endpoint tests', () => {
    var deviceId: number | undefined; 
    var device: Device = new Device();
    device.deviceName = "1";
    device.createdDateTime = "2024-11-11T12:12:12Z";
    device.ipAddress = "1.1.1.1";
    device.userAgent = "safari";

    var updatedDevice: Device = new Device();
    updatedDevice.deviceName = "2";
    updatedDevice.createdDateTime = "2025-12-11T12:12:12Z";
    updatedDevice.ipAddress = "2.2.2.2";
    updatedDevice.userAgent = "chrome";

    var domain: string = "http://localhost:8080";

    test('call create device', async () => {
        var deviceResponse: Device = await callCreateDeviceRestEndpoints(
            device, env, domain);
        deviceId = deviceResponse.id;
        expect(device.deviceName).toBe(deviceResponse.deviceName);
        expect(device.createdDateTime).toBe(deviceResponse.createdDateTime);
        expect(device.ipAddress).toBe(deviceResponse.ipAddress);
        expect(device.userAgent).toBe(deviceResponse.userAgent);
    });

    test('call read device', async () => {
        var devices: Device[] | undefined = await callReadDeviceRestEndpointsByDeviceId(
            deviceId || 1, env, domain) || [];
        expect(devices[0].deviceName).toBe(device.deviceName);
        expect(devices[0].createdDateTime).toBe(device.createdDateTime);
        expect(devices[0].ipAddress).toBe(device.ipAddress);
        expect(devices[0].userAgent).toBe(device.userAgent);
    });

    test('call update device', async () => {
        var deviceResponse: Device[] = await callUpdateDeviceRestEndpoints(
            updatedDevice, env, domain);
        expect(updatedDevice.deviceName).toBe(deviceResponse[0].deviceName);
        expect(updatedDevice.createdDateTime).toBe(deviceResponse[0].createdDateTime);
        expect(updatedDevice.ipAddress).toBe(deviceResponse[0].ipAddress);
        expect(updatedDevice.userAgent).toBe(deviceResponse[0].userAgent);
    });

    test('call delete device', async () => {
        var deleteDeviceResponse: boolean = await callDeleteDeviceRestEndpointsByDeviceId(
            deviceId || 1, env, domain);
        expect(true).toBe(deleteDeviceResponse);
    });
});