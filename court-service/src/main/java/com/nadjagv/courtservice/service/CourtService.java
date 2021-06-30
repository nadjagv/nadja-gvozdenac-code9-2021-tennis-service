package com.nadjagv.courtservice.service;

import com.nadjagv.courtservice.domain.Court;
import com.nadjagv.courtservice.exception.AlreadyExistsException;
import com.nadjagv.courtservice.exception.NotFoundException;
import com.nadjagv.courtservice.repository.CourtRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;

    public Court findCourtById(Long id){
        return courtRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Court not found."));
    }

    public List<Court> findAll(){
        return courtRepository.findAll();
    }

    public void createCourt(Court court) {
        if (!courtRepository.existsByName(court.getName())) {
            courtRepository.save(court);

        } else {
            throw new AlreadyExistsException(String.format("Court with name '%s' already exists",court.getName()));
        }
    }

    public void updateCourt(Court court){
        Court existing = courtRepository.getById(court.getId());
        if (existing != null) {

            Court foundByName = courtRepository.findOneByName(court.getName());
            if (foundByName.getId() != existing.getId()){
                throw new AlreadyExistsException(String.format("Court with name '%s' already exists", court.getName()));
            }

            Court updated = Court.builder()
                    .id(existing.getId())
                    .name(court.getName())
                    .build();
            courtRepository.save(updated);
        } else {
            throw new NotFoundException(String.format("Cannot update court that is not in database."));
        }
    }

    public void deleteCourtById(Long id) {
        Court existing = courtRepository.getById(id);
        if (existing != null) {
            courtRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Court with id %d does not exist.", id));
        }
    }
}
