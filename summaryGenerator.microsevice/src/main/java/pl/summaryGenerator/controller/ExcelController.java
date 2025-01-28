package pl.summaryGenerator.controller;

import org.springframework.http.HttpHeaders;
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

/**
 * Controller for handling Excel file downloads.
 * 
 * This controller provides an endpoint to download an Excel file containing
 * summarized data. It uses the ExcelService to generate the file and returns
 * it as a byte array in the response.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/excel")
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
     * Endpoint to download the generated Excel file.
     *
     * @return ResponseEntity containing the Excel file as a byte array
     * @throws IOException if an error occurs during file generation
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {

        // Generate Excel file
        byte[] excelFile = excelService.generateExcel();

        // Return the file as a response
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=summary.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelFile);
    }

    /**
     *
     * @return Endpoint to handle rabbitmq sender
     * @throws IOException
     */
    @GetMapping("/get")
    public ResponseEntity<byte[]> getExcel() throws IOException {

        rabbitmqProducer.sender();

        return (ResponseEntity<byte[]>) ResponseEntity.ok();
    }
}
