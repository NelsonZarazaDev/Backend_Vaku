package com.Vaku.Vaku.apiRest.service;

import com.Vaku.Vaku.apiRest.model.entity.EmployeesEntity;
import com.Vaku.Vaku.apiRest.model.entity.PersonsEntity;
import com.Vaku.Vaku.apiRest.repository.ChildrensRepository;
import com.Vaku.Vaku.apiRest.repository.EmployeesRepository;
import com.Vaku.Vaku.apiRest.repository.PersonsRepository;
import com.Vaku.Vaku.dto.AuthResponse;
import com.Vaku.Vaku.dto.LoginChildRequest;
import com.Vaku.Vaku.dto.LoginEmployeeRequest;
import com.Vaku.Vaku.exception.AuthenticationFailedException;
import com.Vaku.Vaku.security.JWTService;
import com.Vaku.Vaku.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final PersonsRepository personsRepository;
    private final EmployeesRepository employeesRepository;
    private final ChildrensRepository childrensRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthResponse login(LoginEmployeeRequest request) {
        PersonsEntity persons = personsRepository.findByPersEmailIgnoreCase(request.getPersEmail())
                .orElseThrow(() -> new AuthenticationFailedException(Constants.CREDENTIAL_INVALID.getMessage()));

        Optional<EmployeesEntity> employees = employeesRepository.findByPersons(persons);
        boolean validCredentials = passwordEncoder.matches(request.getPersPassword(), persons.getPersPassword());
        boolean activeEmployee = employees.isPresent() && employees.get().isEmplState();

        if (!validCredentials || !activeEmployee) {
            throw new AuthenticationFailedException(Constants.CREDENTIAL_INVALID.getMessage());
        }

        String token = jwtService.getToken(persons);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse loginChild(LoginChildRequest request) {
        String document = normalizeDocument(request.getPersDocument());
        LocalDate birthDate = request.getPersDateBirth();

        if (document == null || document.isBlank()) {
            throw new AuthenticationFailedException(Constants.CREDENTIAL_INVALID.getMessage());
        }
        if (birthDate == null) {
            throw new AuthenticationFailedException(Constants.CREDENTIAL_INVALID.getMessage());
        }

        PersonsEntity persons = personsRepository.findByPersDocument(document)
                .orElseThrow(() -> new AuthenticationFailedException(Constants.CREDENTIAL_INVALID.getMessage()));

        boolean childRole = isChildRole(persons.getPersRole());
        boolean childAccount = childrensRepository.findByPersons_PersId(persons.getPersId()).isPresent();
        boolean birthDateMatch = birthDate != null && birthDate.equals(persons.getPersDateBirth());

        if (!childRole && !childAccount) {
            throw new AuthenticationFailedException(Constants.CREDENTIAL_INVALID.getMessage());
        }
        if (!birthDateMatch) {
            throw new AuthenticationFailedException(Constants.CREDENTIAL_INVALID.getMessage());
        }

        String token = jwtService.getToken(persons);
        return AuthResponse.builder().token(token).build();
    }

    private boolean isChildRole(String role) {
        if (role == null) {
            return false;
        }

        String normalizedRole = Normalizer.normalize(role, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();

        return "nino".equals(normalizedRole);
    }

    private String normalizeDocument(String document) {
        if (document == null) {
            return null;
        }

        String onlyDigits = document.replaceAll("\\D", "");
        return onlyDigits.isBlank() ? null : onlyDigits;
    }
}
