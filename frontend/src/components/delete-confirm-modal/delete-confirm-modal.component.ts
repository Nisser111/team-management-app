import { Component, Inject } from "@angular/core";
import { MatButton } from "@angular/material/button";
import {
  MatDialogRef,
  MatDialogActions,
  MatDialogContent,
  MAT_DIALOG_DATA,
  MatDialogModule,
} from "@angular/material/dialog";

@Component({
  selector: "app-delete-confirm-modal",
  imports: [MatDialogActions, MatDialogContent, MatButton, MatDialogModule],
  standalone: true,
  template: `
    <h2 mat-dialog-title>{{ data.textToShow }}</h2>
    <mat-dialog-content>
      <p>Ta zmiana jest nieodwracalna.</p>
    </mat-dialog-content>
    <mat-dialog-actions>
      <button mat-button (click)="onCancel()">Anuluj</button>
      <button mat-button (click)="onConfirm()" class="delete-button">
        Usuń
      </button>
    </mat-dialog-actions>
  `,
  styleUrls: ["./delete-confirm-modal.scss"],
})
export class DeleteConfirmModalComponent {
  constructor(
    public dialogRef: MatDialogRef<DeleteConfirmModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { textToShow: string }
  ) {}

  onConfirm(): void {
    this.dialogRef.close(true); // Close the dialog and return true
  }

  onCancel(): void {
    this.dialogRef.close(false); // Close the dialog and return false
  }
}
