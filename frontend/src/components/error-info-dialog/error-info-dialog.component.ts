import { Component, EventEmitter, Inject, Output } from "@angular/core";
import { MatButton } from "@angular/material/button";
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogTitle,
} from "@angular/material/dialog";

@Component({
  selector: "app-error-dialog",
  imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatButton],
  template: `
    <h1 mat-dialog-title>{{ data.header }}</h1>
    <div mat-dialog-content>
      <p>{{ data.message }}</p>
      <p><strong>Błąd:</strong> {{ data.specificError }}</p>
    </div>
    <div mat-dialog-actions>
      <button mat-button (click)="close()">Zamknij</button>
    </div>
  `,
})
export class ErrorDialogComponent {
  @Output() onClose = new EventEmitter();
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: { header: string; message: string; specificError: string }
  ) {}

  close() {
    this.onClose.emit();
  }
}
