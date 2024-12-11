import { Component } from "@angular/core";
import { MatTableModule } from "@angular/material/table";
import { CommonModule } from "@angular/common";
import { MatIcon } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { Employee } from "../../interfaces/Employee.interface";

@Component({
  selector: "app-team-section",
  standalone: true,
  imports: [MatTableModule, CommonModule, MatIcon, MatButtonModule],
  template: `
    <section class="team">
      <h3>{{ teamName }}</h3>
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
              <button mat-icon-button aria-label="Przycisk opcji">
                <mat-icon>more_vert</mat-icon>
              </button>
            </td>
          </ng-container>

          <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
          <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
        </table>
      </div>
    </section>
  `,
  styleUrls: ["../../styles/employees-tables.scss"],
})
export class TeamSectionComponent {
  teamName: string = "Development team";

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
  // temp
  employees: Employee[] = [
    {
      id: 1,
      firstName: "John",
      lastName: "Doe",
      email: "john.doe@example.com",
      phone: "123-456-7890",
      hireDate: "2023-01-15",
      role: "Developer",
      teamId: 1,
    },
    {
      id: 2,
      firstName: "Jane",
      lastName: "Smith",
      email: "jane.smith@example.com",
      phone: "234-567-8901",
      hireDate: "2023-02-20",
      role: "Marketing Specialist",
      teamId: 2,
    },
    {
      id: 3,
      firstName: "Emily",
      lastName: "Johnson",
      email: "emily.johnson@example.com",
      phone: "345-678-9012",
      hireDate: "2023-03-10",
      role: "Sales Associate",
      teamId: 3,
    },
    {
      id: 4,
      firstName: "Michael",
      lastName: "Brown",
      email: "michael.brown@example.com",
      phone: "456-789-0123",
      hireDate: "2023-04-05",
      role: "HR Manager",
      teamId: 4,
    },
    {
      id: 14,
      firstName: "Jakub",
      lastName: "bujak",
      email: "jakub.bujak@example.com",
      phone: "123456789",
      hireDate: "2023-10-20",
      role: "Project Manager",
      teamId: 2,
    },
  ];
}
