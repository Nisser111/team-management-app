import { Component, EventEmitter, Output } from "@angular/core";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatSelectModule } from "@angular/material/select";
import { FormsModule } from "@angular/forms";
import { MatIconModule } from "@angular/material/icon";
import { Team } from "../../interfaces/Team.interface";
import { TeamService } from "../../services/teams.service";
import { NgFor } from "@angular/common";
import { CommunicationService } from "../../services/communication.service";

/**
 * FilterHeaderComponent is a standalone component that provides a toolbar
 * for filtering teams. It allows users to select a team from a dropdown
 * menu and displays the selected team.
 */
@Component({
  selector: "app-filter-header",
  standalone: true,
  imports: [
    MatToolbarModule,
    MatSelectModule,
    FormsModule,
    MatIconModule,
    NgFor,
  ],
  templateUrl: "./filter-header.template.html",
  styleUrls: ["./filter-header.scss"],
})
export class FilterHeaderComponent {
  @Output() teamSelected = new EventEmitter<number>();
  selectedTeam = -1;
  teams: Team[] = [];

  constructor(
    private teamService: TeamService,
    private communicationService: CommunicationService
  ) {}

  ngOnInit() {
    // Fetch teams
    this.teamService.getTeams().subscribe({
      next: (response) => {
        const { data } = response;
        this.teams = [{ id: -1, name: "Wszystkie" }, ...(data as Team[])];
      },
      error: (err) => {
        this.communicationService.showError(err);
      },
    });
  }

  onTeamSelect(teamId: number) {
    this.teamSelected.emit(teamId);
  }
}
