package com.Vaku.Vaku.pdfVaccinationCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generarCarnet")
    public ResponseEntity<byte[]> generarCarnet(@RequestBody CarnetRequest request) {
        byte[] pdf = pdfService.generarCarnet(request.getPaciente(), request.getVacunas());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=carnet.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}