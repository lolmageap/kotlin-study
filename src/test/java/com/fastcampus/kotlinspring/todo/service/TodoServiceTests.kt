package com.fastcampus.kotlinspring.todo.service

import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fastcampus.kotlinspring.todo.domain.TodoRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
class TodoServiceTests {

    @MockkBean
    lateinit var repository: TodoRepository

    private lateinit var service: TodoService

    val stub: Todo by lazy {
        Todo(
                id = 1L,
                title = "테스트",
                description = "테스트 상세",
                done = false,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
        )
    }

    @BeforeEach
    fun setUp() : Unit {
        service = TodoService(repository)
    }

    @Test
    fun `한개의 TODO를 반환해야한다`(){
        //Given
        every { repository.findByIdOrNull(1L) } returns stub

        //When
        val actual = service.findById(1L)

        //Then
        assertThat(actual).isNotNull
        assertThat(actual).isEqualTo(stub)

    }

}