package com.library.app.patron.service;


import com.library.app.patron.boundary.PatronDTO;
import com.library.app.patron.repository.PatronRepository;
import com.library.app.patron.repository.entity.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The `PatronService` class provides services for managing patrons within the library.
 */
@Service
public class PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll()
                .stream()
                .map(PatronDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public PatronDTO getPatronById(Long id) {
        return patronRepository.findById(id)
                .map(PatronDTO::fromEntity)
                .orElse(null);
    }

    public void addPatron(PatronDTO patronDTO) {
        Patron patron = patronDTO.toEntity();
        PatronDTO.fromEntity(patronRepository.save(patron));
    }

    public void editPatron(PatronDTO patronDTO) {
        Patron patron = patronDTO.toEntity();
        PatronDTO.fromEntity(patronRepository.save(patron));
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

    /**
     * Filters patrons based on search text and membership status.
     *
     * @param searchText The text to search for in patron names or emails.
     * @param member     The membership status of patrons (`true` for members, `false` for non-members, `null` for all).
     * @return A filtered list of `PatronDTO` objects representing patrons.
     */
    public List<PatronDTO> filterPatrons(String searchText, Boolean member) {
        List<Patron> patrons = patronRepository.findAll();

        if (searchText != null && !searchText.isEmpty()) {
            patrons = patrons.stream()
                    .filter(patron -> patron.getName().contains(searchText) || patron.getEmail().contains(searchText))
                    .collect(Collectors.toList());
        }

        if (member != null) {
            patrons = patrons.stream()
                    .filter(patron -> patron.isMember() == member)
                    .collect(Collectors.toList());
        }

        return patrons.stream()
                .map(PatronDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
