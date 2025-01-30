package pl.summaryGenerator.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.summaryGenerator.model.CombinedData;
import pl.summaryGenerator.repository.CombinedDataRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Service for generating Excel files containing employee data.
 * This service fetches combined data from the repository and formats it into an
 * Excel file.
 */
@Service
public class ExcelService {

    private final CombinedDataRepository combinedDataRepository;

    /**
     * Constructs an ExcelService with the specified CombinedDataRepository.
     *
     * @param combinedDataRepository the repository used to fetch combined data
     */
    public ExcelService(CombinedDataRepository combinedDataRepository) {
        this.combinedDataRepository = combinedDataRepository;
    }

    /**
     * Generates an Excel file containing employee data.
     *
     * @return a byte array representing the generated Excel file
     * @throws IOException if an error occurs during file generation
     */
    public byte[] generateExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Employee Data");

            // Add Header Row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Imię", "Nazwisko", "Email", "Telefon", "Data zatrudnienia", "Stanowisko", "Zespół" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            // Fetch data from the repository
            List<CombinedData> dataList = combinedDataRepository.get();

            // Add Data Rows
            for (int i = 0; i < dataList.size(); i++) {
                Row row = sheet.createRow(i + 1);
                CombinedData data = dataList.get(i);

                row.createCell(0).setCellValue(data.getFirstName());
                row.createCell(1).setCellValue(data.getLastName());
                row.createCell(2).setCellValue(data.getEmail());
                row.createCell(3).setCellValue(data.getPhone());
                row.createCell(4).setCellValue(data.getHireDate().toString());
                row.createCell(5).setCellValue(data.getRole());
                row.createCell(6).setCellValue(data.getTeam());
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    /**
     * Creates a cell style for header cells in the Excel sheet.
     *
     * @param workbook the workbook to which the style will be applied
     * @return a CellStyle object configured for header cells
     */
    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
