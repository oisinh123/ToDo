package assesment.todo.services;

import assesment.todo.dto.ToDo;
import assesment.todo.dao.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<ToDo> findAll() {
        return todoRepository.findAll();
    }

    public ToDo find(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public ToDo save(ToDo toDo) {
        return todoRepository.save(toDo);
    }

    public ToDo update(Long id, ToDo updatedToDo) {
        Optional<ToDo> toDoOpt = todoRepository.findById(id);
        if (toDoOpt.isPresent()) {
            ToDo toDo = toDoOpt.get();
            toDo.setDescription(updatedToDo.getDescription());
            return todoRepository.save(toDo);
        }
        return null;
    }


    public void deleteAll() {
        todoRepository.deleteAll();
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}