import { Component } from "@angular/core";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatSelectModule } from "@angular/material/select";
import { FormsModule } from "@angular/forms";
import { MatIconModule } from "@angular/material/icon";
import { Team } from "../../interfaces/Team.interface";

/**
 * FilterHeaderComponent is a standalone component that provides a toolbar
 * for filtering teams. It allows users to select a team from a dropdown
 * menu and displays the selected team.
 */
@Component({
  selector: "app-filter-header",
  standalone: true,
  imports: [MatToolbarModule, MatSelectModule, FormsModule, MatIconModule],
  template: `
    <mat-toolbar color="primary">
      <h2>
        <mat-icon>tune</mat-icon>
        {{ title }}
      </h2>
      <div class="spacer"></div>

      <mat-form-field>
        <mat-label>Pokaż zespół</mat-label>
        <mat-select [(ngModel)]="selectedTeam">
          @for (team of teams; track team) {
          <mat-option [value]="team.id">{{ team.name }}</mat-option>
          }
        </mat-select>
      </mat-form-field>
    </mat-toolbar>
  `,
  styleUrls: ["../../styles/filter-header.scss"],
})
export class FilterHeaderComponent {
  title = "Pokaż";

  teamsFromDb: Team[] = [
    { id: 1, name: "Frontend team" },
    { id: 2, name: "Backend team" },
    { id: 3, name: "Menagemenet team" },
  ]; //temp

  teams = [{ id: "all", name: "Wszystkie" }, ...this.teamsFromDb];
  selectedTeam = "all";
}
