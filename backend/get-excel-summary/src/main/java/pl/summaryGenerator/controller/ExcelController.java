package pl.summaryGenerator.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.summaryGenerator.config.RabbitmqProducer;
import pl.summaryGenerator.repository.CombinedDataRepository;
import pl.summaryGenerator.service.ExcelService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling Excel file downloads.
 * 
 * This controller provides an endpoint to download an Excel file containing
 * summarized data. It uses the ExcelService to generate the file and returns
 * it as a byte array in the response.
 */
@RestController
@RequestMapping("/summary")
public class ExcelController {

    private final ExcelService excelService;
    private final RabbitmqProducer rabbitmqProducer;

    /**
     * Constructs an ExcelController with the specified ExcelService.
     *
     * @param excelService           the service used to generate Excel files
     * @param combinedDataRepository the repository for combined data (not used in
     *                               this controller)
     */
    public ExcelController(ExcelService excelService, CombinedDataRepository combinedDataRepository, RabbitmqProducer rabbitmqProducer) {
        this.excelService = excelService;
        this.rabbitmqProducer = rabbitmqProducer;
    }


    /**
     * Endpoint for downloading an Excel file.
     *
     * This method generates an Excel file using the `excelService`, and if successful,
     * returns the file as an attachment in the response. If an error occurs during the
     * generation of the Excel file, it catches the exception and returns an error response.
     *
     * @return ResponseEntity<Object> - A ResponseEntity containing either the Excel file
     *                                  as a byte array or an error message.
     */
    @GetMapping("/download")
    public ResponseEntity<Object> downloadExcel() {
        try {
            // Generate Excel file
            byte[] excelFile = excelService.generateExcel();

            // Return the file as a response
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=summary.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelFile);
        } catch (IOException e) {
            // Handle the error and return a custom error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Błąd podczas generowania podsumowania.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
