package com.board.kotlinboard.board.application

import com.board.kotlinboard.board.domain.Exception.BoardNotFoundException
import com.board.kotlinboard.board.domain.entity.Board
import com.board.kotlinboard.board.infra.BoardRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
internal class BoardServiceTest {

    private lateinit var boardService: BoardService

    @Mock
    private lateinit var boardRepository: BoardRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        boardService = BoardService(boardRepository)
    }

    @ParameterizedTest
    @CsvSource("제목,내용", "어쩌구,저쩌구")
    fun `Board 저장 Service`(title: String, content: String) {
        given(boardRepository.save(Board(title, content))).willReturn(Board(title, content, 1L))

        val boardCreateRes = boardService.create(title, content)

        verify(boardRepository).save(Board(title, content))

        assertThat(boardCreateRes.title).isEqualTo(title)
        assertThat(boardCreateRes.title).isEqualTo(title)
        assertThat(boardCreateRes.id).isNotNull()
    }

    @Test
    fun `Board List 조회 Service`() {
        val mockBoardList = listOf(
                Board("제목1", "내용1", 1L),
                Board("제목2", "내용2", 2L),
                Board("제목3", "내용3", 3L)
        )

        given(boardRepository.findAll()).willReturn(mockBoardList)

        val boardList = boardService.list()

        verify(boardRepository).findAll()

        assertThat(boardList[0].title).isEqualTo(mockBoardList[0].title)
        assertThat(boardList[1].title).isEqualTo(mockBoardList[1].title)
        assertThat(boardList[2].title).isEqualTo(mockBoardList[2].title)
    }

    @ParameterizedTest
    @CsvSource("1", "2")
    fun `Board Detail 조회 Service`(id: Long) {
        val mockBoardDetail = Board("제목", "내용", id)

        given(boardRepository.findById(id)).willReturn(Optional.of(mockBoardDetail))

        val boardDetail = boardService.detail(id)

        verify(boardRepository).findById(id)

        assertThat(boardDetail.id).isEqualTo(mockBoardDetail.id)
        assertThat(boardDetail.title).isEqualTo(mockBoardDetail.title)
        assertThat(boardDetail.content).isEqualTo(mockBoardDetail.content)
    }

    @ParameterizedTest
    @CsvSource("100", "200")
    fun `Board Detail 조회 Not Found`(id: Long) {
        assertThrows<BoardNotFoundException>{
            boardService.detail(id)
        }
    }

    @ParameterizedTest
    @CsvSource("1, 제목, 내용", "2, 어쩌고, 저쩌고")
    fun `Board 수정 Service`(id: Long, title: String, content: String) {
        val mockBoardFind = Board(title, content, id)
        given(boardRepository.save(Board(title, content, id))).willReturn(Board(title, content, id))
        given(boardRepository.findById(id)).willReturn(Optional.of(mockBoardFind))

        val boardUpdate = boardService.update(id, title, content)

        verify(boardRepository).save(Board(title, content, id))
        verify(boardRepository).findById(id)

        assertThat(boardUpdate.id).isEqualTo(id)
        assertThat(boardUpdate.title).isEqualTo(title)
        assertThat(boardUpdate.content).isEqualTo(content)
    }

    @ParameterizedTest
    @CsvSource("1, 제목1, 내용1", "2, 제목2, 내용2")
    fun `Board 삭제 Service`(id: Long, title: String, content: String) {
        val mockBoardFind = Board(title, content, id)

        given(boardRepository.findById(id)).willReturn(Optional.of(mockBoardFind))

        val boardDelete = boardService.delete(id)

        verify(boardRepository).findById(id)
        verify(boardRepository).delete(Board(title, content, id))

        assertThat(boardDelete).isEqualTo("{}")
    }
}