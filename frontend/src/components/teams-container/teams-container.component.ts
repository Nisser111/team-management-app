import { Component, OnInit } from "@angular/core";
import { TeamSectionComponent } from "../team-section/team-section.component";
import { Employee } from "../../interfaces/Employee.interface";
import { NgFor } from "@angular/common";
import { AddNewTeamBtnComponent } from "../add-new-team-btn/add-new-team-btn.component";
import { Team } from "../../interfaces/Team.interface";
import { TeamService } from "../../services/teams.service";

@Component({
  selector: "app-teams-container",
  standalone: true,
  imports: [TeamSectionComponent, NgFor, AddNewTeamBtnComponent],
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
export class TeamsContainerComponent implements OnInit {
  teams: Team[] = [];
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

  constructor(private teamService: TeamService) {}

  ngOnInit() {
    this.teamService.getTeams().subscribe({
      next: (data) => {
        this.teams = data;
      },
      error: (error) => {
        console.error("Error fetching teams:", error);
      },
    });
  }

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
