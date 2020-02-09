package com.board.kotlinboard.board.application

import com.board.kotlinboard.board.domain.entity.Board
import com.board.kotlinboard.board.infra.BoardRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.mockito.BDDMockito.given as given

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class BoardServiceTest(@Autowired private var boardService: BoardService) {

    @Mock
    private lateinit var boardRepository: BoardRepository

    @ParameterizedTest
    @CsvSource("제목,내용", "어쩌구,저쩌구")
    fun create(title: String, content: String) {

        val boardCreateRes = boardService.create(title, content)

        verify(boardRepository).save(Board(title, content))

        assertThat(boardCreateRes.title).isEqualTo(title)
        assertThat(boardCreateRes.title).isEqualTo(title)

        print(boardCreateRes)
    }
}