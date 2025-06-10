export class LoggingMessage {
      appName?: string;
      logLevel?: string;
      loggingPayload?: string;
      callingLoggerName?: string;
      callingMethod?: string;
      latitude?: number;
      longitude?: number;
      accuracy?: number;
      constructor(){
            let error: Error = new Error();
            if(error?.stack) {
                  this.callingMethod = error?.stack
                  ?.split('\n')[2]
                  ?.replace(/^\s+at\s+(.+?)\s.+/g, '$1' );   
            }
      }
}