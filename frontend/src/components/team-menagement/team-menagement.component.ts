import { Component } from "@angular/core";
import { FilterHeaderComponent } from "../filter-header/filter-header.component";
import { HelloUserHeaderComponent } from "../hello-user-header/hello-user-header.component";
import { TeamsContainerComponent } from "../teams-container/teams-container.component";

@Component({
  selector: "app-team-menagement",
  imports: [
    FilterHeaderComponent,
    HelloUserHeaderComponent,
    TeamsContainerComponent,
  ],
  template: `
    <app-hello-user-header></app-hello-user-header>
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
