package assesment.todo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import assesment.todo.dto.ToDo;

@Repository
public interface TodoRepository extends JpaRepository<ToDo, Long> {
    // You can define custom query methods here if needed
}