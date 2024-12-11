import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { FilterHeaderComponent } from "../components/filter-header/filter-header.component";
import { HelloUserHeaderComponent } from "../components/hello-user-header/hello-user-header.component";
import { TeamSectionComponent } from "../components/team-section/team-section.component";

@Component({
  selector: "app-root",
  imports: [
    RouterOutlet,
    FilterHeaderComponent,
    HelloUserHeaderComponent,
    TeamSectionComponent,
  ],
  template: `
    <app-hello-user-header></app-hello-user-header>

    <app-filter-header></app-filter-header>

    <main>
      <app-team-section></app-team-section>
    </main>

    <router-outlet />
  `,
})
export class AppComponent {}
