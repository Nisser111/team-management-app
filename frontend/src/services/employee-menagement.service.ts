import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { Employee } from "../interfaces/Employee.interface";
import { AddEditEmployeeModalComponent } from "../components/add-edit-employee-modal/add-edit-employee-modal.component";
import { DeleteConfirmModalComponent } from "../components/delete-confirm-modal/delete-confirm-modal.component";
import { EmployeesService } from "./employees.service";
import { CommunicationService } from "./communication.service";
import { tap, map, catchError, switchMap } from "rxjs/operators";
import { of } from "rxjs";

/**
 * Service responsible for managing employee data, including editing, adding, and deleting employees.
 * It utilizes MatDialog to open modal dialogs for user interaction and EmployeesService for backend operations.
 * CommunicationService is used for displaying information and error messages to the user.
 */
@Injectable({
  providedIn: "root",
})
export class EmployeeManagementService {
  /**
   * Constructor initializes the service with dependencies.
   * @param employeesService Service for managing employee data on the backend.
   * @param communicationService Service for displaying messages to the user.
   */
  constructor(
    private employeesService: EmployeesService,
    private communicationService: CommunicationService
  ) {}

  /**
   * Opens a modal to edit an existing employee's data.
   * This method initializes a dialog with the AddEditEmployeeModalComponent,
   * passing the employee's data, team ID, and edit mode as data. It then subscribes
   * to the dialog's afterClosed event to handle the result, which is the edited
   * employee data. If the result is not null or undefined, it calls the editEmployee
   * method to update the employee's data.
   *
   * @param dialog The MatDialog service to open the dialog.
   * @param employee The Employee object to be edited.
   * @param teamId The ID of the team to which the employee belongs.
   */
  openEditEmployeeModal(
    dialog: MatDialog,
    employee: Employee,
    teamId: number
  ): void {
    const dialogRef = dialog.open(AddEditEmployeeModalComponent, {
      data: {
        dialogTitle: `Edytuj dane pracownika ${employee.firstName} ${employee.lastName}`,
        defaultTeam: teamId,
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
   * Opens a modal to add a new employee.
   * This method initializes a dialog with the AddEditEmployeeModalComponent,
   * passing the team name, team ID, and a flag indicating it's not in edit mode as data.
   * It then subscribes to the dialog's afterClosed event to handle the result, which is the new
   * employee data. If the result is not null or undefined, it calls the addEmployee method to
   * add the new employee's data.
   *
   * @param dialog The MatDialog service to open the dialog.
   * @param teamName The name of the team to which the employee is being added.
   * @param teamId The ID of the team to which the employee is being added.
   * @returns An Observable that emits the result of adding a new employee.
   */
  openAddEmployeeModal(dialog: MatDialog, teamName: string, teamId: number) {
    const dialogRef = dialog.open(AddEditEmployeeModalComponent, {
      data: {
        dialogTitle: `Dodaj nowego pracownika do ${teamName}`,
        defaultTeam: teamId,
        isEditMode: false,
      },
    });

    return dialogRef.afterClosed().pipe(
      switchMap((dialogResult: Employee) => {
        if (dialogResult) {
          return this.addEmployee(dialogResult).pipe(
            map((result) => ({ ...result, newEmployee: dialogResult }))
          );
        }
        return of({ success: false });
      })
    );
  }

  /**
   * Initiates the deletion process of an employee by opening a confirmation dialog.
   * If the user confirms the deletion, it proceeds to call the deleteEmployee method.
   *
   * @param dialog The MatDialog service to open the dialog.
   * @param employee The Employee object representing the employee to be deleted.
   * @param employees The array of Employee objects from which the employee will be removed.
   */
  confirmEmployeeDelete(
    dialog: MatDialog,
    employee: Employee,
    employees: Employee[]
  ) {
    const dialogRef = dialog.open(DeleteConfirmModalComponent, {
      data: {
        textToShow: `Czy na pewno chcesz usunąć pracownika ${employee.firstName} ${employee.lastName}?`,
      },
    });

    return dialogRef.afterClosed().pipe(
      switchMap((dialogResult: boolean) => {
        if (dialogResult) {
          return this.deleteEmployee(employee.id, employees);
        }
        return of({ success: false });
      })
    );
  }

  /**
   * Updates an existing employee's data.
   * This method takes an updated employee object as a parameter and calls the
   * EmployeesService to update the employee's data on the server. It logs the
   * response or error to the console.
   *
   * @param employee The Employee object with updated details.
   */
  private editEmployee(employee: Employee): void {
    this.employeesService.editById(employee).subscribe({
      next: ({ message }) => this.communicationService.showInfo(message),
      error: (err) => this.communicationService.showError(err),
    });
  }

  /**
   * Initiates the process of adding a new employee to the system.
   * This method calls the EmployeesService to add the new employee to the backend.
   * It handles the response or error from the backend operation, logging the outcome to the user.
   *
   * @param employee The Employee object representing the new employee to be added.
   * @returns An observable that resolves to an object indicating the success or failure of the operation.
   */
  private addEmployee(employee: Employee) {
    return this.employeesService.addNew(employee).pipe(
      tap({
        next: ({ message }) => this.communicationService.showInfo(message),
        error: (err) => this.communicationService.showError(err),
      }),
      map((response) => ({ success: true })),
      catchError((error) => of({ success: false }))
    );
  }

  /**
   * Deletes an employee from the local employees array and the backend.
   * This method calls the EmployeesService to delete the employee by their ID.
   * If the deletion is successful, it removes the employee from the local array
   * and logs an info message. If an error occurs, it logs an error message.
   *
   * @param id The ID of the employee to be deleted.
   * @param employees The array of employees from which the employee will be removed.
   */
  private deleteEmployee(id: number, employees: Employee[]) {
    return this.employeesService.deleteById(id).pipe(
      tap({
        next: ({ message }) => this.communicationService.showInfo(message),
        error: (err) => this.communicationService.showError(err),
      }),
      map((response) => ({ success: true })),
      catchError((error) => of({ success: false }))
    );
  }
}
