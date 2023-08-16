package com.hisujung.web.controller;

import com.hisujung.web.entity.Member;
import com.hisujung.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import com.hisujung.web.dao.ChatRepository;
import com.hisujung.web.dto.ChatRoomDto;
import com.hisujung.web.service.ChatRoomService;
import com.hisujung.web.service.UserService;
import com.hisujung.web.service.social.PrincipalDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@Slf4j
@RestController
public class ChatRoomController {

    private static final Logger logger = Logger.getLogger(ChatRoomController.class.getName());


//    private final ChatRepository chatRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

//    @Autowired
//    public ChatRoomController(
//            ChatRepository chatRepository,
//            ChatRoomService chatRoomService,
//            UserService userService,
//            SimpMessagingTemplate messagingTemplate
//    ) {
//        this.chatRepository = chatRepository;
//        this.chatRoomService = chatRoomService;
//        this.userService = userService;
//        this.messagingTemplate = messagingTemplate;
//    }

    // 채팅 리스트 화면
    // / 로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
    // 사용자의 채팅방 정보 리턴
    @GetMapping("/chat-rooms")
    public ResponseEntity<?> getUserChatRooms(Authentication auth) {
        try {
            // 사용자 정보 가져오기
            Member loginUser = userService.getLoginUserByLoginId(auth.getName());
            Long userId = loginUser.getId();

            // 사용자가 입장한 채팅방 정보 가져오기
            List<ChatRoomDto> userChatRooms = chatRoomService.getChatRoomList(loginUser);

            // 필요한 처리 후 응답 생성
            return ResponseEntity.ok(userChatRooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    // 스프링 시큐리티의 로그인 유저 정보는 Security 세션의 PrincipalDetails 안에 담긴다
    // 정확히는 PrincipalDetails 안에 ChatUser 객체가 담기고, 이것을 가져오면 된다.
//    @GetMapping("/chat")
//    public String goChatRoom(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
//
//        model.addAttribute("list", chatRepository.findAllRoom());
//
//        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
//        if (principalDetails != null) {
//            // 세션에서 로그인 유저 정보를 가져옴
//            model.addAttribute("user", principalDetails.getUser());
//            log.info("user [{}] ",principalDetails);
//        }
//
////        model.addAttribute("user", "hey");
//        log.info("SHOW ALL ChatList {}", chatRepository.findAllRoom());
//        return "roomlist";
//    }

//    // 사용자의 채팅 리스트에 새로운 채팅방을 추가하는 함수
//    private void addChatRoomToUserList(List<ChatRoomDto> userChatRooms, ChatRoomDto newChatRoom) {
//        boolean containsNewChatRoom = userChatRooms.stream()
//                .anyMatch(room -> room.getRoomId().equals(newChatRoom.getRoomId()));
//
//        if (!containsNewChatRoom) {
//            userChatRooms.add(newChatRoom);
//        }
//    }


    // 채팅방 생성
    @PostMapping("/chat/createroom")
    public ResponseEntity<ChatRoomDto> createRoom(@RequestParam("roomName") String name,
                                                  @RequestParam("roomPwd") String roomPwd,
                                                  @RequestParam(value = "secretChk", defaultValue = "0") int secretChk,
                                                  @RequestParam(value = "maxUserCnt", defaultValue = "100") String maxUserCnt,
                                                  Authentication auth) {

//        String userId = auth.getName(); // 현재 인증된 사용자

        // 현재 로그인한 사용자
        Member loginUser = userService.getLoginUserByLoginId(auth.getName());

        Long userId = loginUser.getId();

        ChatRoomDto newChatRoomDto = chatRoomService.createChatRoom(loginUser, name, roomPwd, secretChk, Integer.parseInt(maxUserCnt));

        log.info("CREATE Chat Room [{}]", newChatRoomDto.getRoomId());

//        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
//                .roomName(name)
//                .roomId(roomId)
//                .userCount(0)
//                .maxUserCnt(Integer.parseInt(maxUserCnt))
//                .roomPwd(roomPwd)
//                .secretChk(Boolean.parseBoolean(secretChk))
//                .userlist(new HashMap<>()) // Add initial userlist here if needed
//                .build();

        // 새로운 채팅방이 생성되면 해당 정보를 클라이언트로 전송
//        messagingTemplate.convertAndSend("/sub/newChatRoom", chatRoomDto);

        // 사용자의 채팅 리스트에 새로운 채팅방 추가
//        List<ChatRoomDto> userChatRooms = chatRoomService.getUserChatRooms(userId);
//        addChatRoomToUserList(userChatRooms, newChatRoomDto);
//        logger.info("Add newChat to User's Chatting list " + userChatRooms);

        return ResponseEntity.ok(newChatRoomDto);
    }

    // 채팅방 입장
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
//    @GetMapping("/chat/room/{roomId}")
//    public String roomDetail(Model model, @PathVariable("roomId") String roomId, @AuthenticationPrincipal PrincipalDetails principalDetails){
//
//        log.info("roomId {}", roomId);
//
//        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
//        if (principalDetails != null) {
//            // 세션에서 로그인 유저 정보를 가져옴
//            model.addAttribute("user", principalDetails.getUser());
//        }
//
//        // ChatRoomService를 이용하여 채팅방 정보를 조회하여 모델에 추가
//        ChatRoomDto chatRoomDto = chatRoomService.findChatRoomById(roomId);
//        model.addAttribute("room", chatRoomDto);
//
//        return principalDetails.getName();
//    }

    @GetMapping("/chat/room/{roomId}")
    public String roomDetail(Model model, @PathVariable("roomId") String roomId, Authentication auth){

        Member loginUser = userService.getLoginUserByLoginId(auth.getName());
        log.info("roomId {}", roomId);

        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        if (loginUser != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", loginUser);
        }

        // ChatRoomService를 이용하여 채팅방 정보를 조회하여 모델에 추가
        ChatRoomDto chatRoomDto = chatRoomService.findChatRoomById(roomId);
        model.addAttribute("room", chatRoomDto);

        return loginUser.getUsername();
    }


//    // 채팅방 비밀번호 확인
//    @PostMapping("/chat/confirmPwd/{roomId}")
//    @ResponseBody
//    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd){
//
//        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
//        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
//        return chatRepository.confirmPwd(roomId, roomPwd);
//    }
//
//    // 채팅방 삭제
//    @GetMapping("/chat/delRoom/{roomId}")
//    public String delChatRoom(@PathVariable String roomId){
//
//        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
//        chatRepository.delChatRoom(roomId);
//
//        return "redirect:/";
//    }
//
//    @GetMapping("/chat/chkUserCnt/{roomId}")
//    @ResponseBody
//    public boolean chUserCnt(@PathVariable String roomId){
//
//        return chatRepository.chkRoomUserCnt(roomId);
//    }

}
