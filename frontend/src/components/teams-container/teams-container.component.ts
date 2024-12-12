import { Component } from "@angular/core";
import { TeamSectionComponent } from "../team-section/team-section.component";
import { Employee } from "../../interfaces/Employee.interface";
import { NgFor } from "@angular/common";
import { MatButton } from "@angular/material/button";
import { AddNewTeamBtnComponent } from "../add-new-team-btn/add-new-team-btn.component";

@Component({
  selector: "app-teams-container",
  standalone: true,
  imports: [TeamSectionComponent, NgFor, MatButton, AddNewTeamBtnComponent],
  template: `
    <div *ngFor="let team of teams">
      <app-team-section
        [teamName]="team.name"
        [employees]="getEmployeesByTeamId(team.id)"
      ></app-team-section>
    </div>
    <app-add-new-team-btn></app-add-new-team-btn>
  `,
  styles: [],
})
export class TeamsContainerComponent {
  teams = [
    { id: 1, name: "Development Team" },
    { id: 2, name: "Front-end team" },
    { id: 3, name: "Sales Team" },
    { id: 4, name: "HR Team" },
    { id: 5, name: "Design Team" },
  ];

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
      teamId: 5,
    },
    {
      id: 14,
      firstName: "Jakub",
      lastName: "Bujak",
      email: "jakub.bujak@example.com",
      phone: "123456789",
      hireDate: "2023-10-20",
      role: "Project Manager",
      teamId: 2,
    },
    {
      id: 7,
      firstName: "Jakub",
      lastName: "Bujak",
      email: "jakub.bujak@example.com",
      phone: "123456789",
      hireDate: "2023-10-20",
      role: "Project Manager",
      teamId: 5,
    },
  ];

  /**
   * Retrieves a list of employees that belong to a specific team.
   *
   * @param teamId - The ID of the team for which to retrieve employees.
   * @returns An array of Employee objects that are associated with the given team ID.
   */
  getEmployeesByTeamId(teamId: number): Employee[] {
    return this.employees.filter((employee) => employee.teamId === teamId);
  }
}
