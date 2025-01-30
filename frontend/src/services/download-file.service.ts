import { Injectable } from "@angular/core";

/**
 * Service for downloading files in the application.
 * This service provides a method to download files either as a Blob or a string URL.
 */
@Injectable({
  providedIn: "root",
})
export class DownloadFileService {
  /**
   * Downloads a file by creating a temporary link element and triggering a click event.
   *
   * @param data - The file data to be downloaded, which can be a Blob or a string URL.
   * @param filename - The name to be used for the downloaded file.
   */
  download(data: Blob | string, filename: string) {
    // Create URL for the file
    const url = data instanceof Blob ? window.URL.createObjectURL(data) : data;

    // Create temporary link element
    const link = document.createElement("a");
    link.href = url;
    link.download = filename;

    // Append to document, trigger click and cleanup
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);

    // Release the URL object if blob was provided
    if (data instanceof Blob) {
      window.URL.revokeObjectURL(url);
    }
  }
}
