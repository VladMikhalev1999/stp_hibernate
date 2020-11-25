package Entity;
import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "stp_films", schema = "stp")
public class FilmsEntity {
    private int id;
    private String name;
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmsEntity that = (FilmsEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(name, that.name)) return false;

        return true;
    }
    @Override
    public String toString() {
        return id + "\t" + name;
    }
    public String toString_noid() {
        return name;
    }
}
