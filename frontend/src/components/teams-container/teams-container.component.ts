import { Component, OnInit, Input } from "@angular/core";
import { TeamSectionComponent } from "../team-section/team-section.component";
import { Employee } from "../../interfaces/Employee.interface";
import { NgFor } from "@angular/common";
import { AddNewTeamBtnComponent } from "../add-new-team-btn/add-new-team-btn.component";
import { Team } from "../../interfaces/Team.interface";
import { TeamService } from "../../services/teams.service";
import { EmployeesService } from "../../services/employees.service";
import { MatDialog } from "@angular/material/dialog";
import { CommunicationService } from "../../services/communication.service";
import { TeamManagementService } from "../../services/team-menagement.service";

@Component({
  selector: "app-teams-container",
  standalone: true,
  imports: [TeamSectionComponent, NgFor, AddNewTeamBtnComponent],
  template: `
    <div *ngFor="let team of filteredTeams">
      <app-team-section
        [teamName]="team.name"
        [teamId]="team.id"
        [employees]="getEmployeesByTeamId(team.id)"
        (onEmployeeDelete)="handleEmployeeEdit($event)"
      ></app-team-section>
    </div>
    <app-add-new-team-btn (click)="onAddTeam()"></app-add-new-team-btn>
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
    private employeesService: EmployeesService,
    private communicationService: CommunicationService,
    private teamManagementService: TeamManagementService,
    private dialog: MatDialog
  ) {}

  handleEmployeeEdit(deletedEmployee: Employee) {
    this.employees = this.employees.filter((e) => e.id !== deletedEmployee.id);
  }

  fetchTeams() {
    return this.teamService.getTeams().subscribe({
      next: ({ data }) => {
        this.teams = data as Team[];
        this.filterTeams(); // Filter teams initially
      },
      error: (err) => this.communicationService.showError(err),
    });
  }

  fetchEmployees() {
    return this.employeesService.getAll().subscribe({
      next: ({ data }) => (this.employees = data as Employee[]),
      error: (err) => this.communicationService.showInfo(err),
    });
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

  ngOnInit() {
    // Fetch teams
    this.fetchTeams();

    // Fetch employees
    this.fetchEmployees();
  }

  ngOnChanges() {
    // Re-filter teams when selectedTeamId changes
    this.filterTeams();
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

  // Call addTeam method from teamMenagement service to add new employee
  onAddTeam(): void {
    this.teamManagementService.addTeam(this.dialog, this.fetchTeams.bind(this));
  }
}
