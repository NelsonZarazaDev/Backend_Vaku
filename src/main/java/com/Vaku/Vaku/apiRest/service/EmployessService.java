package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.EmployeesEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.model.response.EmployeesResponse;
import com.Vaku.Vaku.apiRest.repository.EmployeesRepository;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.exception.AlreadyExistsException;
import com.Vaku.Vaku.exception.NotFoundException;
import com.Vaku.Vaku.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmployessService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{7,15}$");

    private final EmployeesRepository employeesRepository;
    private final PersonsRepository personsRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeesEntity CreateEmployee(Long personEmployee) {
        PersonsEntity person = personsRepository.findById(personEmployee)
                .orElseThrow(() -> new NotFoundException("Persona no encontrada"));

        if (employeesRepository.findByPersons_PersId(personEmployee).isPresent()) {
            throw new AlreadyExistsException(Constants.EMPLOYEE_ALREADY_EXISTS.getMessage());
        }

        if (person.getPersPassword() == null || person.getPersPassword().isBlank()) {
            throw new IllegalArgumentException(Constants.PASSWORD_EMPTY.getMessage());
        }

        if (!person.getPersPassword().startsWith("$2a$")
                && !person.getPersPassword().startsWith("$2b$")
                && !person.getPersPassword().startsWith("$2y$")) {
            person.setPersPassword(passwordEncoder.encode(person.getPersPassword()));
            personsRepository.save(person);
        }

        EmployeesEntity employee = new EmployeesEntity();
        employee.setEmplDateAdmission(LocalDate.now());
        employee.setEmplState(true);
        employee.setPersons(person);
        return employeesRepository.save(employee);
    }

    public PersonsEntity updateEmployees(PersonsEntity personRequest, String token, boolean state) {
        EmployeesEntity employee = employeesRepository.findByEmplToken(token)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));

        PersonsEntity person = employee.getPersons();
        sanitizePerson(personRequest);
        validateRole(personRequest.getPersRole());
        validateEmailAndPhone(personRequest.getPersEmail(), personRequest.getPersPhone());

        validateUniqueForUpdate(person, personRequest.getPersEmail(), personRequest.getPersPhone());

        person.setPersNames(personRequest.getPersNames());
        person.setPersLastNames(personRequest.getPersLastNames());
        person.setPersEmail(personRequest.getPersEmail());
        person.setPersPhone(personRequest.getPersPhone());
        person.setPersRole(personRequest.getPersRole());

        if (personRequest.getPersPassword() != null && !personRequest.getPersPassword().isBlank()) {
            person.setPersPassword(passwordEncoder.encode(personRequest.getPersPassword()));
        }

        personsRepository.save(person);

        employee.setEmplState(state);
        employeesRepository.save(employee);

        return person;
    }

    public Set<EmployeesResponse> findByJsonEmployeeEmail(String email) {
        return employeesRepository.findByJsonEmployeeEmail(email);
    }

    public Set<EmployeesResponse> findByAllEmployee() {
        return employeesRepository.findByAllEmployee();
    }

    public Page<EmployeesResponse> findByAllEmployee(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeesRepository.findByAllEmployee(pageable);
    }

    private void validateUniqueForUpdate(PersonsEntity currentPerson, String email, String phone) {
        if (email != null) {
            personsRepository.findByPersEmailIgnoreCase(email)
                    .filter(existing -> !existing.getPersId().equals(currentPerson.getPersId()))
                    .ifPresent(existing -> {
                        throw new AlreadyExistsException(Constants.EMAIL_ALREADY_EXISTS.getMessage());
                    });
        }

        if (phone != null) {
            personsRepository.findByPersPhone(phone)
                    .filter(existing -> !existing.getPersId().equals(currentPerson.getPersId()))
                    .ifPresent(existing -> {
                        throw new AlreadyExistsException(Constants.PHONE_ALREADY_EXISTS.getMessage());
                    });
        }
    }

    private void validateRole(String role) {
        String normalizedRole = normalize(role);
        if (!"enfermera".equals(normalizedRole) && !"jefe de enfermeria".equals(normalizedRole)) {
            throw new IllegalArgumentException(Constants.INVALID_ROLE.getMessage());
        }
    }

    private void validateEmailAndPhone(String email, String phone) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(Constants.EMAIL_EMPTY.getMessage());
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException(Constants.PHONE_EMPTY.getMessage());
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(Constants.INVALID_EMAIL_FORMAT.getMessage());
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException(Constants.INVALID_PHONE_FORMAT.getMessage());
        }
    }

    private void sanitizePerson(PersonsEntity personRequest) {
        personRequest.setPersNames(trimToNull(personRequest.getPersNames()));
        personRequest.setPersLastNames(trimToNull(personRequest.getPersLastNames()));
        personRequest.setPersEmail(trimToNull(personRequest.getPersEmail()));
        personRequest.setPersPhone(trimToNull(personRequest.getPersPhone()));
        personRequest.setPersPassword(trimToNull(personRequest.getPersPassword()));
        personRequest.setPersRole(trimToNull(personRequest.getPersRole()));
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return Normalizer.normalize(value, Normalizer.Form.NFD)
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
