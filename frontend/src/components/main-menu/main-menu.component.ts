import { Component } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { MatToolbar } from "@angular/material/toolbar";
import { GetSummaryBtnComponent } from "../get-summary-btn/get-summary-btn.component";

@Component({
  selector: "app-main-menu",
  imports: [MatToolbar, MatButton, GetSummaryBtnComponent],
  templateUrl: "main-menu.template.html",
  styleUrls: ["./main-menu.styles.scss"],
})
export class MainMenuComponent {
  userData = {
    username: "Miłosz", // temporary hardcoded
  };
}
