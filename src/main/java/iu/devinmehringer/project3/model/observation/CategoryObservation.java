package iu.devinmehringer.project3.model.observation;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CATEGORY")
public class CategoryObservation extends Observation {
    @Enumerated(EnumType.STRING)
    private Presence presence;

    @Enumerated(EnumType.STRING)
    private ObservationSource source = ObservationSource.MANUAL;

    @ManyToOne(optional = true)
    @JoinColumn(name = "phenomenon_id")
    private Phenomenon phenomenon;

    public CategoryObservation(Presence presence, Phenomenon phenomenon) {
        this.presence = presence;
        this.phenomenon = phenomenon;
    }

    public CategoryObservation() {}

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    public Phenomenon getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(Phenomenon phenomenon) {
        this.phenomenon = phenomenon;
    }

    public ObservationSource getSource() {
        return source;
    }

    public void setSource(ObservationSource source) {
        this.source = source;
    }
}
