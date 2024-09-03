package com.delivery.storefranchise.domain.sse.connection.model;

import com.delivery.storefranchise.domain.sse.connection.ifs.ConnectionPoolIfs;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    private final String uniqueKey;
    private final SseEmitter sseEmitter;

    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private final ObjectMapper objectMapper;

    private UserSseConnection(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper) {

        // key 초기화
        this.uniqueKey = uniqueKey;

        // sse 초기화
        this.sseEmitter = new SseEmitter(1000L * 60);

        // object Mapper 초기화
        this.objectMapper = objectMapper;

        // call back 초기화
        this.connectionPoolIfs = connectionPoolIfs;

        // on completion
        this.sseEmitter.onCompletion(() -> {
            // connection pool remove
            this.connectionPoolIfs.onCompletionCallback(this);
        });

        // on timeout
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        // onopen 메시지
        this.sendMessage("onopen", "connect");
    }

    public static UserSseConnection connect(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper){
        return new UserSseConnection(uniqueKey, connectionPoolIfs,objectMapper);
    }

    public void sendMessage(String eventName, String data){

        try{
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .name(eventName)
                    .data(json)
                    .build();

            this.sseEmitter.send(event);
        }catch(IOException e){
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(String data){

        try{
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .data(json)
                    .build();
            this.sseEmitter.send(event);
        }catch(IOException e){
            this.sseEmitter.completeWithError(e);
        }
    }

}
