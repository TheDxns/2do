import Stomp from 'stompjs'

export default {
    connect(callback) {
        const socket = new WebSocket('ws://localhost/connect');
        const stompClient = Stomp.over(socket);
        console.log("Connecting")
        stompClient.connect({}, function() {
            callback(stompClient);
            console.log("Connected")
        });
    },
    subscribe(stompClient, destination, callback) {
        stompClient.subscribe(destination, function(notification) {
            const payload = JSON.parse(notification.body);
            callback(payload);
        });
    },
    send(stompClient, destination) {
        stompClient.send(destination);
    }
}