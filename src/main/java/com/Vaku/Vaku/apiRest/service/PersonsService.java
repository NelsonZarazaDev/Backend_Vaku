package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.ChildrensEntity;
import com.Vaku.Vaku.apiRest.model.entity.ParentsEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.exception.AlreadyExistsException;
import com.Vaku.Vaku.exception.NotFoundException;
import com.Vaku.Vaku.utils.Constants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PersonsService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");
    private static final Pattern DOCUMENT_PATTERN = Pattern.compile("^[0-9]{5,10}$");

    private final PersonsRepository personsRepository;
    private final ChildrensParentsService childrensParentsService;
    private final PasswordEncoder passwordEncoder;
    private final EmployessService employessService;

    @Transactional
    public List<PersonsEntity> createPersons(List<PersonsEntity> personsRequest) {
        if (personsRequest == null || personsRequest.isEmpty()) {
            throw new IllegalArgumentException("Debe enviar al menos una persona para registrar");
        }

        validateDuplicateFieldsInRequest(personsRequest);

        List<PersonsEntity> savedPersonsList = new ArrayList<>();
        Long parentId = null;
        Long childId = null;

        for (PersonsEntity personRequest : personsRequest) {
            sanitizePerson(personRequest);
            validateRoleSupported(personRequest.getPersRole());
            validateRequiredFieldsByRole(personRequest);
            validateFieldFormats(personRequest);

            if (personsRepository.findByPersDocument(personRequest.getPersDocument()).isPresent()) {
                throw new AlreadyExistsException(Constants.DOCUMENT_ALREADY_EXISTS.getMessage());
            }

            validateUniqueFieldsInDatabase(personRequest);

            PersonsEntity person = saveNewPerson(personRequest);
            savedPersonsList.add(person);

            String normalizedRole = normalizeRole(person.getPersRole());

            if (isParentRole(normalizedRole)) {
                ParentsEntity parent = childrensParentsService.createParent(person.getPersId());
                parentId = parent.getPareId();
            } else if (isChildRole(normalizedRole)) {
                ChildrensEntity child = childrensParentsService.createChildren(person.getPersId());
                childId = child.getChilId();
            } else {
                employessService.CreateEmployee(person.getPersId());
            }
        }

        if (parentId != null && childId != null) {
            childrensParentsService.createChildrenParentChildren(childId, parentId);
        }

        return savedPersonsList;
    }

    public PersonsEntity updatePersons(PersonsEntity personsRequest, Long id) {
        PersonsEntity person = personsRepository.findById(id)
                .orElseThrow(() -> new com.Vaku.Vaku.exception.NotFoundException("Persona no encontrada"));

        person.setPersNames(personsRequest.getPersNames());
        person.setPersLastNames(personsRequest.getPersLastNames());
        person.setPersDocument(personsRequest.getPersDocument());

        return personsRepository.save(person);
    }

    public PersonsEntity findByDocument(String document) {
        String cleanDocument = trimToNull(document);
        if (cleanDocument == null) {
            throw new IllegalArgumentException(Constants.INVALID_DOCUMENT_FORMAT.getMessage());
        }

        return personsRepository.findByPersDocument(cleanDocument)
                .orElseThrow(() -> new NotFoundException("No existe una persona registrada con ese documento"));
    }

    private PersonsEntity saveNewPerson(PersonsEntity person) {
        if (person.getPersPassword() != null && !person.getPersPassword().isBlank()) {
            person.setPersPassword(passwordEncoder.encode(person.getPersPassword()));
        }
        return personsRepository.save(person);
    }

    private void sanitizePerson(PersonsEntity person) {
        person.setPersNames(trimToNull(person.getPersNames()));
        person.setPersLastNames(trimToNull(person.getPersLastNames()));
        person.setPersDocument(trimToNull(person.getPersDocument()));
        person.setPersSex(trimToNull(person.getPersSex()));
        person.setPersAddress(trimToNull(person.getPersAddress()));
        person.setPersRole(trimToNull(person.getPersRole()));
        person.setPersPhone(trimToNull(person.getPersPhone()));
        person.setPersEmail(trimToNull(person.getPersEmail()));
        person.setPersPassword(trimToNull(person.getPersPassword()));
    }

    private void validateDuplicateFieldsInRequest(List<PersonsEntity> personsRequest) {
        Set<String> documents = new HashSet<>();
        Set<String> emails = new HashSet<>();
        Set<String> phones = new HashSet<>();

        for (PersonsEntity person : personsRequest) {
            String document = trimToNull(person.getPersDocument());
            if (document != null && !documents.add(document)) {
                throw new AlreadyExistsException(Constants.DOCUMENT_ALREADY_EXISTS.getMessage());
            }

            String email = trimToNull(person.getPersEmail());
            if (email != null) {
                String normalizedEmail = email.toLowerCase(Locale.ROOT);
                if (!emails.add(normalizedEmail)) {
                    throw new AlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS.getMessage());
                }
            }

            String phone = trimToNull(person.getPersPhone());
            if (phone != null && !phones.add(phone)) {
                throw new AlreadyExistsException(Constants.PHONE_ALREADY_EXISTS.getMessage());
            }
        }
    }

    private void validateRoleSupported(String role) {
        String normalizedRole = normalizeRole(role);
        boolean supportedRole = isParentRole(normalizedRole)
                || isChildRole(normalizedRole)
                || isEmployeeRole(normalizedRole);

        if (!supportedRole) {
            throw new IllegalArgumentException(Constants.INVALID_ROLE.getMessage());
        }
    }

    private void validateRequiredFieldsByRole(PersonsEntity person) {
        String normalizedRole = normalizeRole(person.getPersRole());

        if (isParentRole(normalizedRole) || isEmployeeRole(normalizedRole)) {
            if (person.getPersEmail() == null || person.getPersEmail().isBlank()) {
                throw new IllegalArgumentException(Constants.EMAIL_EMPTY.getMessage());
            }
            if (person.getPersPhone() == null || person.getPersPhone().isBlank()) {
                throw new IllegalArgumentException(Constants.PHONE_EMPTY.getMessage());
            }
        }

        if (isEmployeeRole(normalizedRole)
                && (person.getPersPassword() == null || person.getPersPassword().isBlank())) {
            throw new IllegalArgumentException(Constants.PASSWORD_EMPTY.getMessage());
        }
    }

    private void validateFieldFormats(PersonsEntity person) {
        if (person.getPersDocument() == null || !DOCUMENT_PATTERN.matcher(person.getPersDocument()).matches()) {
            throw new IllegalArgumentException(Constants.INVALID_DOCUMENT_FORMAT.getMessage());
        }

        if (person.getPersEmail() != null && !EMAIL_PATTERN.matcher(person.getPersEmail()).matches()) {
            throw new IllegalArgumentException(Constants.INVALID_EMAIL_FORMAT.getMessage());
        }

        if (person.getPersPhone() != null && !PHONE_PATTERN.matcher(person.getPersPhone()).matches()) {
            throw new IllegalArgumentException(Constants.INVALID_PHONE_FORMAT.getMessage());
        }
    }

    private void validateUniqueFieldsInDatabase(PersonsEntity person) {
        if (person.getPersEmail() != null
                && personsRepository.findByPersEmailIgnoreCase(person.getPersEmail()).isPresent()) {
            throw new AlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS.getMessage());
        }

        if (person.getPersPhone() != null
                && personsRepository.findByPersPhone(person.getPersPhone()).isPresent()) {
            throw new AlreadyExistsException(Constants.PHONE_ALREADY_EXISTS.getMessage());
        }
    }

    private boolean isParentRole(String normalizedRole) {
        return "madre".equals(normalizedRole) || "padre".equals(normalizedRole);
    }

    private boolean isChildRole(String normalizedRole) {
        return "nino".equals(normalizedRole);
    }

    private boolean isEmployeeRole(String normalizedRole) {
        return "enfermera".equals(normalizedRole) || "jefe de enfermeria".equals(normalizedRole);
    }

    private String normalizeRole(String role) {
        if (role == null) {
            return "";
        }

        return Normalizer.normalize(role, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
