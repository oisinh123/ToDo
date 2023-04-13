package assesment.todo.controllers;
import assesment.todo.dto.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import assesment.todo.services.TodoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private TodoService todoService;

    private final Map<Long, ToDo> todos = new HashMap<>();

    @GetMapping("/{todoId}")
    public ToDo getToDo(@PathVariable Long todoId) {
        //hi
        return todos.get(todoId);
    }

    @PostMapping("/")
    public ToDo createToDo(@RequestBody ToDo toDo) {
        todos.put(toDo.getId(), toDo);
        return toDo;
    }

    @PutMapping("/{todoId}")
    public ToDo updateToDo(@PathVariable Long todoId, @RequestBody ToDo updatedToDo) {
        todos.put(todoId, updatedToDo);
        return updatedToDo;
    }

    @DeleteMapping("/{todoId}")
    public void deleteToDo(@PathVariable Long todoId) {
        todos.remove(todoId);
    }

    @GetMapping
    public List<ToDo> getAllTodos() {
        return todoService.findAll();
    }
}