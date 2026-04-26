package com.Vaku.Vaku.pdfVaccinationCard;

import com.Vaku.Vaku.apiRest.model.response.VaccinesResponse;
import com.Vaku.Vaku.apiRest.repository.VaccinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CarnetAssemblerService {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

    @Autowired
    private PdfService pdfService;

    @Autowired
    private VaccinesRepository vaccinesRepository;

    public byte[] generarCarnetPDF(ParentChildInfoDTO info, List<AplicacionVacunaDTO> aplicaciones) {
        PacienteDTO paciente = new PacienteDTO();
        Long childId = info.getChildId();

        paciente.setNombre(info.getChildNames() + " " + info.getChildLastNames());
        paciente.setDocumento(info.getChildDocument());
        paciente.setFechaNacimiento(info.getChildBirthDate());
        paciente.setNombrePadre(info.getParentNames() + " " + info.getParentLastNames());
        paciente.setDireccion(info.getChildAddress() != null ? info.getChildAddress() : info.getParentAddress());
        paciente.setTelefono(info.getParentPhone());
        paciente.setCorreo(info.getParentEmail());

        List<VaccinesResponse> vacunasFromDb = vaccinesRepository.findVaccinesByChildId(childId);
        List<VacunaDTO> vacunas = new ArrayList<>();

        List<VaccinesResponse> sortedVaccines = vacunasFromDb.stream()
                .sorted(Comparator
                        .comparingInt((VaccinesResponse v) -> ageOrder(v.getVaccAgeDose()))
                        .thenComparingInt(v -> dosageOrder(v.getVaccDosage()))
                        .thenComparing(VaccinesResponse::getVaccName, String.CASE_INSENSITIVE_ORDER))
                .toList();

        for (VaccinesResponse vacunaResponse : sortedVaccines) {
            VacunaDTO vacuna = new VacunaDTO();

            vacuna.setNombreVacuna(vacunaResponse.getVaccName());
            vacuna.setEdad(vacunaResponse.getVaccAgeDose());
            vacuna.setDosis(vacunaResponse.getVaccDosage());

            if (vacunaResponse.getVaapDateApplication() != null) {
                vacuna.setFechaAplicacion(vacunaResponse.getVaapDateApplication().toString());
            } else {
                vacuna.setFechaAplicacion("-");
            }

            if (vacunaResponse.getVaapNextAppointmentDate() != null) {
                vacuna.setFechaProximaCita(vacunaResponse.getVaapNextAppointmentDate().toString());
            } else {
                vacuna.setFechaProximaCita("-");
            }

            vacuna.setLaboratorio(vacunaResponse.getInveLaboratory());
            vacuna.setNumeroLote(vacunaResponse.getInveLot());
            vacunas.add(vacuna);
        }

        return pdfService.generarCarnet(paciente, vacunas);
    }

    public AplicacionVacunaDTO buscarAplicacion(String nombreVacuna, List<AplicacionVacunaDTO> aplicaciones) {
        return aplicaciones.stream()
                .filter(a -> a.getVaccName() != null &&
                        a.getVaccName().trim().equalsIgnoreCase(nombreVacuna.trim()))
                .findFirst()
                .orElse(null);
    }

    private int ageOrder(String ageText) {
        if (ageText == null || ageText.isBlank()) {
            return Integer.MAX_VALUE;
        }

        String normalized = normalize(ageText);

        if (normalized.contains("recien nac")) {
            return 0;
        }

        Matcher matcher = NUMBER_PATTERN.matcher(normalized);
        if (!matcher.find()) {
            return Integer.MAX_VALUE - 1;
        }

        int value = Integer.parseInt(matcher.group(1));

        if (normalized.contains("ano") || normalized.contains("anos")) {
            return value * 12;
        }

        return value;
    }

    private int dosageOrder(String dosageText) {
        if (dosageText == null || dosageText.isBlank()) {
            return Integer.MAX_VALUE;
        }

        String normalized = normalize(dosageText);

        if (normalized.contains("recien")) {
            return 0;
        }
        if (normalized.contains("primera")) {
            return 1;
        }
        if (normalized.contains("segunda")) {
            return 2;
        }
        if (normalized.contains("tercera")) {
            return 3;
        }
        if (normalized.contains("unica")) {
            return 4;
        }
        if (normalized.contains("primer refuerzo")) {
            return 5;
        }
        if (normalized.contains("segundo refuerzo")) {
            return 6;
        }
        if (normalized.contains("refuerzo")) {
            return 7;
        }

        return Integer.MAX_VALUE - 1;
    }

    private String normalize(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }
}
