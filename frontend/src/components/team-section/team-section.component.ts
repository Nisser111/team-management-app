import { Component, Input } from "@angular/core";
import { MatTableModule } from "@angular/material/table";
import { CommonModule } from "@angular/common";
import { MatButton, MatButtonModule } from "@angular/material/button";
import { Employee } from "../../interfaces/Employee.interface";
import { EmployeeOptionsListComponent } from "../employee-options-list/employee-options-list.component";
import { TeamMenuButtonComponent } from "../team-menu-button/team-menu-button.component";

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
  ],
  template: `
    <section class="team">
      <header>
        <h3>
          {{ teamName }}
        </h3>
        <app-team-menu-button
          (rename)="teamRename()"
          (delete)="teamDelete()"
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
                (edit)="editEmployee($event)"
                (move)="moveEmployee($event)"
                (delete)="deleteEmployee($event)"
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

      <button mat-button class="add-new-employee-btn">Dodaj pracownika</button>
    </section>
  `,
  styleUrls: ["../../styles/employees-tables.scss"],
})
export class TeamSectionComponent {
  @Input() teamName: string = "";
  @Input() employees: Employee[] = [];

  editEmployee(employee: Employee) {
    // Implement edit logic here
  }

  moveEmployee(employee: Employee) {
    // Implement move logic here
  }

  deleteEmployee(employee: Employee) {
    // Implement delete logic here
  }

  // New methods to handle rename and delete events
  teamRename() {
    // Implement rename logic here
    console.log("Rename action triggered");
  }

  teamDelete() {
    // Implement delete logic here
    console.log("Delete action triggered");
  }

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
}
