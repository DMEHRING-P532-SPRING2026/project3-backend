package iu.devinmehringer.project3.model.observation;

import jakarta.persistence.*;

@Entity
@Table(name = "phenomenon")
public class Phenomenon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "phenomenon_type_id", nullable = false)
    private PhenomenonType phenomenonType;

    public Phenomenon(String name, PhenomenonType phenomenonType) {
        this.name = name;
        this.phenomenonType = phenomenonType;
    }

    public Phenomenon() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhenomenonType getPhenomenonType() {
        return phenomenonType;
    }

    public void setPhenomenonType(PhenomenonType phenomenonType) {
        this.phenomenonType = phenomenonType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
