import { Component } from "@angular/core";
import { RouterOutlet } from "@angular/router";
import { TeamMenagementComponent } from "../components/team-menagement/team-menagement.component";

@Component({
  selector: "app-root",
  imports: [RouterOutlet, TeamMenagementComponent],
  template: `
    <app-team-menagement></app-team-menagement>
    <router-outlet />
  `,
})
export class AppComponent {}
