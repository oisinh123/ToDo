package assesment.todo.services;

import assesment.todo.dto.ToDo;
import assesment.todo.dao.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<ToDo> findAll() {
        return todoRepository.findAll();
    }
}