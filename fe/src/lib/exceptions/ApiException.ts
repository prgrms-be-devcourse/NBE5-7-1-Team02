export class APIException extends Error {
    statusCode: number;
    errorCode?: string;

    constructor(statusCode: number, message: string, errorCode?: string) {
        super(message);
        this.name = "APIException";
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}
