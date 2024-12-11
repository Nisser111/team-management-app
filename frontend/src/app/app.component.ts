import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { FilterHeaderComponent } from "../components/filter-header/filter-header.component";
import { HelloUserHeaderComponent } from "../components/hello-user-header/hello-user-header.component";
import { TeamSectionComponent } from "../components/team-section/team-section.component";
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

    <app-filter-header></app-filter-header>

    <main>
      <app-teams-container></app-teams-container>
    </main>

    <router-outlet />
  `,
})
export class AppComponent {}
