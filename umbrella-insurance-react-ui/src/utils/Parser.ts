export function toObject(obj: any) {
    return JSON.parse(JSON.stringify(obj, (key, value) =>
        typeof value === 'number'
            ? value.toString()
            : value // return everything else unchanged
    ));
}