package com.Vaku.Vaku.Report;

import com.Vaku.Vaku.apiRest.model.entity.InventoryHistoryEntity;
import com.Vaku.Vaku.apiRest.model.response.InventoriesResponse;
import com.Vaku.Vaku.apiRest.model.response.InventoryHistoryResponse;
import com.Vaku.Vaku.apiRest.repository.InventoryHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class ExcelService implements ReportService {

    private InventoryHistoryRepository inventoryHistoryRepository;

    @Override
    public byte[] readFile() {
        try {
            this.createReport();
            var path = Paths.get(REPORTS_PATH, String.format(FILE_NAME, LocalDate.now().getMonth())).toAbsolutePath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createReport() {
        var workBook = new XSSFWorkbook();
        //Esto es una sola hoja de excel
        var sheet = workBook.createSheet(SHEET_NAME);
        //Configurar pagina, primero se le pasa la posicion es como una matriz y luego el tamano
        sheet.setColumnWidth(0, 9000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);

        //Se le indica que cree una fila en el indice
        var header = sheet.createRow(0);
        var headerStyle = workBook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        var font = workBook.createFont();
        font.setFontName(FONT_TYPE);
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        //Crear celdas
        var headerCell = header.createCell(0);
        headerCell.setCellValue(COLUMN_NAME_VACINNE);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue(COLUMN_INVENTORY_DATE);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue(COLUMN_INVENTORY_LABORATORY);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue(COLUMN_INVENTORY_LOTE);
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue(COLUMN_INVENTORY_QUANTITY);
        headerCell.setCellStyle(headerStyle);

        var style = workBook.createCellStyle();
        style.setWrapText(true);
        var createHelper = workBook.getCreationHelper();
        var dateTimeCellStyle = workBook.createCellStyle();
        dateTimeCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss"));

        var inventoryHistorys = this.inventoryHistoryRepository.getVaccineHistoryRaw();
        var rowPos = 1;
        for (InventoryHistoryResponse inventoryHistory : inventoryHistorys) {
            var row = sheet.createRow(rowPos);
            var cell = row.createCell(0);
            cell.setCellValue(inventoryHistory.getVaccName());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(java.sql.Timestamp.valueOf(inventoryHistory.getInhiDate()));
            cell.setCellStyle(dateTimeCellStyle);

            cell = row.createCell(2);
            cell.setCellValue(inventoryHistory.getInveLaboratory());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(inventoryHistory.getInveLot());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue(inventoryHistory.getInveQuantity());
            cell.setCellStyle(style);

            rowPos++;
        }

        var report = new File(String.format(REPORTS_PATH_WITH_NAME, LocalDate.now().getMonth()));
        var path = report.getAbsolutePath();
        var fileLocation = path + FILE_TYPE;
        try (var outputStream = new FileOutputStream(fileLocation)) {
            workBook.write(outputStream);
            workBook.close(); //Por seguridad se debe cerrar
        } catch (IOException e) {
            log.error("Cant create Excel", e);
            throw new RuntimeException();
        }
    }

    //Nombre de la pagina
    private static final String SHEET_NAME = "Historial";
    //Tipo de fuente
    private static final String FONT_TYPE = "Arial";
    //Columnas
    private static final String COLUMN_NAME_VACINNE = "Vacuna";
    private static final String COLUMN_INVENTORY_DATE = "Fecha";
    private static final String COLUMN_INVENTORY_LABORATORY = "Laboratorio";
    private static final String COLUMN_INVENTORY_LOTE = "Lote";
    private static final String COLUMN_INVENTORY_QUANTITY = "Cantidad";

    private static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
    private static final String REPORTS_PATH = "reports";
    private static final String FILE_TYPE = ".xlsx";
    private static final String FILE_NAME = "Sales-%s.xlsx";
}
