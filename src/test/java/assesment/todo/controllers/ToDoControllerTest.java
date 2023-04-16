package assesment.todo.controllers;

import assesment.todo.dto.ToDo;
import assesment.todo.services.TodoService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private ToDoController toDoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
    }

    @Test
    public void testGetToDo() throws Exception {
        ToDo toDo = new ToDo(1L, "Test task");
        when(todoService.find(eq(1L))).thenReturn(toDo);

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("description").value("Test task"));

        verify(todoService, times(1)).find(eq(1L));
    }

//    @Test
//    public void testCreateToDo() throws Exception {
//        String todoJson = "{\"description\": \"Task 4\"}";
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(todoJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.description").value("Task 4"));
//    }

    @Test
    public void testUpdateToDo() throws Exception {
        ToDo updatedToDo = new ToDo(1L, "Updated test task");
        when(todoService.update(eq(1L), any(ToDo.class))).thenReturn(updatedToDo);

        mockMvc.perform(put("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"description\": \"Updated test task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("description").value("Updated test task"));

        verify(todoService, times(1)).update(eq(1L), any(ToDo.class));
    }

    @Test
    public void testDeleteToDo() {
        // Given
        Long id = 1L;

        // When
        toDoController.deleteToDo(id);

        // Then
        verify(todoService, times(1)).delete(id);

    }

}
