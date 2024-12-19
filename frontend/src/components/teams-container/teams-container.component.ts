import { Component, OnInit, Input } from "@angular/core";
import { TeamSectionComponent } from "../team-section/team-section.component";
import { Employee } from "../../interfaces/Employee.interface";
import { NgFor } from "@angular/common";
import { AddNewTeamBtnComponent } from "../add-new-team-btn/add-new-team-btn.component";
import { Team } from "../../interfaces/Team.interface";
import { TeamService } from "../../services/teams.service";
import { EmployeesService } from "../../services/employees.service";
import { AddEditTeamsModalComponent } from "../add-edit-teams-modal/add-edit-teams-modal.component";
import { MatDialog } from "@angular/material/dialog";
import { CommunicationService } from "../../services/communication.service";

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
      ></app-team-section>
    </div>
    <app-add-new-team-btn (click)="addTeam()"></app-add-new-team-btn>
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
    private addEditTeamsDialog: MatDialog,
    private communicationService: CommunicationService
  ) {}

  fetchTeams() {
    return this.teamService.getTeams().subscribe({
      next: (res) => {
        const { data } = res;

        this.teams = data as Team[];
        this.filterTeams(); // Filter teams initially
      },
      error: (e) => {
        this.communicationService.showError(e);
      },
    });
  }

  ngOnInit() {
    // Fetch teams
    this.fetchTeams();

    // Fetch employees
    this.employeesService.getAll().subscribe({
      next: (response) => {
        const { data } = response;
        this.employees = data as Employee[];
      },
      error: (err) => {
        this.communicationService.showInfo(err);
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

  addTeam() {
    const dialogRef = this.addEditTeamsDialog.open(AddEditTeamsModalComponent, {
      data: { dialogTitle: "Dodaj nowy zespół" },
    });

    interface DialogResult {
      confirmed: boolean;
      newName: string;
    }

    dialogRef.afterClosed().subscribe((result: DialogResult) => {
      if (result.confirmed) {
        this.teamService.addTeam(result.newName).subscribe({
          next: (response) => {
            const { message } = response;
            this.communicationService.showInfo(message);
            this.fetchTeams();
          },
          error: (err) => {
            this.communicationService.showError(err);
          },
        });
      }
    });
  }
}
