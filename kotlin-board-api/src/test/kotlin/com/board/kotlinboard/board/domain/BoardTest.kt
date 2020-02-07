package com.board.kotlinboard.board.domain


import com.board.kotlinboard.board.domain.entity.Board
import com.board.kotlinboard.board.infra.BoardRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class BoardTest(@Autowired val boardRepository: BoardRepository) {

    @Test
    fun `Board 객체 생성`() {
        val title = "제목"
        val content = "내용"
        val id = 1L

        val board1 = Board(title, content, null)

        assertThat(board1.title).isEqualTo(title)
        assertThat(board1.content).isEqualTo(content)

        val board2 = Board(title, content, id)

        assertThat(board2.title).isEqualTo(title)
        assertThat(board2.content).isEqualTo(content)
        assertThat(board2.id).isEqualTo(id)
    }

    @Test
    fun `Board 저장`() {
        val title = "제목"
        val content = "내용"
        val board = Board(title, content, null)

        val newBoard = boardRepository.save(board)

        assertThat(newBoard.title).isEqualTo("제목")
        assertThat(newBoard.content).isEqualTo(content)
        assertThat(newBoard.id).isNotNull()
    }
}