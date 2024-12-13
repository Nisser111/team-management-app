import { Component, OnInit, Input } from "@angular/core";
import { TeamSectionComponent } from "../team-section/team-section.component";
import { Employee } from "../../interfaces/Employee.interface";
import { NgFor } from "@angular/common";
import { AddNewTeamBtnComponent } from "../add-new-team-btn/add-new-team-btn.component";
import { Team } from "../../interfaces/Team.interface";
import { TeamService } from "../../services/teams.service";
import { EmployeesService } from "../../services/employees.service";

@Component({
  selector: "app-teams-container",
  standalone: true,
  imports: [TeamSectionComponent, NgFor, AddNewTeamBtnComponent],
  template: `
    <div *ngFor="let team of filteredTeams">
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
  @Input() selectedTeamId: number = -1; // Input property for selected team ID. -1 means all teams
  teams: Team[] = [];
  filteredTeams: Team[] = [];
  employees: Employee[] = [];

  constructor(
    private teamService: TeamService,
    private employeesService: EmployeesService
  ) {}

  ngOnInit() {
    // Fetch teams
    this.teamService.getTeams().subscribe({
      next: (data) => {
        this.teams = data;
        this.filterTeams(); // Filter teams initially
      },
      error: (error) => {
        console.error("Error fetching teams:", error);
      },
    });

    // Fetch employees
    this.employeesService.getAll().subscribe({
      next: (data) => {
        this.employees = data;
      },
      error: (error) => {
        console.error("Error fetching employees:", error);
      },
    });
  }

  ngOnChanges() {
    this.filterTeams(); // Re-filter teams when selectedTeamId changes
  }

  // Method to filter teams based on selected team ID
  filterTeams() {
    if (this.selectedTeamId === -1) {
      this.filteredTeams = this.teams;
    } else {
      // Filter teams based on selection
      this.filteredTeams = this.teams.filter(
        (team) => team.id === this.selectedTeamId
      );
    }
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
