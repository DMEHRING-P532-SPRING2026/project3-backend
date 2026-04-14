package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.ProtocolRepository;
import iu.devinmehringer.project3.model.observation.Protocol;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProtocolAccess {
    private final ProtocolRepository protocolRepository;

    public ProtocolAccess(ProtocolRepository protocolRepository) {
        this.protocolRepository = protocolRepository;
    }

    public void save(Protocol protocol) {
        protocolRepository.save(protocol);
    }

    public List<Protocol> getProtocols() {
        return protocolRepository.findAll();
    }
    
    public Optional<Protocol> getById(Long id) {
        return protocolRepository.findById(id);
    }

    public void delete(Protocol protocol) {
        protocolRepository.delete(protocol);
    }
}
