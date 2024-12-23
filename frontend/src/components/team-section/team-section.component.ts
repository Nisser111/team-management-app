import { Component, Input } from "@angular/core";
import { MatTableModule } from "@angular/material/table";
import { CommonModule, NgIf } from "@angular/common";
import { MatButton, MatButtonModule } from "@angular/material/button";
import { Employee } from "../../interfaces/Employee.interface";
import { EmployeeOptionsListComponent } from "../employee-options-list/employee-options-list.component";
import { TeamMenuButtonComponent } from "../team-menu-button/team-menu-button.component";
import { MatDialog, MatDialogModule } from "@angular/material/dialog";
import { EmployeeManagementService } from "../../services/employee-menagement.service";
import { TeamManagementService } from "../../services/team-menagement.service";

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
  templateUrl: "./team-section.template.html",
  styleUrls: ["./team-section.scss"],
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
    private employeeManagementService: EmployeeManagementService,
    private teamManagementService: TeamManagementService,
    private dialog: MatDialog
  ) {}

  openEditEmployeeModal(employee: Employee): void {
    this.employeeManagementService.openEditEmployeeModal(
      this.dialog,
      employee,
      this.teamId
    );
  }

  openAddEmployeeModal(): void {
    this.employeeManagementService.openAddEmployeeModal(
      this.dialog,
      this.teamName,
      this.teamId
    );
  }

  confirmEmployeeDelete(employee: Employee): void {
    this.employeeManagementService.confirmEmployeeDelete(
      this.dialog,
      employee,
      this.employees
    );
  }

  teamRename(): void {
    this.teamManagementService.teamRename(
      this.dialog,
      this.teamName,
      this.teamId
    );
  }

  confirmTeamDelete(): void {
    this.teamManagementService.confirmTeamDelete(
      this.dialog,
      this.teamName,
      this.teamId,
      this.employees,
      () => {
        this.showComponent = false;
      }
    );
  }
}
