import Stomp from "stompjs";
import SockJS from "sockjs-client";

export default {
    connect(callback) {
        const socket = new SockJS('http://localhost:9000/connect');
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