package iu.devinmehringer.project3.model.observation;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phenomenon_types")
public class PhenomenonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Kind kind;

    @ElementCollection
    @CollectionTable(name = "phenomenon_type_allowed_units",
            joinColumns = @JoinColumn(name = "phenomenon_type_id"))
    @Column(name = "unit")
    private List<String> allowedUnits = new ArrayList<>();

    @OneToMany(mappedBy = "phenomenonType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phenomenon> phenomena = new ArrayList<>();

    private BigDecimal normalMin;
    private BigDecimal normalMax;


    public PhenomenonType(String name, Kind kind, List<String> allowedUnits, List<Phenomenon> phenomena) {
        this.name = name;
        this.kind = kind;
        this.allowedUnits = allowedUnits;
        this.phenomena = phenomena;
    }

    public PhenomenonType(String name, Kind kind, List<String> allowedUnits, List<Phenomenon> phenomena,
                          BigDecimal normalMin, BigDecimal normalMax) {
        this.name = name;
        this.kind = kind;
        this.allowedUnits = allowedUnits;
        this.phenomena = phenomena;
        this.normalMin = normalMin;
        this.normalMax =normalMax;
    }

    public PhenomenonType() {}

    public List<String> getAllowedUnits() {
        return allowedUnits;
    }

    public void setAllowedUnits(List<String> allowedUnits) {
        this.allowedUnits = allowedUnits;
    }

    public void addAllowedUnits(String units) {
        this.allowedUnits.add(units);
    }

    public boolean removeAllowedUnits(String units) {
        return this.allowedUnits.remove(units);
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Phenomenon> getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(List<Phenomenon> phenomena) {
        this.phenomena = phenomena;
    }

    public void addPhenomenon(Phenomenon phenomenon) {
        this.phenomena.add(phenomenon);
    }

    public boolean removePhenomenon(Phenomenon phenomenon) {
        return this.phenomena.remove(phenomenon);
    }

    public BigDecimal getNormalMin() {
        return normalMin;
    }

    public void setNormalMin(BigDecimal normalMin) {
        this.normalMin = normalMin;
    }

    public BigDecimal getNormalMax() {
        return normalMax;
    }

    public void setNormalMax(BigDecimal normalMax) {
        this.normalMax = normalMax;
    }
}
