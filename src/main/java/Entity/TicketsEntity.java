package Entity;
import javax.persistence.*;
@Entity
@Table(name = "stp_tickets", schema = "stp")
public class TicketsEntity {
    private int id;
    private FilmsEntity film;
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmsEntity that = (FilmsEntity) o;

        if (id != that.getId()) return false;

        return true;
    }
    @ManyToOne
    @JoinColumn(name = "NAME", referencedColumnName = "ID", nullable = false)
    public FilmsEntity getFilm() { return film; }
    public void setFilm(FilmsEntity goodsmainByIdGd) {
        this.film = goodsmainByIdGd;
    }
    public String toString() {
        return id + " | " + this.film.toString_noid();
    }
    public Integer toString_noid() {
        return id;
    }
}
