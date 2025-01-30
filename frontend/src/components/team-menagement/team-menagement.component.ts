import { Component } from "@angular/core";
import { FilterHeaderComponent } from "../filter-header/filter-header.component";
import { TeamsContainerComponent } from "../teams-container/teams-container.component";
import { MainMenuComponent } from "../main-menu/main-menu.component";

@Component({
  selector: "app-team-menagement",
  imports: [FilterHeaderComponent, TeamsContainerComponent, MainMenuComponent],
  template: `
    <app-main-menu></app-main-menu>
    <app-filter-header
      (teamSelected)="onTeamSelected($event)"
    ></app-filter-header>

    <main>
      <app-teams-container
        [selectedTeamId]="selectedTeamId"
      ></app-teams-container>
    </main>
  `,
  styles: ``,
})
export class TeamMenagementComponent {
  selectedTeamId: number = -1;

  onTeamSelected(teamId: number) {
    this.selectedTeamId = teamId;
  }
}
