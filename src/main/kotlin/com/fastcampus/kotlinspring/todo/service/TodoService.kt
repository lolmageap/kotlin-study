package com.fastcampus.kotlinspring.todo.service

import com.fastcampus.kotlinspring.todo.api.model.TodoRequest
import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fastcampus.kotlinspring.todo.domain.TodoRepository
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class TodoService(
    private val todoRepository: TodoRepository,
) {

   @Transactional(readOnly = true)
   fun findAll() : List<Todo> = todoRepository.findAll(Sort.by(Direction.DESC, "id"))

   @Transactional(readOnly = true)
   fun findById(id : Long) : Todo? =
           todoRepository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Transactional
    fun create(todoRequest: TodoRequest?) : Todo {
        checkNotNull(todoRequest) {"TodoRequest is null"}

        val todo = Todo(
                title = todoRequest.title,
                description = todoRequest.description,
                done = todoRequest.done,
                createdAt = LocalDateTime.now(),
        )

        return todoRepository.save(todo)
    }

    @Transactional
    fun update(todoRequest: TodoRequest?, id : Long) : Todo {
        checkNotNull(todoRequest) {"TodoRequest is null"}

//        return findById(id).let {
//            it.update(title = todoRequest.title,
//                    description = todoRequest.description,
//                    done = todoRequest.done
//            )
//            todoRepository.save(it)
//        }

        val findTodo = todoRepository.findByIdOrNull(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        findTodo.update(
                title = todoRequest.title,
                description = todoRequest.description,
                done = todoRequest.done,
        )

        return todoRepository.save(findTodo)
    }

    @Transactional
    fun delete(id : Long) = todoRepository.deleteById(id)
//    : Unit {
//        val todo = (todoRepository.findByIdOrNull(id)
//                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND))
//
//        todoRepository.delete(todo)
//    }

}