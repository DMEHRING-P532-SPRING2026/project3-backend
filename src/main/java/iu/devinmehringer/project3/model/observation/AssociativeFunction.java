package iu.devinmehringer.project3.model.observation;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "associative_function")
public class AssociativeFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "associative_function_arguments",
            joinColumns = @JoinColumn(name = "function_id"),
            inverseJoinColumns = @JoinColumn(name = "phenomenon_type_id")
    )
    private List<PhenomenonType> argumentConcepts = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_concept_id")
    private Phenomenon productConcept;

    public AssociativeFunction(String name, List<PhenomenonType> argumentConcepts, Phenomenon productConcept) {
        this.name = name;
        this.argumentConcepts = argumentConcepts;
        this.productConcept = productConcept;
    }

    public AssociativeFunction() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<PhenomenonType> getArgumentConcepts() { return argumentConcepts; }

    public void setArgumentConcepts(List<PhenomenonType> argumentConcepts) { this.argumentConcepts = argumentConcepts; }

    public void addArgumentConcept(PhenomenonType argumentConcept) { this.argumentConcepts.add(argumentConcept); }

    public boolean removeArgumentConcept(PhenomenonType argumentConcept) { return this.argumentConcepts.remove(argumentConcept); }

    public Phenomenon getProductConcept() { return productConcept; }

    public void setProductConcept(Phenomenon productConcept) { this.productConcept = productConcept; }
}
