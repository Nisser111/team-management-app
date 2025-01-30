import { Component, EventEmitter, Output } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { SummaryService } from "../../services/summary.service";
import { CommunicationService } from "../../services/communication.service";
import { DownloadFileService } from "../../services/download-file.service";

@Component({
  selector: "app-get-summary-btn",
  imports: [MatButton],
  template: `
    <button mat-button (click)="handleClick()">Pobierz podsumowanie</button>
  `,
  styles: ``,
})
export class GetSummaryBtnComponent {
  constructor(
    private summaryService: SummaryService,
    private communicationService: CommunicationService,
    private downloadFileService: DownloadFileService
  ) {}

  handleClick() {
    this.summaryService.getFullSummary().subscribe({
      next: (data) => {
        console.log(data);

        if ("error" in data) {
          this.communicationService.showError(data.error);
        } else {
          this.downloadFileService.download(data, "Podsumowanie.xlsx");
        }
      },
      error: (data) => this.communicationService.showError(data.error),
    });
  }
}
