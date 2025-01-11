import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { TeamService } from "./teams.service";
import { CommunicationService } from "./communication.service";
import { AddEditTeamsModalComponent } from "../components/add-edit-teams-modal/add-edit-teams-modal.component";
import { DeleteConfirmModalComponent } from "../components/delete-confirm-modal/delete-confirm-modal.component";

/**
 * Service responsible for managing team operations, including renaming and deleting teams.
 * It utilizes MatDialog to open modal dialogs for user interaction and TeamService for backend operations.
 * CommunicationService is used for displaying information and error messages to the user.
 */
@Injectable({
  providedIn: "root",
})
export class TeamManagementService {
  /**
   * Constructor initializes the service with dependencies.
   * @param teamService Service for managing team data on the backend.
   * @param communicationService Service for displaying messages to the user.
   */
  constructor(
    private teamService: TeamService,
    private communicationService: CommunicationService
  ) {}

  /**
   * Opens a dialog for renaming the team and updates the team name if confirmed.
   * This method initializes a dialog with the AddEditTeamsModalComponent, passing the team name as data.
   * It then subscribes to the dialog's afterClosed event to handle the result, which includes the confirmation status and the new team name.
   * If the result is confirmed, it calls the TeamService to update the team name on the server.
   * If the update is successful, it displays an info message; otherwise, it displays an error message.
   *
   * @param dialog The MatDialog service to open the dialog.
   * @param teamName The current name of the team.
   * @param teamId The ID of the team to be renamed.
   */
  teamRename(dialog: MatDialog, teamName: string, teamId: number): void {
    const dialogRef = dialog.open(AddEditTeamsModalComponent, {
      data: { dialogTitle: "Zmień nazwę zespołu", oldName: teamName },
    });

    dialogRef
      .afterClosed()
      .subscribe((result: { confirmed: boolean; newName: string }) => {
        if (result.confirmed) {
          this.teamService.updateTeam(teamId, result.newName).subscribe({
            next: ({ message }) => this.communicationService.showInfo(message),
            error: (err) => this.communicationService.showError(err),
          });
        }
      });
  }

  /**
   * Opens a confirmation dialog to delete the current team.
   * If the user confirms, it calls the deleteTeam method to delete the team.
   * This method initializes a dialog with the DeleteConfirmModalComponent, passing a confirmation message as data.
   * It then subscribes to the dialog's afterClosed event to handle the result, which is a boolean indicating user confirmation.
   * If the result is true, it calls the deleteTeam method to proceed with the deletion.
   *
   * @param dialog The MatDialog service to open the dialog.
   * @param teamName The name of the team to be deleted.
   * @param teamId The ID of the team to be deleted.
   * @param employees The array of employees associated with the team.
   * @param onSuccess Callback function to be executed after successful deletion.
   */
  confirmTeamDelete(
    dialog: MatDialog,
    teamName: string,
    teamId: number,
    employees: any[],
    onSuccess: () => void
  ): void {
    const dialogRef = dialog.open(DeleteConfirmModalComponent, {
      data: { textToShow: `Czy napewno checsz usunąć ${teamName}?` },
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.deleteTeam(teamId, employees, onSuccess);
      }
    });
  }

  /**
   * Deletes the current team.
   * This method uses the TeamService to delete the team from the server.
   * If the deletion is successful, it filters out the deleted team's employees and hides the component.
   * It also displays an info message to the user.
   * If an error occurs, it displays an error message.
   *
   * @param teamId The ID of the team to be deleted.
   * @param employees The array of employees associated with the team.
   * @param onSuccess Callback function to be executed after successful deletion.
   */
  private deleteTeam(
    teamId: number,
    employees: any[],
    onSuccess: () => void
  ): void {
    this.teamService.deleteTeam(teamId).subscribe({
      next: ({ message }) => {
        this.communicationService.showInfo(message);
        employees.splice(0, employees.length);
        onSuccess();
      },
      error: (err) => this.communicationService.showError(err),
    });
  }

  /**
   * Adds a new team.
   * This method uses the MatDialog service to open a dialog for adding a new team.
   * If the user confirms the action, it uses the TeamService to add the new team to the server.
   * If the addition is successful, it displays an info message to the user and refreshes the list of teams.
   * If an error occurs, it displays an error message.
   *
   * @param dialog The MatDialog service to open the dialog.
   * @param fetchTeams Callback function to be executed after adding a new team.
   */
  addTeam(dialog: MatDialog, fetchTeams: () => void): void {
    const dialogRef = dialog.open(AddEditTeamsModalComponent, {
      data: { dialogTitle: "Dodaj nowy zespół", oldName: "" },
    });

    interface DialogResult {
      confirmed: boolean;
      newName: string;
    }

    dialogRef.afterClosed().subscribe((result: DialogResult) => {
      if (result?.confirmed) {
        this.teamService.addTeam(result.newName).subscribe({
          next: ({ message }) => {
            this.communicationService.showInfo(message);
            fetchTeams(); // Refresh the list of teams after adding a new one
          },
          error: (err) => this.communicationService.showError(err),
        });
      }
    });
  }
}
