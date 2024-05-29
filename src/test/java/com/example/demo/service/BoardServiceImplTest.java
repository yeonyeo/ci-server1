package com.example.demo.service;

import com.example.demo.domain.entity.Board;
import com.example.demo.domain.request.BoardRequest;
import com.example.demo.domain.response.BoardResponse;
import com.example.demo.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
//
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {
    @Mock private BoardRepository boardRepository;
    @InjectMocks private BoardServiceImpl boardService;

    @Test
    void getById() {
        Board board = new Board(1l, "test", "test");
        BDDMockito.given(boardRepository.findById(1l))
                .willReturn(Optional.of(board));


        BoardResponse byId = boardService.getById(1l);

//        행위 검증
        Mockito.verify(boardRepository, Mockito.times(1)).findById(1l);
//        상태 검증
        assertEquals("test", byId.name());
        assertEquals("test", byId.text());
        assertNotNull(byId.id());
    }
    @Test
    void 아이디로_보드를_조회할때_없는_아이디로_조회를_했을때_IllegalArumentException이_발생한다() {
        BDDMockito.given(boardRepository.findById(1l))
                .willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, ()->{
            boardService.getById(1l);
        });
        Mockito.verify(boardRepository,Mockito.times(1)).findById(1l);

    }

    @Test
    void getAll() {

        BDDMockito.given(boardRepository.findAll()).willReturn(
                List.of(new Board(1l,"test", "test"),new Board(2l,"test", "test")));

        List<BoardResponse> all = boardService.getAll();

        assertEquals(2, all.size());
        assertEquals("test", all.get(1).name());
        Mockito.verify(boardRepository).findAll();
    }

    @Test
    void saveBoard() {
        BoardRequest request = new BoardRequest("test", "test");
        Board entity = request.toEntity();
        BDDMockito.given(boardRepository.save(entity))
                .willReturn(entity);

        boardService.saveBoard(request);

        Mockito.verify(boardRepository, Mockito.times(1)).save(entity);
    }



}