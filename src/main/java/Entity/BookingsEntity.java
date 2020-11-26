package Entity;
import javax.persistence.*;
@Entity
@Table(name = "stp_bookings", schema = "stp")
public class BookingsEntity {
    private int id;
    private TicketsEntity ticket;
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

        BookingsEntity that = (BookingsEntity) o;

        if (id != that.id) return false;

        return true;
    }
    @Override
    public int hashCode() {
        return id;
    }
    @ManyToOne
    @JoinColumn(name = "NAME", referencedColumnName = "ID", nullable = false)
    public TicketsEntity getTicket() {
        return ticket;
    }
    public void setTicket(TicketsEntity warehousesByIdWh) {
        this.ticket = warehousesByIdWh;
    }
    public String toString() {
        return id +  " | " + this.ticket.toString_noid();
    }
}
