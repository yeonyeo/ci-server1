package com.example.demo.controller;

import com.example.demo.domain.request.BoardRequest;
import com.example.demo.domain.response.BoardResponse;
import com.example.demo.service.BoardServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @MockBean
    private BoardServiceImpl boardService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllBoard() throws Exception {
//        given
//        boardService.getAll()  list [3]
        BDDMockito.given(boardService.getAll())
                .willReturn(List.of(
                        new BoardResponse(1l, "test1", "test1"),
                        new BoardResponse(2l, "test2", "test2"),
                        new BoardResponse(3l, "test3", "test3")
                ));
//        when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/boards"))// axios.get("/api/boards")
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].id").value(1)
                ) // 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()); // 하는거
//
//        body : [1,2,3]
//
    }

    @Test
    void getByIdBoard() throws Exception {

    }

    @Test
    void saveBoard() throws Exception {
        BoardRequest boardRequest = new BoardRequest("test1", "test1");
        BDDMockito.doNothing()
                .when(boardService)
                .saveBoard(boardRequest);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(boardRequest);
//        when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}