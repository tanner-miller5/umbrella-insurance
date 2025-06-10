export class WebSocketBackend {
    ws?:WebSocket;
    
    public connect(env: string, domain: string, wsProtocol: string) {

        let url :URL =new URL(`${wsProtocol}://${domain}/userWs?env=${env}`);
        this.ws = new WebSocket(url);
        console.log(`url ${this.ws.url}`);
        console.log(`protocol ${this.ws.protocol}`);
        this.ws.onmessage = function(data) {
            console.log(data.data);
        }
        this.ws.onerror = function(ev:Event){
            console.error(ev);
        };
        if (this.ws) {
            setTimeout(()=>{
                console.log('waiting');
                this.sendData("cool beans1");
            },5000);
            setTimeout(()=>{
                console.log('waiting');
                this.sendData("cool beans2");
            },10000);
        }    
    }
    
    public disconnect() {
        if (this.ws != null) {
            this.ws.close();
        }
        console.log("Websocket is in disconnected state");
    }
    
    public sendData(message:string) {
        let data = JSON.stringify({
            'user' : message
        });
        try {
            this?.ws?.send(data);
        } catch(e: any) {
            console.log(e?.message);
        }
       
    }
}
