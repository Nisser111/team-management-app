import { Component, EventEmitter, Output } from "@angular/core";
import { MatButton } from "@angular/material/button";

@Component({
  selector: "app-get-summary-btn",
  imports: [MatButton],
  template: `
    <button mat-button (click)="handleClick()">Pobierz podsumowanie</button>
  `,
  styles: ``,
})
export class GetSummaryBtnComponent {
  handleClick() {}
}
