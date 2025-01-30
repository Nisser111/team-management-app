import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable, catchError, throwError } from "rxjs";

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
   * Handles errors that occur when attempting to retrieve a Blob response.
   * If the error response is a Blob, it attempts to read it as text and parse it as JSON.
   * If parsing fails, it returns a generic error message.
   *
   * @param error - The HttpErrorResponse object containing error details.
   * @returns A Promise that resolves with the parsed error object or rejects with a generic error.
   */
  private handleBlobError(error: HttpErrorResponse) {
    if (error.error instanceof Blob) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          try {
            const errorObject = JSON.parse(reader.result as string);
            errorObject.message = "Wystąpił nieoczekiwany błąd.";
            reject({ error: errorObject });
          } catch (e) {
            reject({ error: { message: "Wystąpił nieoczekiwany błąd." } });
          }
        };
        reader.readAsText(error.error);
      });
    } else {
      return throwError(() => error);
    }
  }

  /**
   * Retrieves the full summary from the server as a Blob.
   * This method sends a GET request to the server to download the summary file.
   *
   * @returns An Observable that resolves to a Blob containing the summary file,
   *          or an object with an error message if the download fails.
   */
  getFullSummary(): Observable<any> {
    return this.http
      .get(`${this.apiUrl}/summary/download`, {
        headers: {
          Accept:
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/json",
        },
        responseType: "blob",
      })
      .pipe(
        map((response) => response as Blob),
        catchError((error) => this.handleBlobError(error))
      );
  }
}
