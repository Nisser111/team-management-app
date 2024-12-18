import { MatSnackBar } from "@angular/material/snack-bar";
import { ApiResponse } from "../../interfaces/ApiResponse.interface";
import { MatDialog } from "@angular/material/dialog";
import { ErrorDialogComponent } from "../error-info-dialog/error-info-dialog.component";

/**
 * Handles API errors by displaying a snack bar with the error message and an option to view more details.
 * If the user clicks on "Więcej", a dialog with more detailed error information is displayed.
 *
 * @param error - The error object received from the API. It can be either an ApiResponse object or any other object.
 * @param snackBar - The MatSnackBar service for displaying snack bars.
 * @param dialog - The MatDialog service for opening dialogs.
 */
export function apiErrorHandler(
  error: ApiResponse | any,
  snackBar: MatSnackBar,
  dialog: MatDialog
) {
  // Extracting the error message and specific error from the error object
  const { error: resError, message } = error;

  // Opening a snack bar with the error message and an action to view more details
  const snackBarRef = snackBar.open(message, "Więcej", {
    duration: 3000,
  });

  // Listening for the action on the snack bar
  snackBarRef.onAction().subscribe(() => {
    // Opening a dialog with more detailed error information when the user clicks on "Więcej"
    const dialogRef = dialog.open(ErrorDialogComponent, {
      data: {
        header: "Wystąpił błąd",
        message: message,
        specificError: resError || "nieokreślony",
      },
    });

    // Listening for the close event on the dialog and closing it when emitted
    dialogRef.componentInstance.onClose.subscribe(() => {
      dialogRef.close();
    });
  });
}
