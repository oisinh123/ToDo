package assesment.todo.controllers;

import assesment.todo.dto.ToDo;
import assesment.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/{todoId}")
    public ToDo getToDo(@PathVariable Long todoId) {
        //test
        return todoService.find(todoId);
    }

    @PostMapping
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        ToDo createdToDo = todoService.save(toDo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToDo);
    }

    @PutMapping("/{todoId}")
    public ToDo updateToDo(@PathVariable Long todoId, @RequestBody ToDo updatedToDo) {
        return todoService.update(todoId, updatedToDo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return HTTP 204 status
    }

    @GetMapping
    public List<ToDo> getAllTodos() {
        return todoService.findAll();
    }
}