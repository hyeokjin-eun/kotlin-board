package com.board.kotlinboard.board.domain


import com.board.kotlinboard.board.domain.entity.Board
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
internal class BoardTest {

    @ParameterizedTest
    @CsvSource("제목,내용,", "title,content,", "title,content,1")
    fun `Board 객체 생성`(title: String, content: String, id: Long?) {
        val board1 = Board(title, content)

        assertThat(board1.title).isEqualTo(title)
        assertThat(board1.content).isEqualTo(content)

        val board2 = Board(title, content, id)

        assertThat(board2.title).isEqualTo(title)
        assertThat(board2.content).isEqualTo(content)
        assertThat(board2.id).isEqualTo(id)
    }
}