import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { FilterHeaderComponent } from "../components/filter-header/filter-header.component";
import { HelloUserHeaderComponent } from "../components/hello-user-header/hello-user-header.component";
import { TeamsContainerComponent } from "../components/teams-container/teams-container.component";

@Component({
  selector: "app-root",
  imports: [
    RouterOutlet,
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
    <router-outlet />
  `,
})
export class AppComponent {
  selectedTeamId: number = -1;

  onTeamSelected(teamId: number) {
    this.selectedTeamId = teamId;
  }
}
