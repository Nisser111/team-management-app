import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable, catchError, of } from "rxjs";

/**
 * Service for managing summary data in the application.
 * This service provides methods to retrieve the full summary as a downloadable file.
 */
@Injectable({
  providedIn: "root",
})
export class SummaryService {
  private apiUrl = "http://localhost:8080"; // Base URL for the API

  constructor(private http: HttpClient) {}

  /**
   * Retrieves the full summary from the server as a Blob.
   * This method sends a GET request to the server to download the summary file.
   *
   * @returns An Observable that resolves to a Blob containing the summary file,
   *          or an object with an error message if the download fails.
   */
  getFullSummary(): Observable<Blob | { error: string }> {
    return this.http
      .get(`${this.apiUrl}/summary/download`, {
        headers: {
          Accept:
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        },
        responseType: "blob",
      })
      .pipe(
        map((response) => response as Blob),
        catchError((error) => {
          console.error("Download error:", error);
          return of({ error: error.message || "Failed to download file" });
        })
      );
  }
}
