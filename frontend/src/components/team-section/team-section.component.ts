import { Component, Input } from "@angular/core";
import { MatTableModule } from "@angular/material/table";
import { CommonModule, NgIf } from "@angular/common";
import { MatButton, MatButtonModule } from "@angular/material/button";
import { Employee } from "../../interfaces/Employee.interface";
import { EmployeeOptionsListComponent } from "../employee-options-list/employee-options-list.component";
import { TeamMenuButtonComponent } from "../team-menu-button/team-menu-button.component";
import { TeamService } from "../../services/teams.service";
import { DeleteConfirmModalComponent } from "../delete-confirm-modal/delete-confirm-modal.component";
import { MatDialog, MatDialogModule } from "@angular/material/dialog";
import { AddEditTeamsModalComponent } from "../add-edit-teams-modal/add-edit-teams-modal.component";
import { EmployeesService } from "../../services/employees.service";
import { AddEditEmployeeModalComponent } from "../add-edit-employee-modal/add-edit-employee-modal.component";
import { CommunicationService } from "../../services/communication.service";

@Component({
  selector: "app-team-section",
  standalone: true,
  imports: [
    MatTableModule,
    CommonModule,
    MatButtonModule,
    MatButton,
    EmployeeOptionsListComponent,
    TeamMenuButtonComponent,
    NgIf,
    MatDialogModule,
  ],
  template: `
    <section class="team" [attr.data-team-id]="teamId" *ngIf="showComponent">
      <header>
        <h3>
          {{ teamName }}
        </h3>
        <app-team-menu-button
          (rename)="teamRename()"
          (delete)="confirmTeamDelete()"
        ></app-team-menu-button>
      </header>
      <div class="table-container">
        <table mat-table [dataSource]="employees" class="mat-elevation-z8">
          <!-- ID Column -->
          <ng-container matColumnDef="id">
            <th mat-header-cell *matHeaderCellDef>L. p.</th>
            <td mat-cell *matCellDef="let element; let i = index">
              {{ i + 1 }}
            </td>
          </ng-container>

          <!-- First Name Column -->
          <ng-container matColumnDef="firstName">
            <th mat-header-cell *matHeaderCellDef>Imię</th>
            <td mat-cell *matCellDef="let element">{{ element.firstName }}</td>
          </ng-container>

          <!-- Last Name Column -->
          <ng-container matColumnDef="lastName">
            <th mat-header-cell *matHeaderCellDef>Nazwisko</th>
            <td mat-cell *matCellDef="let element">{{ element.lastName }}</td>
          </ng-container>

          <!-- Email Column -->
          <ng-container matColumnDef="email">
            <th mat-header-cell *matHeaderCellDef>Email</th>
            <td mat-cell *matCellDef="let element">{{ element.email }}</td>
          </ng-container>

          <!-- Phone Column -->
          <ng-container matColumnDef="phone">
            <th mat-header-cell *matHeaderCellDef>Telefon</th>
            <td mat-cell *matCellDef="let element">{{ element.phone }}</td>
          </ng-container>

          <!-- Hire Date Column -->
          <ng-container matColumnDef="hireDate">
            <th mat-header-cell *matHeaderCellDef>Data zatrudnienia</th>
            <td mat-cell *matCellDef="let element">{{ element.hireDate }}</td>
          </ng-container>

          <!-- Role Column -->
          <ng-container matColumnDef="role">
            <th mat-header-cell *matHeaderCellDef>Stanowisko</th>
            <td mat-cell *matCellDef="let element">{{ element.role }}</td>
          </ng-container>

          <!-- Options column -->
          <ng-container matColumnDef="options">
            <th mat-header-cell *matHeaderCellDef>Opcje</th>
            <td mat-cell *matCellDef="let element">
              <app-employee-options-list
                [employee]="element"
                (edit)="openEditEmployeeModal($event)"
                (delete)="confirmEmployeeDelete($event)"
              ></app-employee-options-list>
            </td>
          </ng-container>

          <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
          <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
        </table>

        <!-- Show info if team is empty -->
        <p *ngIf="employees.length === 0" class="no-employees-info">
          Brak pracowników w tym zespole
        </p>
      </div>

      <button
        mat-button
        class="add-new-employee-btn"
        (click)="openAddEmployeeModal()"
      >
        Dodaj pracownika
      </button>
    </section>
  `,
  styleUrls: ["../../styles/employees-tables.scss"],
})
export class TeamSectionComponent {
  @Input() teamName: string = "";
  @Input() teamId: number = -1;
  @Input() employees: Employee[] = [];

  showComponent: boolean = true;

  displayedColumns: string[] = [
    "id",
    "firstName",
    "lastName",
    "email",
    "phone",
    "hireDate",
    "role",
    "options",
  ];

  constructor(
    private teamService: TeamService,
    private employeesService: EmployeesService,
    private dialog: MatDialog,
    private addEditTeamsDialog: MatDialog,
    private communicationService: CommunicationService
  ) {}

  // Employees

  /**
   * Opens a modal to edit an existing employee's data.
   * This method initializes a dialog with the AddEditEmployeeModalComponent,
   * passing the employee's data, team ID, and edit mode as data. It then subscribes
   * to the dialog's afterClosed event to handle the result, which is the edited
   * employee data. If the result is not null or undefined, it calls the editEmployee
   * method to update the employee's data.
   *
   * @param employee The Employee object to be edited.
   */
  openEditEmployeeModal(employee: Employee): void {
    const dialogRef = this.dialog.open(AddEditEmployeeModalComponent, {
      data: {
        dialogTitle: `Edytuj dane pracownika ${employee.firstName} ${employee.lastName}`,
        defaultTeam: this.teamId,
        isEditMode: true,
        currentEmployeeData: employee,
      },
    });

    dialogRef.afterClosed().subscribe((result: Employee) => {
      if (result) {
        this.editEmployee(result);
      }
    });
  }

  /**
   * Updates an existing employee's data.
   * This method takes an updated employee object as a parameter and calls the
   * EmployeesService to update the employee's data on the server. It logs the
   * response or error to the console.
   *
   * @param employee The Employee object with updated details.
   */
  editEmployee(employee: Employee) {
    this.employeesService.editById(employee).subscribe({
      next: (response) => {
        const { message } = response;
        this.communicationService.showInfo(message);
      },
      error: (err) => {
        this.communicationService.showError(err);
      },
    });
  }

  /**
   * Opens a modal to add a new employee to the team.
   * This method initializes a dialog with the AddEditEmployeeModalComponent,
   * passing the team name and ID as data. It then subscribes to the dialog's
   * afterClosed event to handle the result, which is the new employee data.
   * If the result is not null or undefined, it calls the addNewEmployee method
   * to add the new employee to the local employees array and optionally to the backend.
   */
  openAddEmployeeModal(): void {
    const dialogRef = this.dialog.open(AddEditEmployeeModalComponent, {
      data: {
        dialogTitle: "Dodaj nowego pracownika do " + this.teamName,
        defaultTeam: this.teamId,
        isEditMode: false,
      },
    });

    dialogRef.afterClosed().subscribe((result: Employee) => {
      if (result) {
        this.addNewEmployee(result);
      }
    });
  }

  /**
   * Adds a new employee to the local employees array and optionally to the backend.
   * This method takes a new employee object as a parameter and adds it to the
   * local employees array. It also calls the EmployeesService to add the new
   * employee to the backend, logging the response or error to the console.
   *
   * @param newEmployee The Employee object to be added.
   */
  addNewEmployee(newEmployee: Employee): void {
    // Add the new employee to the employees array
    this.employees.push(newEmployee);

    // Optionally, you can also call a service to save the new employee to the backend
    this.employeesService.addNew(newEmployee).subscribe({
      next: (response) => {
        const { message } = response;
        this.communicationService.showInfo(message);
      },
      error: (err) => {
        this.communicationService.showError(err);
      },
    });
  }

  /**
   * Deletes an employee by their ID.
   * This method uses the EmployeesService to delete the employee from the server.
   * If the deletion is successful, it filters out the deleted employee from the local employees array.
   *
   * @param id The ID of the employee to be deleted.
   */
  deleteEmployee(id: number) {
    this.employeesService.deleteById(id).subscribe({
      next: (response) => {
        const { message } = response;
        this.communicationService.showInfo(message);

        // Remove deleted employee from local employees
        this.employees = this.employees.filter((e) => e.id !== id);
      },
      error: (err) => {
        this.communicationService.showError(err);
      },
    });
  }

  /**
   * Opens a confirmation dialog to delete an employee.
   * If the user confirms, it calls the deleteEmployee method to delete the employee.
   *
   * @param employee The employee to be deleted.
   */
  confirmEmployeeDelete(employee: Employee) {
    const dialogRef = this.dialog.open(DeleteConfirmModalComponent, {
      data: {
        textToShow: `Czy napewno checsz usunąć pracownika ${employee.firstName} ${employee.lastName}?`,
      },
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      // Call delete method if confirmed
      if (result) {
        this.deleteEmployee(employee.id);
      }
    });
  }

  // Teams

  /**
   * Opens a dialog for renaming the team and updates the team name if confirmed.
   */
  teamRename() {
    const dialogRef = this.addEditTeamsDialog.open(AddEditTeamsModalComponent, {
      data: { dialogTitle: "Zmień nazwę zespołu", oldName: this.teamName },
    });

    /**
     * Interface to define the structure of the dialog result.
     * @property confirmed - Indicates if the rename operation was confirmed.
     * @property newName - The new name for the team.
     */
    interface DialogResult {
      confirmed: boolean;
      newName: string;
    }

    dialogRef.afterClosed().subscribe((result: DialogResult) => {
      if (result.confirmed) {
        this.teamService.updateTeam(this.teamId, result.newName).subscribe({
          next: (response) => {
            const { message } = response;
            this.communicationService.showInfo(message);
          },
          error: (err) => {
            this.communicationService.showError(err);
          },
        });
      }
    });
  }

  /**
   * Deletes the current team.
   * This method uses the TeamService to delete the team from the server.
   * If the deletion is successful, it filters out the deleted team's employees and hides the component.
   */
  teamDelete(): void {
    this.teamService.deleteTeam(this.teamId).subscribe({
      next: (response) => {
        const { message } = response;
        this.communicationService.showInfo(message);

        this.employees = this.employees.filter(
          (e, index) => e.id !== this.employees[index].id
        );

        // Hide the component after successful deletion
        this.showComponent = false;
      },
      error: (err) => {
        this.communicationService.showError(err);
      },
    });
  }

  /**
   * Opens a confirmation dialog to delete the current team.
   * If the user confirms, it calls the teamDelete method to delete the team.
   */
  confirmTeamDelete(): void {
    const dialogRef = this.dialog.open(DeleteConfirmModalComponent, {
      data: { textToShow: "Czy napewno checsz usunąć " + this.teamName + "?" },
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      // Call delete method if confirmed
      if (result) {
        this.teamDelete();
      }
    });
  }
}
