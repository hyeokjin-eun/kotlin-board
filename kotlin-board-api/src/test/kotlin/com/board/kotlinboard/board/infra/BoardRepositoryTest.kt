package com.board.kotlinboard.board.infra

import com.board.kotlinboard.board.domain.entity.Board
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@DataJpaTest
class BoardRepositoryTest {

    @Mock
    private lateinit var boardRepository: BoardRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @ParameterizedTest
    @CsvSource("제목,내용,1", "어쩌구,저쩌구,2")
    fun `Board 저장 Repository`(title: String, content: String, id: Long) {
        val mockBoard = Board(title, content, id)

        given(boardRepository.save(Board(title, content))).willReturn(mockBoard)

        val newBoard = boardRepository.save(Board(title, content))

        assertThat(newBoard.title).isEqualTo(title)
        assertThat(newBoard.content).isEqualTo(content)
        assertThat(newBoard.id).isNotNull()
    }

    @Test
    fun `Board List 조회 Repository`() {
        val mockBoardList = listOf(
                Board("제목1", "내용1", 1L),
                Board("제목2", "내용2", 2L),
                Board("제목3", "내용3", 3L)
        )

        given(boardRepository.findAll()).willReturn(mockBoardList)

        val boardList = boardRepository.findAll()

        assertThat(boardList[0].id).isEqualTo(1L)
        assertThat(boardList[1].id).isEqualTo(2L)
        assertThat(boardList[2].id).isEqualTo(3L)
    }

    @ParameterizedTest
    @CsvSource("제목,내용,1", "어쩌구,저쩌구,2")
    fun `Board Detail 조회 Repository`(title: String, content: String, id: Long) {
        val mockBoardDetail = Board(title, content, id)

        given(boardRepository.findById(id)).willReturn(Optional.of(mockBoardDetail))

        val boardDetail = boardRepository.findById(id)

        assertThat(boardDetail.get().id).isEqualTo(id)
        assertThat(boardDetail.get().title).isEqualTo(title)
        assertThat(boardDetail.get().content).isEqualTo(content)
    }
}