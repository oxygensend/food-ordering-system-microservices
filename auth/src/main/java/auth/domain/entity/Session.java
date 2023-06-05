package auth.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Session {

    @Id
    private Integer id;

    public Session() {
    }

    public Session(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
