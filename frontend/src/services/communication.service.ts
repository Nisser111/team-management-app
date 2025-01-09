import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ApiResponse } from "../interfaces/ApiResponse.interface";
import { ErrorDialogComponent } from "../components/error-info-dialog/error-info-dialog.component";
import { MatDialog } from "@angular/material/dialog";

/**
 * Service for handling communication with the user.
 */
@Injectable({
  providedIn: "root",
})
export class CommunicationService {
  constructor(
    private snackBar: MatSnackBar,
    private errorDialogComponent: MatDialog
  ) {}

  /**
   * Displays an information message to the user.
   * If an action callback is provided, it is executed when the user clicks the action button.
   *
   * @param message - The information message to display.
   * @param actionCallback - The callback function to execute when the user clicks the action button.
   */
  showInfo(message: string, actionCallback?: Function) {
    const snackBarRef = this.snackBar.open(message, "Ok", {
      duration: 3000,
    });

    if (actionCallback)
      snackBarRef.onAction().subscribe(() => actionCallback());
  }

  /**
   * Displays an error message to the user.
   * If the error is specific, it displays a more detailed error dialog when the user clicks the action button.
   *
   * @param err - The error object to display.
   */
  showError(err: any) {
    const {
      error: { error: resError, message, success },
    } = err;
    const data = {
      header: "Wystąpił błąd",
      message: "",
      specyficError: resError || "nieokreślony",
    };

    if (success === undefined) {
      data.message = "Cos poszło nie tak. Przepraszamy za utrudnienia.";
    }

    // Opening a snack bar with the error message and an action to view more details
    const snackBarRef = this.snackBar.open(message, "Więcej", {
      duration: 3000,
    });

    // Listening for the action on the snack bar
    snackBarRef.onAction().subscribe(() => {
      // Opening a dialog with more detailed error information when the user clicks on "Więcej"
      const dialogRef = this.errorDialogComponent.open(ErrorDialogComponent, {
        data,
      });

      // Listening for the close event on the dialog and closing it when emitted
      dialogRef.componentInstance.onClose.subscribe(() => {
        dialogRef.close();
      });
    });
  }
}
