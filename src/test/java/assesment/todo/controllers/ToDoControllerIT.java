package assesment.todo.controllers;
import assesment.todo.dao.TodoRepository;
import assesment.todo.dto.ToDo;
import assesment.todo.services.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ToDoControllerIT {


    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE todo");

        // Insert a ToDo item for testGetToDoById and testUpdateToDo
        jdbcTemplate.update("INSERT INTO todo (description) VALUES ('Integration Test Description')");
    }

    @Test
    public void getToDoTest() throws JSONException
    {
        String expected = "[{\"id\":1,\"description\":\"Task 1\"},{\"id\":2,\"description\":\"Task 2\"},{\"id\":3,\"description\":\"Task 3\"}]";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + 8081 + "/todos", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }



    @Test
    public void testCreateToDo() throws Exception {
        ToDo toDo = new ToDo();
        toDo.setDescription("Sample task description");

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.description").value("Sample task description"));
    }

    @Test
    public void testGetAllToDos() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].description").value("Integration Test Description"));
    }

    @Test
    public void testGetToDoById() throws Exception {
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Integration Test Description"));
    }

    @Test
    public void testUpdateToDo() throws Exception {
        ToDo updatedToDo = new ToDo();
        updatedToDo.setDescription("Updated Task 1");

        mockMvc.perform(put("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedToDo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Updated Task 1"));
    }

    @Test
    public void testDeleteToDo() throws Exception {
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNoContent());

        // Verify that the ToDo item has been deleted by trying to find it
        assertFalse(todoRepository.findById(1L).isPresent());
    }
}