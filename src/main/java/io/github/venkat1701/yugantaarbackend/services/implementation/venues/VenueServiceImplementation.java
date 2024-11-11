package io.github.venkat1701.yugantaarbackend.services.implementation.venues;

import io.github.venkat1701.yugantaarbackend.dto.venues.VenueDTO;
import io.github.venkat1701.yugantaarbackend.models.venues.Venue;
import io.github.venkat1701.yugantaarbackend.repositories.venue.VenueRepository;
import io.github.venkat1701.yugantaarbackend.services.core.venues.VenueService;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresVenueCreatePermission;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresVenueDeletePermission;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresVenueReadPermission;
import io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations.RequiresVenueUpdatePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueServiceImplementation implements VenueService {

    private final VenueRepository venueRepository;

    public VenueServiceImplementation(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    @RequiresVenueReadPermission
    public Page<Venue> search(PageRequest pageRequest) {
        return this.venueRepository.findAll(pageRequest);
    }

    @Override
    @RequiresVenueReadPermission
    public List<Venue> getAll() {
        return this.venueRepository.findAll();
    }

    @Override

    public Optional<Venue> findById(Long aLong) {
        if(this.venueRepository.existsById(aLong)) {
            return this.venueRepository.findById(aLong);
        }
        return Optional.empty();
    }

    @Override
    @RequiresVenueUpdatePermission
    public Optional<Venue> update(Long aLong, Venue venue) {
        if(this.venueRepository.existsById(aLong)) {
            return Optional.of(this.venueRepository.save(venue));
        }
        return Optional.empty();
    }

    @Override
    @RequiresVenueDeletePermission
    public boolean delete(Long aLong) {
        if(this.venueRepository.existsById(aLong)) {
            this.venueRepository.deleteById(aLong);
            return true;
        }
        return false;
    }

    @Override
    @RequiresVenueReadPermission
    public Optional<Venue> findByName(String name) {
        return this.venueRepository.findByName(name);
    }

    @RequiresVenueCreatePermission
    public Venue registerVenue(VenueDTO venueDTO) {
        Venue venue = new Venue();
        venue.setName(venueDTO.getName());
        venue.setDescription(venueDTO.getDescription());
        venue.setCapacity(venueDTO.getCapacity());
        venue.setAddress(venueDTO.getAddress());
        venue.setContactEmail(venueDTO.getContactEmail());
        venue.setContactPhone(venueDTO.getContactPhone());
        venue.setContactPerson(venueDTO.getContactPerson());
        return this.venueRepository.save(venue);
    }
}
