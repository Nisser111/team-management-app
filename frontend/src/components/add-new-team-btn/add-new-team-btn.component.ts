import { Component } from "@angular/core";
import { MatButton } from "@angular/material/button";

@Component({
  selector: "app-add-new-team-btn",
  imports: [MatButton],
  template: `
    <button class="add-new-team-btn" mat-stroked-button>
      Dodaj nowy zespół
    </button>
  `,
  styleUrls: ["../../styles/add-new-team-btn.scss"],
})
export class AddNewTeamBtnComponent {}
