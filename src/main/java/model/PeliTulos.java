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

    @Column
    private int panos;


    public int getId() {
        return id;
    }

    public boolean isPelaajaVoitti() {
        return pelaajaVoitti;
    }

    public void setPelaajaVoitti(boolean pelaajaVoitti) {
        this.pelaajaVoitti = pelaajaVoitti;
    }

    public int getPanos() {
        return panos;
    }

    public void setPanos(int panos) {
        this.panos = panos;
    }
}
