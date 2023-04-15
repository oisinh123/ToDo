package assesment.todo.dto;

import javax.persistence.*;

@Entity
@Table(name = "todo")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = true) // Set nullable to true
    private String status;
    private String description;

    public ToDo() {
    }

    public ToDo(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public ToDo(long l, String s, String s1, boolean b) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}