package com.hisujung.web.controller;

import com.hisujung.web.dto.ChatRoomDto;
import com.hisujung.web.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import com.hisujung.web.dto.ChatDto;

import java.util.ArrayList;
import java.util.logging.Logger;


@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    // 아래에서 사용되는 convertAndSend 를 사용하기 위해서 선언
    // convertAndSend 는 객체를 인자로 넘겨주면 자동으로 Message 객체로 변환 후 도착지로 전송한다.
    private final SimpMessageSendingOperations template;

    // 클래스 내에서 로깅 객체 생성
    // 테 스 트 용
    private static final Logger logger = Logger.getLogger(ChatController.class.getName());

    @Autowired
    ChatRoomService repository;

    @Autowired
    ChatRoomService chatRoomService;

    // MessageMapping 을 통해 webSocket 로 들어오는 메시지를 발신 처리한다.
    // 이때 클라이언트에서는 /pub/chat/message 로 요청하게 되고 이것을 controller 가 받아서 처리한다.
    // 처리가 완료되면 /sub/chat/room/roomId 로 메시지가 전송된다.
    @MessageMapping("/chat/enterUser")
    public ResponseEntity<ChatRoomDto> enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {

        // 채팅방 유저+1
        chatRoomService.plusUserCnt(chat.getRoomId());

        // 채팅방에 유저 추가 및 유저 email 반환
        String email = chatRoomService.addUser(chat.getRoomId(), chat.getSender());

        // 반환 결과를 socket session 에 저장
        headerAccessor.getSessionAttributes().put("userName", email);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + " 님이 입장하였습니다.");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

        // 채팅방 정보를 가져온다.
        ChatRoomDto chatRoomInfo = chatRoomService.findChatRoomById(chat.getRoomId());

        // 채팅방 정보를 클라이언트로 전달한다.
        return ResponseEntity.ok(chatRoomInfo);
    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

//    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
//    @EventListener
//    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
//        log.info("DisConnEvent {}", event);
//
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
//        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
//        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
//
//        log.info("headAccessor {}", headerAccessor);
//
//        // 채팅방 유저 -1
//        repository.minusUserCnt(roomId);
//
//        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
//        String username = repository.getUserName(roomId, userUUID);
//        repository.delUser(roomId, userUUID);
//
//        if (username != null) {
//            log.info("User Disconnected : " + username);
//
//            // builder 어노테이션 활용
//            ChatDto chat = ChatDto.builder()
//                    .type(ChatDto.MessageType.LEAVE)
//                    .sender(username)
//                    .message(username + " 님이 퇴장하였습니다.")
//                    .build();
//
//            template.convertAndSend("/sub/chat/room/" + roomId, chat);
//        }
//    }
//
//    // 채팅에 참여한 유저 리스트 반환
//    @GetMapping("/chat/userlist")
//    @ResponseBody
//    public ArrayList<String> userList(String roomId) {
//
//        return repository.getUserList(roomId);
//    }
//
//    // 채팅에 참여한 유저 닉네임 중복 확인
//    @GetMapping("/chat/duplicateName")
//    @ResponseBody
//    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {
//
//        // 유저 이름 확인
//        String userName = repository.isDuplicateName(roomId, username);
//        log.info("동작확인 {}", userName);
//
//        return userName;
//    }
}

