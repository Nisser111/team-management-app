import { Component, Output, EventEmitter } from "@angular/core";
import { MatIconButton } from "@angular/material/button";
import { MatIcon } from "@angular/material/icon";

@Component({
  selector: "app-edit-team-name-button",
  imports: [MatIcon, MatIconButton],
  template: `
    <button mat-icon-button (click)="onEditClick()">
      <mat-icon>edit</mat-icon>
    </button>
  `,
  styles: [
    `
      :host {
        position: relative;
        display: inline-block;
      }

      button {
        mat-icon {
          color: rgba(0, 0, 0, 0.4);
        }
      }
    `,
  ],
})
export class EditTeamNameButtonComponent {
  @Output() edit = new EventEmitter<void>();

  onEditClick() {
    this.edit.emit();
  }
}
