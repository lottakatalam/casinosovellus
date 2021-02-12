package model;

import javax.persistence.*;

@Entity
@Table(name="PeliTulos")
public class PeliTulos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private boolean pelaajaVoitti;

    public int getId() {
        return id;
    }

    public boolean isPelaajaVoitti() {
        return pelaajaVoitti;
    }

    public void setPelaajaVoitti(boolean pelaajaVoitti) {
        this.pelaajaVoitti = pelaajaVoitti;
    }
}
